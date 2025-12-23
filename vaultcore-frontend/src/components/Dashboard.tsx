import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import api from '../services/api';
import BalanceCard from './BalanceCard';
import RecentTransactions from './RecentTransactions';
import AccountDetails from './AccountDetails';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100vh;
  background: linear-gradient(180deg, #0f172a 0%, #111827 120%);
  padding: 3.5rem 2rem 3rem;
  box-sizing: border-box;
  color: #e2e8f0;
`;

const Card = styled.div`
  background: #0b1220;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.35);
  width: 100%;
  max-width: 1200px;
  border: 1px solid rgba(226, 232, 240, 0.08);
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1rem;
`;

const Title = styled.h2`
  color: #e2e8f0;
  margin: 0;
  text-align: left;
`;

const AccountsList = styled.div`
  margin: 1.5rem 0;
`;

const Grid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 1rem;
  margin: 1.5rem 0;
`;

const TwoCol = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 1rem;
  margin: 1rem 0;
`;

const StatsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1rem;
  margin: 1rem 0 1.5rem;
`;

const StatCard = styled.div`
  background: #0f172a;
  border: 1px solid rgba(226, 232, 240, 0.08);
  border-radius: 12px;
  padding: 1rem 1.25rem;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.25);
`;

const StatLabel = styled.p`
  margin: 0;
  color: #94a3b8;
  font-size: 0.9rem;
`;

const StatValue = styled.p`
  margin: 0.4rem 0 0;
  color: #22c55e;
  font-weight: 800;
  font-size: 1.6rem;
`;

const MainContent = styled.div`
  background: #0f172a;
  border: 1px solid rgba(226, 232, 240, 0.08);
  border-radius: 14px;
  padding: 1.5rem;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.28);
  margin: 1rem 0;
`;

const SectionTitle = styled.h3`
  margin: 0 0 1rem;
  color: #e2e8f0;
`;

const QuickActions = styled.div`
  background: #0f172a;
  border: 1px solid rgba(226, 232, 240, 0.08);
  border-radius: 14px;
  padding: 1rem 1.25rem;
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.28);
`;

const ActionButton = styled.button`
  background: #1e3a8a;
  color: #e0f2fe;
  border: 1px solid rgba(226, 232, 240, 0.1);
  padding: 0.65rem 1rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: transform 0.1s ease, box-shadow 0.2s ease, background 0.2s ease;

  &:hover {
    background: #1d4ed8;
    transform: translateY(-1px);
    box-shadow: 0 6px 14px rgba(0, 0, 0, 0.3);
  }
`;

const AccountItem = styled.div`
  padding: 1rem;
  border: 1px solid rgba(226, 232, 240, 0.08);
  border-radius: 10px;
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #0f172a;
  color: #e2e8f0;
`;

const AccountInfo = styled.div`
  flex: 1;
`;

const AccountType = styled.h3`
  margin: 0 0 0.5rem 0;
  color: #e2e8f0;
`;

const Balance = styled.p`
  margin: 0;
  color: #cbd5e1;
  font-size: 1.1rem;
  font-weight: bold;
`;

interface Account {
  id: string;
  accountType: string;
  balance: number;
}

const Dashboard: React.FC = () => {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAccounts();
  }, []);

  const fetchAccounts = async () => {
    try {
      const response = await api.get('/accounts');
      setAccounts(response.data);
    } catch (err: any) {
      console.error('Failed to fetch accounts:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      <Card>
        <Header>
          <Title>VaultCore Dashboard</Title>
        </Header>

        <Grid>
          <BalanceCard totalBalance={accounts.reduce((sum, a) => sum + a.balance, 0)} />
          <AccountDetails />
        </Grid>

        <StatsGrid>
          <StatCard>
            <StatLabel>Total Balance</StatLabel>
            <StatValue>${accounts.reduce((s, a) => s + a.balance, 0).toFixed(2)}</StatValue>
          </StatCard>
          <StatCard>
            <StatLabel>Accounts</StatLabel>
            <StatValue>{accounts.length}</StatValue>
          </StatCard>
          <StatCard>
            <StatLabel>Recent Transactions (demo)</StatLabel>
            <StatValue>2</StatValue>
          </StatCard>
          <StatCard>
            <StatLabel>Status</StatLabel>
            <StatValue style={{ color: '#22c55e' }}>Active</StatValue>
          </StatCard>
        </StatsGrid>

        <TwoCol>
          <div style={{ background: '#f8fafc', border: '1px dashed #cbd5e1', borderRadius: 12, padding: '1rem', color: '#475569' }}>
            Portfolio / Account Summary slot
          </div>
          <div style={{ background: '#f8fafc', border: '1px dashed #cbd5e1', borderRadius: 12, padding: '1rem', color: '#475569' }}>
            Additional insights slot
          </div>
        </TwoCol>

        <MainContent>
          <SectionTitle>Main Activity</SectionTitle>
          <RecentTransactions />
        </MainContent>

        <QuickActions>
          <ActionButton>Send</ActionButton>
          <ActionButton>Book</ActionButton>
          <ActionButton>Message</ActionButton>
        </QuickActions>

        <AccountsList>
          <h3 style={{ marginBottom: '1rem', color: '#e2e8f0' }}>Your Accounts</h3>
          {loading ? (
            <p>Loading accounts...</p>
          ) : accounts.length === 0 ? (
            <p style={{ color: '#cbd5e1' }}>No accounts found. Please create an account.</p>
          ) : (
            accounts.map((account) => (
              <AccountItem key={account.id}>
                <AccountInfo>
                  <AccountType>{account.accountType}</AccountType>
                  <Balance>Balance: ${account.balance.toFixed(2)}</Balance>
                </AccountInfo>
              </AccountItem>
            ))
          )}
        </AccountsList>
      </Card>
    </Container>
  );
};

export default Dashboard;