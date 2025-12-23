package com.vaultcore.vaultcore_backend.service;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.Transaction;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;
    private final LedgerService ledgerService;

    public TransactionService(TransactionRepository repository, AccountService accountService,
                             LedgerService ledgerService) {
        this.repository = repository;
        this.accountService = accountService;
        this.ledgerService = ledgerService;
    }

    @Transactional
    public Transaction performTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        BigDecimal newBalance;

        if ("DEBIT".equals(transaction.getType())) {
            if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            newBalance = account.getBalance().subtract(transaction.getAmount());
        } else if ("CREDIT".equals(transaction.getType())) {
            newBalance = account.getBalance().add(transaction.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        // Validate balance constraint
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Balance constraint violation: balance cannot be negative");
        }

        account.setBalance(newBalance);
        accountService.createAccount(account); // Update account balance

        // Create immutable ledger entry
        ledgerService.createLedgerEntry(transaction.getAmount(), transaction.getType(),
                                       newBalance, account, transaction.getUser());

        return repository.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(User user) {
        return repository.findByUserOrderByTimestampDesc(user);
    }
}