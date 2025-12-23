import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const Bar = styled.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1.25rem;
  background: #0d1b2a;
  color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
  position: sticky;
  top: 0;
  z-index: 1000;
`;

const Brand = styled(Link)`
  color: #fff;
  font-weight: 700;
  letter-spacing: 0.5px;
  text-decoration: none;
`;

const Links = styled.div`
  display: flex;
  gap: 0.75rem;
`;

const Right = styled.div`
  display: flex;
  align-items: center;
  gap: 0.75rem;
`;

const NavLink = styled(Link)`
  color: #cde1ff;
  text-decoration: none;
  font-weight: 500;
  padding: 0.35rem 0.65rem;
  border-radius: 4px;
  transition: background 0.2s ease, color 0.2s ease;

  &:hover {
    background: rgba(255, 255, 255, 0.08);
    color: #fff;
  }
`;

const ProfileBadge = styled.div`
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #1e3a8a;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #e0f2fe;
  font-weight: 700;
  font-size: 1rem;
  cursor: pointer;
  border: 1px solid rgba(255, 255, 255, 0.2);
`;

const LogoutButton = styled.button`
  border: 1px solid rgba(255, 255, 255, 0.4);
  background: transparent;
  color: #fff;
  padding: 0.35rem 0.75rem;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s ease, border-color 0.2s ease;

  &:hover {
    background: rgba(255, 255, 255, 0.12);
    border-color: rgba(255, 255, 255, 0.6);
  }
`;

const Menu = styled.div`
  position: absolute;
  top: 54px;
  right: 0;
  background: #0b1220;
  border: 1px solid rgba(226, 232, 240, 0.12);
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
  min-width: 220px;
  padding: 0.75rem;
  z-index: 1100;
`;

const MenuItem = styled.button`
  width: 100%;
  text-align: left;
  background: transparent;
  border: none;
  color: #e2e8f0;
  padding: 0.55rem 0.4rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;

  &:hover {
    background: rgba(255, 255, 255, 0.08);
  }
`;

const MenuLabel = styled.p`
  margin: 0 0 0.5rem;
  color: #94a3b8;
  font-size: 0.9rem;
  font-weight: 600;
`;

const MenuText = styled.p`
  margin: 0.15rem 0;
  color: #e2e8f0;
  font-size: 0.95rem;
  font-weight: 600;
`;

const MenuSubText = styled.p`
  margin: 0;
  color: #94a3b8;
  font-size: 0.85rem;
`;

type Props = {
  theme: 'dark' | 'light';
  onToggleTheme: () => void;
};

const Navbar: React.FC<Props> = ({ theme, onToggleTheme }) => {
  const navigate = useNavigate();
  const isAuthed = !!localStorage.getItem('jwtToken');
  const [open, setOpen] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('refreshToken');
    navigate('/login');
  };

  const initials = 'VC';
  const name = 'VaultCore User';
  const email = 'user@vaultcore.app';

  return (
    <Bar>
      <Brand to="/">VaultCore</Brand>
      <Links>
        <NavLink to="/send-money">Send Money</NavLink>
        <NavLink to="/create-account">Create Account</NavLink>
        <NavLink to="/transactions">History</NavLink>
        {!isAuthed && <NavLink to="/login">Login</NavLink>}
        {!isAuthed && <NavLink to="/signup">Sign Up</NavLink>}
      </Links>
      <Right style={{ position: 'relative' }}>
        <ProfileBadge onClick={() => setOpen((v) => !v)}>{initials}</ProfileBadge>
        {open && (
          <Menu>
            <MenuLabel>User</MenuLabel>
            <MenuText>{name}</MenuText>
            <MenuSubText>{email}</MenuSubText>
            <div style={{ height: 10 }} />
            <MenuItem onClick={() => { onToggleTheme(); setOpen(false); }}>
              Toggle Theme ({theme === 'dark' ? 'Light' : 'Dark'})
            </MenuItem>
            <MenuItem onClick={() => { navigate('/create-account'); setOpen(false); }}>Create Account</MenuItem>
            <MenuItem onClick={() => { navigate('/transactions'); setOpen(false); }}>View History</MenuItem>
            {isAuthed && <MenuItem onClick={handleLogout}>Logout</MenuItem>}
            {!isAuthed && <MenuItem onClick={() => { navigate('/login'); setOpen(false); }}>Login</MenuItem>}
          </Menu>
        )}
        {isAuthed && <LogoutButton onClick={handleLogout}>Logout</LogoutButton>}
      </Right>
    </Bar>
  );
};

export default Navbar;

