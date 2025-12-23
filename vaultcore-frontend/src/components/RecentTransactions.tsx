import React from 'react';
import styled from 'styled-components';

const Card = styled.div`
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 18px rgba(15, 23, 42, 0.08);
  padding: 1.5rem;
  border: 1px solid #e2e8f0;
`;

const Title = styled.h3`
  margin: 0 0 1rem;
  color: #0f172a;
  font-size: 1.1rem;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

const Th = styled.th`
  text-align: left;
  font-size: 0.9rem;
  color: #64748b;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #e2e8f0;
`;

const Td = styled.td`
  padding: 0.6rem 0;
  font-size: 0.95rem;
  color: #0f172a;
  border-bottom: 1px solid #e2e8f0;
`;

const Amount = styled.span<{ positive: boolean }>`
  color: ${(p) => (p.positive ? '#16a34a' : '#ef4444')};
  font-weight: 700;
`;

type Txn = {
  date: string;
  description: string;
  amount: number;
};

type Props = {
  transactions?: Txn[];
};

const RecentTransactions: React.FC<Props> = ({ transactions }) => {
  const rows =
    transactions && transactions.length
      ? transactions
      : [
          { date: '23 Dec', description: 'Stock Buy - TCS', amount: -5000 },
          { date: '22 Dec', description: 'Salary Credit', amount: 50000 }
        ];

  return (
    <Card>
      <Title>Recent Transactions</Title>
      <Table>
        <thead>
          <tr>
            <Th>Date</Th>
            <Th>Description</Th>
            <Th style={{ textAlign: 'right' }}>Amount</Th>
          </tr>
        </thead>
        <tbody>
          {rows.map((txn, idx) => (
            <tr key={`${txn.description}-${idx}`}>
              <Td>{txn.date}</Td>
              <Td>{txn.description}</Td>
              <Td style={{ textAlign: 'right' }}>
                <Amount positive={txn.amount >= 0}>
                  {txn.amount >= 0 ? '+' : '-'} ${Math.abs(txn.amount).toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
                </Amount>
              </Td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Card>
  );
};

export default RecentTransactions;

