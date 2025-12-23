package com.vaultcore.vaultcore_backend.repository;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.Ledger;
import com.vaultcore.vaultcore_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ledger Repository - Prevents update operations to maintain immutability
 * Only save (insert) and read operations are allowed.
 */
@Repository
public interface LedgerRepository extends MongoRepository<Ledger, String> {
    
    // Find all ledger entries for a specific account
    List<Ledger> findByAccountOrderByTimestampDesc(Account account);
    
    // Find all ledger entries for a specific user
    List<Ledger> findByUserOrderByTimestampDesc(User user);
    
    // Find ledger entries for an account sorted by timestamp
    List<Ledger> findByAccountIdOrderByTimestampAsc(String accountId);
    
    // Prevent update operations by not exposing save() for updates
    // Only use save() for new inserts
}

