import React from 'react';
import styled from 'styled-components';

const Card = styled.div`
  background: white;
  border-radius: 12px;
  box-shadow: 0 6px 18px rgba(15, 23, 42, 0.08);
  padding: 1.25rem 1.5rem;
  border: 1px solid #e2e8f0;
`;

const Title = styled.h3`
  margin: 0 0 0.75rem;
  color: #64748b;
  font-size: 0.95rem;
  letter-spacing: 0.2px;
`;

const Item = styled.p`
  margin: 0.2rem 0;
  color: #0f172a;
  font-size: 0.95rem;
`;

const Label = styled.span`
  font-weight: 700;
  margin-right: 0.25rem;
  color: #1e293b;
`;

const Status = styled.span<{ active?: boolean }>`
  color: ${(p) => (p.active ? '#16a34a' : '#ef4444')};
  font-weight: 700;
  margin-left: 0.25rem;
`;

type Props = {
  accountNumber?: string;
  accountType?: string;
  branch?: string;
  active?: boolean;
};

const AccountDetails: React.FC<Props> = ({
  accountNumber = '1234567890',
  accountType = 'Savings',
  branch = 'Hyderabad',
  active = true
}) => {
  return (
    <Card>
      <Title>Account Details</Title>
      <Item>
        <Label>Account No:</Label>
        {accountNumber}
      </Item>
      <Item>
        <Label>Account Type:</Label>
        {accountType}
      </Item>
      <Item>
        <Label>Branch:</Label>
        {branch}
      </Item>
      <Item>
        <Label>Status:</Label>
        <Status active={active}>{active ? 'Active' : 'Inactive'}</Status>
      </Item>
    </Card>
  );
};

export default AccountDetails;

