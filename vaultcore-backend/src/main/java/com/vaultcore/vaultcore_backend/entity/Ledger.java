package com.vaultcore.vaultcore_backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Ledger Entity - Immutable once created
 * This represents an immutable ledger entry that cannot be modified after creation.
 * All balance validations and constraints are enforced at the application level.
 * Immutability is enforced by not providing setters and validating constraints in constructor.
 */
@Getter
@NoArgsConstructor
@Document(collection = "ledger")
public class Ledger {

    @Id
    private String id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Transaction type is required")
    private String transactionType; // "DEBIT" or "CREDIT"

    @NotNull(message = "Balance after transaction is required")
    @DecimalMin(value = "0.0", message = "Balance must be non-negative")
    private BigDecimal balanceAfter; // Balance after this transaction

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @DBRef
    @NotNull(message = "Account is required")
    private Account account;

    @DBRef
    @NotNull(message = "User is required")
    private User user;

    @Version
    private Long version; // Optimistic locking to prevent concurrent modifications

    // Constructor for creating new ledger entries - enforces immutability
    public Ledger(BigDecimal amount, String transactionType, BigDecimal balanceAfter, 
                  Account account, User user) {
        // Validate constraints
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive. Constraint violation.");
        }
        if (balanceAfter == null || balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative. Constraint violation.");
        }
        if (transactionType == null || (!"DEBIT".equals(transactionType) && !"CREDIT".equals(transactionType))) {
            throw new IllegalArgumentException("Transaction type must be DEBIT or CREDIT. Constraint violation.");
        }
        
        this.amount = amount;
        this.transactionType = transactionType;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.account = account;
        this.user = user;
    }

    // No setters provided - immutability enforced at application level
    // Once created, ledger entries should never be modified
}

