import React from 'react';
import styled from 'styled-components';

const Card = styled.div`
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 18px rgba(15, 23, 42, 0.08);
  padding: 1.5rem;
  border: 1px solid #e2e8f0;
`;

const Label = styled.p`
  margin: 0;
  color: #64748b;
  font-weight: 600;
  letter-spacing: 0.2px;
`;

const Amount = styled.p`
  margin: 0.35rem 0 0;
  color: #16a34a;
  font-size: 2rem;
  font-weight: 800;
`;

const Sub = styled.p`
  margin: 0.25rem 0 0;
  color: #94a3b8;
  font-size: 0.9rem;
`;

type Props = {
  totalBalance: number;
};

const BalanceCard: React.FC<Props> = ({ totalBalance }) => {
  return (
    <Card>
      <Label>Available Balance</Label>
      <Amount>${totalBalance.toFixed(2)}</Amount>
      <Sub>Updated just now</Sub>
    </Card>
  );
};

export default BalanceCard;

