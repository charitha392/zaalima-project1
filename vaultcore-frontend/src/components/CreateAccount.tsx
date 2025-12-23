import React, { useState } from 'react';
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
  max-width: 520px;
  border: 1px solid rgba(226, 232, 240, 0.08);
`;

const Title = styled.h2`
  margin: 0 0 1.25rem;
  color: #e2e8f0;
  text-align: center;
`;

const FormGroup = styled.div`
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.35rem;
`;

const Label = styled.label`
  color: #cbd5e1;
  font-weight: 600;
`;

const Input = styled.input`
  width: 100%;
  padding: 0.8rem;
  border-radius: 10px;
  border: 1px solid rgba(226, 232, 240, 0.15);
  background: #0f172a;
  color: #e2e8f0;
  font-size: 0.95rem;
  box-sizing: border-box;
`;

const Button = styled.button`
  width: 100%;
  padding: 0.85rem;
  background: #1e3a8a;
  color: #e0f2fe;
  border: 1px solid rgba(226, 232, 240, 0.12);
  border-radius: 12px;
  cursor: pointer;
  font-weight: 700;
  margin-top: 0.5rem;
  transition: transform 0.1s ease, box-shadow 0.2s ease, background 0.2s ease;

  &:hover {
    background: #1d4ed8;
    transform: translateY(-1px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
    box-shadow: none;
  }
`;

const Message = styled.p<{ error?: boolean }>`
  color: ${(p) => (p.error ? '#ef4444' : '#22c55e')};
  text-align: center;
  margin: 0.75rem 0 0;
  font-weight: 600;
`;

const CreateAccount: React.FC = () => {
  const [accountNumber, setAccountNumber] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [ifsc, setIfsc] = useState('');
  const [balance, setBalance] = useState('');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState<{ text: string; error: boolean } | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage(null);
    if (!accountNumber || !username || !password || !balance || !ifsc) {
      setMessage({ text: 'All fields are required', error: true });
      return;
    }
    const initialBalance = parseFloat(balance);
    if (Number.isNaN(initialBalance) || initialBalance < 0) {
      setMessage({ text: 'Balance must be zero or greater', error: true });
      return;
    }

    setLoading(true);
    try {
      await api.post('/accounts', {
        accountNumber,
        username,
        password,
        ifsc,
        balance: initialBalance
      });
      setMessage({ text: 'Account created successfully', error: false });
      setAccountNumber('');
      setUsername('');
      setPassword('');
      setIfsc('');
      setBalance('');
    } catch (err: any) {
      const errMsg = err?.response?.data?.message || 'Failed to create account';
      setMessage({ text: errMsg, error: true });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      <Card>
        <Title>Create Bank Account</Title>
        <form onSubmit={handleSubmit}>
          <FormGroup>
            <Label>Account Number</Label>
            <Input
              value={accountNumber}
              onChange={(e) => setAccountNumber(e.target.value)}
              placeholder="Enter account number"
              required
            />
          </FormGroup>
          <FormGroup>
            <Label>User Name</Label>
            <Input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter user name"
              required
            />
          </FormGroup>
          <FormGroup>
            <Label>Password</Label>
            <Input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Set account password"
              required
            />
          </FormGroup>
          <FormGroup>
            <Label>IFSC Code</Label>
            <Input
              value={ifsc}
              onChange={(e) => setIfsc(e.target.value.toUpperCase())}
              placeholder="e.g., HDFC0001234"
              required
            />
          </FormGroup>
          <FormGroup>
            <Label>Initial Balance</Label>
            <Input
              type="number"
              min="0"
              step="0.01"
              value={balance}
              onChange={(e) => setBalance(e.target.value)}
              placeholder="0.00"
              required
            />
          </FormGroup>
          <Button type="submit" disabled={loading}>
            {loading ? 'Creating...' : 'Create Account'}
          </Button>
          {message && <Message error={message.error}>{message.text}</Message>}
        </form>
      </Card>
    </Container>
  );
};

export default CreateAccount;

