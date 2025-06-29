import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import { AuthProvider } from './contexts/AuthContext';
import { TaskProvider } from './contexts/TaskContext';
import ErrorBoundary from './components/ErrorBoundary';
import ProtectedRoute from './components/ProtectedRoute';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import OfflineIndicator from './components/OfflineIndicator';

function App() {
  return (
    <ErrorBoundary>
      <AuthProvider>
        <TaskProvider>
          <Router>
            <div className="min-h-screen bg-white">
              <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route
                  path="/dashboard"
                  element={
                    <ProtectedRoute>
                      <Dashboard />
                    </ProtectedRoute>
                  }
                />
                <Route path="/" element={<Navigate to="/dashboard" replace />} />
              </Routes>
              <Toaster
                position="top-right"
                toastOptions={{
                  duration: 4000,
                  style: {
                    background: '#FF6363',
                    color: '#FFFFFF',
                    borderRadius: '12px',
                    padding: '16px',
                    fontSize: '14px',
                    boxShadow: '0 20px 25px -5px rgba(255, 99, 99, 0.3), 0 10px 10px -5px rgba(255, 99, 99, 0.2)',
                  },
                }}
              />
              <OfflineIndicator />
            </div>
          </Router>
        </TaskProvider>
      </AuthProvider>
    </ErrorBoundary>
  );
}

export default App;