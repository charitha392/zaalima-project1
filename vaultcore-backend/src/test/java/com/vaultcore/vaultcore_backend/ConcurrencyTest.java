package com.vaultcore.vaultcore_backend;

import com.vaultcore.vaultcore_backend.entity.Account;
import com.vaultcore.vaultcore_backend.entity.User;
import com.vaultcore.vaultcore_backend.repository.AccountRepository;
import com.vaultcore.vaultcore_backend.service.TransferService;
import com.vaultcore.vaultcore_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Concurrency Test - Week 2 Requirement
 * Test 100 threads trying to withdraw simultaneously.
 * Final balance must be correct and non-negative.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ConcurrencyTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testConcurrentWithdrawals() throws InterruptedException {
        // Setup: Create a user and account with initial balance
        User testUser = new User();
        testUser.setEmail("concurrency-test@test.com");
        testUser.setPassword("password123");
        testUser.setRole("USER");
        User savedUser = userService.save(testUser);

        Account testAccount = new Account();
        testAccount.setAccountType("CHECKING");
        testAccount.setBalance(new BigDecimal("1000.00")); // Initial balance: 1000
        testAccount.setUser(savedUser);
        Account savedAccount = accountRepository.save(testAccount);

        final String accountId = Objects.requireNonNull(savedAccount.getId(), "Account ID cannot be null after save");
        final BigDecimal withdrawalAmount = new BigDecimal("1.00"); // Each thread withdraws 1.00
        final int numberOfThreads = 100;
        final int expectedWithdrawals = 100; // Should be able to do 100 withdrawals of 1.00 each

        // Expected final balance: 1000 - (100 * 1.00) = 900.00
        final BigDecimal expectedFinalBalance = new BigDecimal("900.00");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        List<Future<?>> futures = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();
        List<Boolean> successFlags = new ArrayList<>();

        // Launch 100 concurrent withdrawal threads
        for (int i = 0; i < numberOfThreads; i++) {
            Future<?> future = executor.submit(() -> {
                try {
                    latch.countDown();
                    latch.await(); // Wait for all threads to be ready
                    
                    transferService.withdraw(accountId, withdrawalAmount, savedUser);
                    synchronized (successFlags) {
                        successFlags.add(true);
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    // Expected for some threads if balance becomes insufficient
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                    synchronized (successFlags) {
                        successFlags.add(false);
                    }
                } catch (Exception e) {
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                }
            });
            futures.add(future);
        }

        // Wait for all threads to complete
        for (Future<?> future : futures) {
            try {
                future.get(10, TimeUnit.SECONDS);
            } catch (ExecutionException | TimeoutException e) {
                fail("Thread execution failed or timed out: " + e.getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Verify final balance
        Optional<Account> finalAccountOpt = accountRepository.findById(accountId);
        if (finalAccountOpt.isEmpty()) {
            fail("Account not found after test");
        }
        Account finalAccount = finalAccountOpt.get();
        BigDecimal finalBalance = finalAccount.getBalance();

        // Assertions
        assertNotNull(finalBalance, "Final balance should not be null");
        assertTrue(finalBalance.compareTo(BigDecimal.ZERO) >= 0, 
                  "Final balance must be non-negative. Actual: " + finalBalance);
        assertEquals(expectedFinalBalance, finalBalance, 
                    "Final balance should be exactly " + expectedFinalBalance + 
                    " but was " + finalBalance);
        
        // Verify that exactly 100 withdrawals succeeded (or as many as possible with sufficient balance)
        long successfulWithdrawals = successFlags.stream().filter(Boolean::booleanValue).count();
        assertEquals(expectedWithdrawals, successfulWithdrawals, 
                    "Expected " + expectedWithdrawals + " successful withdrawals, but got " + successfulWithdrawals);

        System.out.println("✅ Concurrency test passed!");
        System.out.println("   Initial balance: 1000.00");
        System.out.println("   Withdrawals attempted: " + numberOfThreads);
        System.out.println("   Successful withdrawals: " + successfulWithdrawals);
        System.out.println("   Final balance: " + finalBalance);
        System.out.println("   Expected balance: " + expectedFinalBalance);
    }
}

