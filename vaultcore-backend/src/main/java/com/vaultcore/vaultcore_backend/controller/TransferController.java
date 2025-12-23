package com.vaultcore.vaultcore_backend.controller;

import com.vaultcore.vaultcore_backend.dto.TransferRequest;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.service.TransferService;
import com.vaultcore.vaultcore_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final UserService userService;

    public TransferController(TransferService transferService, UserService userService) {
        this.transferService = transferService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            transferService.transfer(request.getFromAccountId(), request.getToAccountId(), 
                                   request.getAmount(), user.get());
            return ResponseEntity.ok(java.util.Map.of("message", "Transfer completed successfully"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }
}

