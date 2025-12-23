package com.vaultcore.vaultcore_backend.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    @NotBlank(message = "Account type is required")
    private String accountType; // e.g., "SAVINGS", "CHECKING"

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be positive")
    private BigDecimal balance;

    @DBRef
    @NotNull(message = "User is required")
    private User user;
}