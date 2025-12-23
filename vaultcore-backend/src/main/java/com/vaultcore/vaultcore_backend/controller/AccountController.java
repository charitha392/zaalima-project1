package com.vaultcore.vaultcore_backend.controller;

import com.vaultcore.vaultcore_backend.dto.AccountRequest;
import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.service.AccountService;
import com.vaultcore.vaultcore_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest request, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Account account = new Account();
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialBalance());
        account.setUser(user.get());

        Account savedAccount = accountService.createAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Account> accounts = accountService.getAccountsByUser(user.get());
        return ResponseEntity.ok(accounts);
    }
}