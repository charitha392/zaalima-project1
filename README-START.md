# 🚀 How to Start VaultCore

## 🌟 Key Features

- **Database**: Immutable ledger schema with CHECK constraints to prevent negative balances.
- **Core Engine**: `TransferService` wrapped in `@Transactional(isolation = Isolation.SERIALIZABLE)` for ACID-safe money movement.
- **Security**: Spring Security 6 with JWT (access/refresh tokens) and BCrypt password hashing.
- **Send Money Wizard**: Guided flow with validation and real-time balance updates.
- **Frontend Auth**: React login UI integrated with the secure backend authentication APIs.
- **Dashboard**: Transaction history API surfaces sent/received logs dynamically on the frontend.

---

# 📁 VaultCore Frontend – Folder Structure Explanation

**VaultCore Frontend** is built using **React + TypeScript**, following a **feature-based and scalable architecture** suitable for a **banking & trading application**.

## 1️⃣ Root Folder – `vaultcore-frontend/`
Main frontend project directory with config, source, and public assets.

## 2️⃣ `src/` – Application Source Code
All core application logic.

## 3️⃣ `assets/` – Static Resources
```
assets/
├── images/
└── styles/
    └── theme.css
```
Logos, icons, banners, and global theme styles.

## 4️⃣ `components/` – UI Components (feature-based)
Organized by domain for maintainability.

### 🔐 `auth/` – Authentication Screens
```
auth/
├── Login.tsx
├── Signup.tsx
└── ForgotPassword.tsx
```
Login, registration, password recovery.

### 📊 `dashboard/` – User Dashboard
```
dashboard/
├── Dashboard.tsx
├── AccountSummary.tsx
├── BalanceCard.tsx
└── RecentTransactions.tsx
```
Account overview, balances, recent transactions snapshot.

### 🏦 `banking/` – Core Banking Features
```
banking/
├── TransferMoney.tsx
├── TransactionHistory.tsx
└── AddBeneficiary.tsx
```
Transfers, full history, beneficiary management.

### 📈 `trading/` – Stock Trading Module
```
trading/
├── Portfolio.tsx
├── BuyStock.tsx
├── SellStock.tsx
└── StockChart.tsx
```
Portfolio, buy/sell stocks, price visualization.

### 🛡️ `admin/` – Admin-Only Features
```
admin/
├── AuditLogs.tsx
└── UserManagement.tsx
```
Audit logs and user management (admin role).

### 🔁 `common/` – Reusable Components
```
common/
├── Navbar.tsx
├── Sidebar.tsx
├── Footer.tsx
├── ProtectedRoute.tsx
└── Loader.tsx
```
Navigation layout, route protection, global loader.

## 5️⃣ `services/` – API Communication Layer
```
services/
├── authService.ts
├── accountService.ts
├── transactionService.ts
└── tradingService.ts
```
Axios-based API calls; keeps networking separate from UI.

## 6️⃣ `context/` – Global State Management
```
context/
├── AuthContext.tsx
└── ThemeContext.tsx
```
Authentication state (JWT, roles) and theme switching.

## 7️⃣ `hooks/` – Custom React Hooks
```
hooks/
└── useAuth.ts
```
Wraps auth logic for reuse.

## 8️⃣ `types/` – TypeScript Type Definitions
```
types/
├── auth.types.ts
├── account.types.ts
├── transaction.types.ts
└── trading.types.ts
```
Strong typing for APIs and models.

## 9️⃣ `utils/` – Utility & Configuration
```
utils/
├── axiosConfig.ts
└── constants.ts
```
Central Axios config and shared constants.

## 🔟 `routes/` – Application Routing
```
routes/
└── AppRoutes.tsx
```
Centralized, role-aware route definitions.

## 1️⃣1️⃣ Core Files
```
App.tsx  → Root component
main.tsx → Application entry point
```

## 1️⃣2️⃣ `public/`
`index.html` and other static assets.

## 1️⃣3️⃣ Configuration Files
`tsconfig.json`, `package.json`, `README.md`

## ✅ Architecture Benefits
Feature-based, scalable, clean separation of concerns, secure role-based routing—fit for FinTech/banking applications.

## ❌ Current Issue: Backend Server Not Running

You're seeing `ERR_CONNECTION_REFUSED` because the **backend server is not running**.

---

## ✅ Solution: Start the Backend Server

### Method 1: Using Batch File (Easiest)

**Double-click** the file: `START-BACKEND.bat`

This will open a window and start the backend server.

---

### Method 2: Using PowerShell/Terminal

**Open a NEW terminal window** and run:

```powershell
cd vaultcore-backend
mvn spring-boot:run
```

---

### Method 3: Using the PowerShell Script

```powershell
.\run-backend.ps1
```

---

## ⏳ What to Wait For

After running the backend, wait for these messages:

```
✅ MongoDB Connected
Started VaultcoreBackendApplication in X.XXX seconds
```

**Once you see "Started VaultcoreBackendApplication", the backend is ready!**

---

## 🔄 After Backend Starts

1. **Refresh your browser** (frontend should already be running)
2. The `ERR_CONNECTION_REFUSED` errors will disappear
3. You can now use Sign Up and Sign In features

---

## ⚠️ Prerequisites

**MongoDB must be running!**

Check if MongoDB is running:
```powershell
Get-Process mongod -ErrorAction SilentlyContinue
```

If not running, start MongoDB first before starting the backend.

---

## 📋 Quick Checklist

- [ ] MongoDB is running
- [ ] Backend server started (you see "Started VaultcoreBackendApplication")
- [ ] Frontend is running (already running in your browser)
- [ ] Browser refreshed after backend started

---

## 🐛 Still Having Issues?

1. **Check backend terminal** for error messages
2. **Verify MongoDB** is running: `mongodb://localhost:27017`
3. **Check port 8080** is not in use by another service
4. **Look at backend logs** for specific error messages

