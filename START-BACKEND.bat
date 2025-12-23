@echo off
echo ========================================
echo Starting VaultCore Backend Server
echo ========================================
echo.
echo Make sure MongoDB is running first!
echo.
cd vaultcore-backend
mvn spring-boot:run
pause

