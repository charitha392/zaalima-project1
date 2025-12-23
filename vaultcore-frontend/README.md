# VaultCore Frontend

Built with **React + TypeScript** for a **banking & trading application**, following a **feature-based, scalable architecture**.

## 📌 Core Features
- **Frontend Auth**: React login UI integrated with secure backend authentication APIs.
- **Send Money Wizard**: Guided flow with validation and real-time balance updates.
- **Dashboard**: Transaction history (sent/received) fetched dynamically from the API.
- **Security Alignment**: Works with backend JWT (access/refresh), BCrypt, and serializable transfer engine.

## 📁 Folder Structure (Given Information)

### Root: `vaultcore-frontend/`
Main frontend project directory with configuration, source, and public assets.

### `src/` – Application Source Code
All core application logic.

### `assets/` – Static Resources
```
assets/
├── images/
└── styles/
    └── theme.css
```
Logos, icons, banners, and global theme styles.

### `components/` – UI Components (feature-based)
Organized by domain for maintainability.

#### 🔐 `auth/` – Authentication Screens
```
auth/
├── Login.tsx
├── Signup.tsx
└── ForgotPassword.tsx
```

#### 📊 `dashboard/` – User Dashboard
```
dashboard/
├── Dashboard.tsx
├── AccountSummary.tsx
├── BalanceCard.tsx
└── RecentTransactions.tsx
```

#### 🏦 `banking/` – Core Banking Features
```
banking/
├── TransferMoney.tsx
├── TransactionHistory.tsx
└── AddBeneficiary.tsx
```

#### 📈 `trading/` – Stock Trading Module
```
trading/
├── Portfolio.tsx
├── BuyStock.tsx
├── SellStock.tsx
└── StockChart.tsx
```

#### 🛡️ `admin/` – Admin-Only Features
```
admin/
├── AuditLogs.tsx
└── UserManagement.tsx
```

#### 🔁 `common/` – Reusable Components
```
common/
├── Navbar.tsx
├── Sidebar.tsx
├── Footer.tsx
├── ProtectedRoute.tsx
└── Loader.tsx
```

### `services/` – API Communication Layer
```
services/
├── authService.ts
├── accountService.ts
├── transactionService.ts
└── tradingService.ts
```

### `context/` – Global State Management
```
context/
├── AuthContext.tsx
└── ThemeContext.tsx
```

### `hooks/` – Custom React Hooks
```
hooks/
└── useAuth.ts
```

### `types/` – TypeScript Type Definitions
```
types/
├── auth.types.ts
├── account.types.ts
├── transaction.types.ts
└── trading.types.ts
```

### `utils/` – Utility & Configuration
```
utils/
├── axiosConfig.ts
└── constants.ts
```

### `routes/` – Application Routing
```
routes/
└── AppRoutes.tsx
```

### Core Files
`App.tsx` (root component), `main.tsx` (entry point)

### `public/`
`index.html` and static assets.

### Configuration
`tsconfig.json`, `package.json`, `README.md`

## ✅ Architecture Benefits
Feature-based, scalable, clean separation of concerns, secure role-based routing—fit for FinTech/banking applications.
