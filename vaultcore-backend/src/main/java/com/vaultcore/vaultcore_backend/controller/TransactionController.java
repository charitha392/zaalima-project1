package com.vaultcore.vaultcore_backend.controller;

import com.vaultcore.vaultcore_backend.dto.TransactionRequest;
import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.Transaction;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.service.AccountService;
import com.vaultcore.vaultcore_backend.service.TransactionService;
import com.vaultcore.vaultcore_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, AccountService accountService, UserService userService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> performTransaction(@Valid @RequestBody TransactionRequest request, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Account> account = accountService.getAccountById(request.getAccountId());
        if (account.isEmpty() || !account.get().getUser().getId().equals(user.get().getId())) {
            return ResponseEntity.badRequest().body("Invalid account");
        }

        try {
            Transaction transaction = new Transaction();
            transaction.setAmount(request.getAmount());
            transaction.setType(request.getType());
            transaction.setAccount(account.get());
            transaction.setUser(user.get());

            Transaction savedTransaction = transactionService.performTransaction(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Transaction> transactions = transactionService.getTransactionsByUser(user.get());
        return ResponseEntity.ok(transactions);
    }
}