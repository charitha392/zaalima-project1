package com.vaultcore.vaultcore_backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        try {
            MongoClient client = MongoClients.create(
                "mongodb://localhost:27017/vaultcore_db"
            );
            // Test connection
            client.listDatabaseNames().first();
            System.out.println("✅ MongoDB Connected");
            return client;
        } catch (Exception e) {
            System.err.println("❌ MongoDB connection failed: " + e.getMessage());
            System.err.println("⚠️  Please ensure MongoDB is running on localhost:27017");
            // Don't exit - let Spring Boot handle the failure more gracefully
            throw new RuntimeException("MongoDB connection failed. Please start MongoDB.", e);
        }
    }
}