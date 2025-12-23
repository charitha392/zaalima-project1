package com.vaultcore.vaultcore_backend.service;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Transfer Service with SERIALIZABLE isolation level
 * This ensures that concurrent transactions are fully isolated and
 * prevents race conditions when multiple threads try to modify balances simultaneously.
 */
@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final LedgerService ledgerService;

    public TransferService(AccountRepository accountRepository, LedgerService ledgerService) {
        this.accountRepository = accountRepository;
        this.ledgerService = ledgerService;
    }

    /**
     * Transfer money between accounts with SERIALIZABLE isolation
     * This method ensures atomicity and prevents race conditions
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount, User user) {
        if (fromAccountId == null || toAccountId == null) {
            throw new IllegalArgumentException("Account IDs must not be null");
        }
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Fetch accounts with SERIALIZABLE isolation - prevents dirty reads
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountRepository.findById(toAccountId);

        if (fromAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Source account not found");
        }
        if (toAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("Destination account not found");
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        // Verify account ownership
        String fromUserId = fromAccount.getUser() != null ? fromAccount.getUser().getId() : null;
        String userId = user.getId();
        if (fromUserId == null || !fromUserId.equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to transfer from this account");
        }

        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Perform transfer atomically
        BigDecimal fromNewBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal toNewBalance = toAccount.getBalance().add(amount);

        // Validate balances cannot be negative
        if (fromNewBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Balance constraint violation: balance cannot be negative");
        }

        // Update balances
        fromAccount.setBalance(fromNewBalance);
        toAccount.setBalance(toNewBalance);

        // Save accounts (with optimistic locking via @Version if using JPA)
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Create immutable ledger entries
        ledgerService.createLedgerEntry(amount, "DEBIT", fromNewBalance, fromAccount, user);
        ledgerService.createLedgerEntry(amount, "CREDIT", toNewBalance, toAccount, user);
    }

    /**
     * Withdraw money from an account with SERIALIZABLE isolation
     * Used for testing concurrency with 100 threads
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void withdraw(String accountId, BigDecimal amount, User user) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must not be null");
        }
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = accountOpt.get();

        // Verify account ownership
        String accountUserId = account.getUser() != null ? account.getUser().getId() : null;
        String userId = user.getId();
        if (accountUserId == null || !accountUserId.equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to withdraw from this account");
        }

        // Check sufficient balance with SERIALIZABLE isolation
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Perform withdrawal atomically
        BigDecimal newBalance = account.getBalance().subtract(amount);

        // Validate balance constraint
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Balance constraint violation: balance cannot be negative");
        }

        // Update balance
        account.setBalance(newBalance);
        accountRepository.save(account);

        // Create immutable ledger entry
        ledgerService.createLedgerEntry(amount, "DEBIT", newBalance, account, user);
    }

    /**
     * Deposit money to an account with SERIALIZABLE isolation
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deposit(String accountId, BigDecimal amount, User user) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must not be null");
        }
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = accountOpt.get();

        // Verify account ownership
        String accountUserId = account.getUser() != null ? account.getUser().getId() : null;
        String userId = user.getId();
        if (accountUserId == null || !accountUserId.equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to deposit to this account");
        }

        // Perform deposit atomically
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        // Create immutable ledger entry
        ledgerService.createLedgerEntry(amount, "CREDIT", newBalance, account, user);
    }
}

