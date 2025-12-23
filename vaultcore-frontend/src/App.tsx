import React from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Dashboard from './components/Dashboard';
import SendMoney from './components/SendMoney';
import Navbar from './components/Navbar';
import TransactionHistoryPage from './components/TransactionHistoryPage';
import CreateAccount from './components/CreateAccount';
import styled from 'styled-components';

const Container = styled.div`
  text-align: center;
`;

function App() {
  const location = useLocation();
  const isAuthed = !!localStorage.getItem('jwtToken');
  const [theme, setTheme] = useState<'dark' | 'light'>(() => {
    const stored = localStorage.getItem('theme');
    return stored === 'light' ? 'light' : 'dark';
  });

  useEffect(() => {
    document.body.dataset.theme = theme;
    document.body.style.background = theme === 'dark' ? '#0f172a' : '#f5f7fb';
    document.body.style.color = theme === 'dark' ? '#e2e8f0' : '#0f172a';
    localStorage.setItem('theme', theme);
  }, [theme]);

  const toggleTheme = () => setTheme((t) => (t === 'dark' ? 'light' : 'dark'));

  const RequireAuth = ({ children }: { children: JSX.Element }) =>
    isAuthed ? children : <Navigate to="/login" replace state={{ from: location.pathname }} />;

  const PublicOnly = ({ children }: { children: JSX.Element }) =>
    isAuthed ? <Navigate to="/dashboard" replace /> : children;

  return (
    <Container>
      <Navbar theme={theme} onToggleTheme={toggleTheme} />
      <Routes>
        <Route
          path="/login"
          element={
            <PublicOnly>
              <SignIn />
            </PublicOnly>
          }
        />
        <Route
          path="/signup"
          element={
            <PublicOnly>
              <SignUp />
            </PublicOnly>
          }
        />
        <Route
          path="/dashboard"
          element={
            <RequireAuth>
              <Dashboard />
            </RequireAuth>
          }
        />
        <Route
          path="/send-money"
          element={
            <RequireAuth>
              <SendMoney />
            </RequireAuth>
          }
        />
        <Route
          path="/transactions"
          element={
            <RequireAuth>
              <TransactionHistoryPage />
            </RequireAuth>
          }
        />
        <Route
          path="/create-account"
          element={
            <RequireAuth>
              <CreateAccount />
            </RequireAuth>
          }
        />
        <Route path="/" element={<Navigate to={isAuthed ? '/dashboard' : '/login'} replace />} />
        <Route path="*" element={<Navigate to={isAuthed ? '/dashboard' : '/login'} replace />} />
      </Routes>
    </Container>
  );
}

export default App;
