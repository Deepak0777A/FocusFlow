import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import { LogOut, Zap } from 'lucide-react';
import toast from 'react-hot-toast';

const Header: React.FC = () => {
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    toast.success('Logged out successfully');
  };

  return (
    <header className="bg-white/90 backdrop-blur-md border-b border-[#FF6363]/20 sticky top-0 z-40">
      <div className="px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Left section - Logo */}
          <div className="flex items-center">
            <div className="bg-gradient-to-r from-[#FF6363] to-[#FF8A8A] p-2 rounded-xl mr-3">
              <Zap className="w-6 h-6 text-white" />
            </div>
            <h1 className="text-xl font-bold text-[#2D3748]">
              FocusFlow
            </h1>
          </div>

          {/* Right section - User info and logout */}
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-3">
              <img
                src={user?.avatar}
                alt={user?.name}
                className="w-8 h-8 rounded-full ring-2 ring-[#FF6363]/30"
              />
              <div className="hidden sm:block">
                <p className="text-sm font-medium text-[#2D3748]">{user?.name}</p>
                <p className="text-xs text-[#4A5568]">{user?.email}</p>
              </div>
            </div>

            <button
              onClick={handleLogout}
              className="p-2 text-[#4A5568] hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors duration-200"
              title="Logout"
            >
              <LogOut className="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;