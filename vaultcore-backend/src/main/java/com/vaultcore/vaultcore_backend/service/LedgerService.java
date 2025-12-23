package com.vaultcore.vaultcore_backend.service;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.Ledger;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.repository.LedgerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Ledger Service - Handles immutable ledger entries
 * Ensures ledger entries are never modified once created
 */
@Service
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public LedgerService(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    /**
     * Create a new ledger entry - this is the only way to add to ledger
     * Ledger entries are immutable once created
     */
    public Ledger createLedgerEntry(BigDecimal amount, String transactionType, 
                                    BigDecimal balanceAfter, Account account, User user) {
        // Validation is done in Ledger constructor
        Ledger ledger = new Ledger(amount, transactionType, balanceAfter, account, user);
        return ledgerRepository.save(ledger);
    }

    public List<Ledger> getLedgerEntriesByAccount(Account account) {
        return ledgerRepository.findByAccountOrderByTimestampDesc(account);
    }

    public List<Ledger> getLedgerEntriesByUser(User user) {
        return ledgerRepository.findByUserOrderByTimestampDesc(user);
    }

    // No update or delete methods - ledger is immutable
}

