import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import api from '../services/api';

const Container = styled.div`
  min-height: 100vh;
  background: linear-gradient(180deg, #0f172a 0%, #111827 120%);
  padding: 3.5rem 2rem 3rem;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
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

const Title = styled.h2`
  margin: 0 0 1.5rem;
  color: #e2e8f0;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

const Th = styled.th`
  text-align: left;
  padding: 0.75rem 0.5rem;
  color: #94a3b8;
  font-weight: 700;
  border-bottom: 1px solid rgba(226, 232, 240, 0.12);
`;

const Td = styled.td`
  padding: 0.75rem 0.5rem;
  border-bottom: 1px solid rgba(226, 232, 240, 0.08);
  color: #e2e8f0;
  font-size: 0.95rem;
`;

const Amount = styled.span<{ positive: boolean }>`
  color: ${(p) => (p.positive ? '#22c55e' : '#ef4444')};
  font-weight: 700;
`;

const Badge = styled.span<{ kind: 'credit' | 'debit' }>`
  padding: 0.25rem 0.55rem;
  border-radius: 999px;
  background: ${(p) => (p.kind === 'credit' ? 'rgba(34, 197, 94, 0.12)' : 'rgba(239, 68, 68, 0.12)')};
  color: ${(p) => (p.kind === 'credit' ? '#22c55e' : '#ef4444')};
  font-weight: 700;
  font-size: 0.85rem;
`;

const EmptyState = styled.div`
  padding: 2rem 1rem;
  text-align: center;
  color: #94a3b8;
`;

interface Txn {
  id: string;
  date: string;
  description: string;
  amount: number;
  type: 'CREDIT' | 'DEBIT';
}

const TransactionHistoryPage: React.FC = () => {
  const [txns, setTxns] = useState<Txn[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/transactions');
        setTxns(res.data || []);
      } catch (err: any) {
        setError('Failed to load transactions.');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  return (
    <Container>
      <Card>
        <Title>Transaction History</Title>
        {loading ? (
          <EmptyState>Loading transactions...</EmptyState>
        ) : error ? (
          <EmptyState>{error}</EmptyState>
        ) : txns.length === 0 ? (
          <EmptyState>No transactions found.</EmptyState>
        ) : (
          <Table>
            <thead>
              <tr>
                <Th>Date</Th>
                <Th>Description</Th>
                <Th>Type</Th>
                <Th style={{ textAlign: 'right' }}>Amount</Th>
              </tr>
            </thead>
            <tbody>
              {txns.map((t) => (
                <tr key={t.id}>
                  <Td>{t.date}</Td>
                  <Td>{t.description}</Td>
                  <Td>
                    <Badge kind={t.type === 'CREDIT' ? 'credit' : 'debit'}>
                      {t.type === 'CREDIT' ? 'Credit' : 'Debit'}
                    </Badge>
                  </Td>
                  <Td style={{ textAlign: 'right' }}>
                    <Amount positive={t.amount >= 0}>
                      {t.amount >= 0 ? '+' : '-'} ${Math.abs(t.amount).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
                    </Amount>
                  </Td>
                </tr>
              ))}
            </tbody>
          </Table>
        )}
      </Card>
    </Container>
  );
};

export default TransactionHistoryPage;


