# How to Start VaultCore Servers

## Prerequisites

1. **MongoDB must be running** on `localhost:27017`
   - Check if MongoDB is running: `Get-Process mongod -ErrorAction SilentlyContinue`
   - If not running, start MongoDB first

## Step 1: Start MongoDB (if not already running)

Make sure MongoDB is running on port 27017.

## Step 2: Start Backend Server

Open **Terminal 1** and run:

```powershell
cd vaultcore-backend
mvn spring-boot:run
```

Wait for the message: `Started VaultcoreBackendApplication` and `✅ MongoDB Connected`

## Step 3: Start Frontend Server

Open **Terminal 2** and run:

```powershell
cd vaultcore-frontend
npm start
```

The frontend will automatically open at `http://localhost:3000`

## Troubleshooting

### Backend won't start
- **MongoDB not running**: Start MongoDB first
- **Port 8080 already in use**: Stop any other service using port 8080
- Check backend logs for errors

### Frontend shows "ERR_CONNECTION_REFUSED"
- Make sure backend is running on port 8080
- Check backend terminal for startup errors
- Verify MongoDB is connected

### React Router warnings
- These are just deprecation warnings, not errors
- Can be safely ignored for now
- Will be fixed in future React Router updates

## Quick Check Commands

```powershell
# Check if backend is running
Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue

# Check if frontend is running
Get-NetTCPConnection -LocalPort 3000 -ErrorAction SilentlyContinue

# Check if MongoDB is running
Get-Process mongod -ErrorAction SilentlyContinue
```

