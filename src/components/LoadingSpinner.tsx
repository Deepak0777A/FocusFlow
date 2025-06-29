import React from 'react';

const LoadingSpinner: React.FC = () => {
  return (
    <div className="min-h-screen bg-white flex items-center justify-center">
      <div className="bg-white border border-[#FF6363]/20 rounded-2xl p-8 shadow-xl">
        <div className="flex flex-col items-center">
          <div className="animate-spin w-12 h-12 border-4 border-[#FF6363]/20 border-t-[#FF6363] rounded-full mb-4"></div>
          <p className="text-[#2D3748] font-medium">Loading FocusFlow...</p>
        </div>
      </div>
    </div>
  );
};

export default LoadingSpinner;