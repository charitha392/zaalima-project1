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

const Header = styled.div`
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 0.75rem;
`;

const Avatar = styled.div<{ imageUrl?: string }>`
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: ${(p) => (p.imageUrl ? `url(${p.imageUrl}) center/cover` : '#e2e8f0')};
  display: flex;
  align-items: center;
  justify-content: center;
  color: #0f172a;
  font-weight: 800;
  letter-spacing: 0.5px;
  text-transform: uppercase;
`;

const Name = styled.p`
  margin: 0;
  color: #0f172a;
  font-weight: 700;
  font-size: 1rem;
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

type Props = {
  name?: string;
  email?: string;
  role?: string;
  phone?: string;
  avatarUrl?: string;
};

const UserDetails: React.FC<Props> = ({
  name = 'Anil Kumar',
  email = 'anil@gmail.com',
  role = 'USER',
  phone = '+91 98765 43210',
  avatarUrl
}) => {
  const initials = name
    .split(' ')
    .map((n) => n[0])
    .join('')
    .slice(0, 2);

  return (
    <Card>
      <Title>User Details</Title>
      <Header>
        <Avatar imageUrl={avatarUrl}>{!avatarUrl && initials}</Avatar>
        <div>
          <Name>{name}</Name>
          <Item style={{ margin: 0, color: '#475569', fontSize: '0.9rem' }}>{email}</Item>
        </div>
      </Header>
      <Item>
        <Label>Name:</Label>
        {name}
      </Item>
      <Item>
        <Label>Email:</Label>
        {email}
      </Item>
      <Item>
        <Label>Phone:</Label>
        {phone}
      </Item>
      <Item>
        <Label>Role:</Label>
        {role}
      </Item>
    </Card>
  );
};

export default UserDetails;

