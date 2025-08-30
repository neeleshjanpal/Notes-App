import React from 'react';
import { ThemeProvider, CssBaseline } from '@mui/material';
import { theme } from './theme';
import { AuthProvider } from './context/AuthContext';
import { AuthGuard } from './components/auth/AuthGuard';
import { Header } from './components/layout/Header';
import { NotesPage } from './components/notes/NotesPage';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <AuthGuard>
          <Header />
          <NotesPage />
        </AuthGuard>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;