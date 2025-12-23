# Quick Start Guide - VaultCore

## ⚠️ IMPORTANT: Start Backend First!

The **ERR_CONNECTION_REFUSED** error means the backend is not running.

## Steps to Fix:

### 1. Check if MongoDB is Running
```powershell
Get-Process mongod -ErrorAction SilentlyContinue
```

If MongoDB is NOT running, start it first!

### 2. Start Backend Server

**Open a NEW PowerShell/Terminal window** and run:

```powershell
cd vaultcore-backend
mvn spring-boot:run
```

**Wait for these messages:**
- ✅ `MongoDB Connected`
- ✅ `Started VaultcoreBackendApplication`

### 3. Verify Backend is Running

In another terminal, check:
```powershell
Get-NetTCPConnection -LocalPort 8080
```

You should see port 8080 in LISTENING state.

### 4. Refresh Frontend Browser

Once backend is running, refresh your browser at `http://localhost:3000`

---

## Alternative: Use the Scripts

I've created helper scripts for you:

**Backend:**
```powershell
.\run-backend.ps1
```

**Frontend:**
```powershell
.\run-frontend.ps1
```

---

## Troubleshooting

### Backend won't start?
- **MongoDB not running**: Start MongoDB service first
- **Port 8080 in use**: Stop other services using port 8080
- **Java not found**: Make sure Java 21 is installed and in PATH

### Still getting ERR_CONNECTION_REFUSED?
- Make sure backend terminal shows "Started VaultcoreBackendApplication"
- Check backend logs for errors
- Verify backend is listening: `netstat -an | findstr 8080`

