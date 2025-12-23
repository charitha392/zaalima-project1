package com.vaultcore.vaultcore_backend.service;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account createAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        return repository.save(account);
    }

    public List<Account> getAccountsByUser(User user) {
        return repository.findByUser(user);
    }

    public Optional<Account> getAccountById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        return repository.findById(id);
    }
}