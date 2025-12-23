package com.vaultcore.vaultcore_backend.repository;

import com.vaultcore.vaultcore_backend.entity.Transaction;
import com.vaultcore.vaultcore_backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserOrderByTimestampDesc(User user);
}