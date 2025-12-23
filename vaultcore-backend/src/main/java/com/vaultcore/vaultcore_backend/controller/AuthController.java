package com.vaultcore.vaultcore_backend.controller;

import com.vaultcore.vaultcore_backend.dto.AuthRequest;
import com.vaultcore.vaultcore_backend.dto.AuthResponse;
import com.vaultcore.vaultcore_backend.dto.RefreshTokenRequest;
import com.vaultcore.vaultcore_backend.dto.SignupRequest;
import com.vaultcore.vaultcore_backend.entity.RefreshToken;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.service.RefreshTokenService;
import com.vaultcore.vaultcore_backend.service.UserService;
import com.vaultcore.vaultcore_backend.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "Email already exists"));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userService.save(user);
        return ResponseEntity.ok(java.util.Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        if (userService.authenticate(request.getEmail(), request.getPassword())) {
            Optional<User> userOpt = userService.findByEmail(request.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String accessToken = jwtUtil.generateToken(request.getEmail());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
                return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
            }
        }
        return ResponseEntity.badRequest().body(java.util.Map.of("message", "Invalid credentials"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(request.getRefreshToken());
        
        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "Invalid refresh token"));
        }

        RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenOpt.get());
        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.ok(java.util.Map.of("message", "Logged out successfully"));
    }
}