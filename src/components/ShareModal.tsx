import React, { useState } from 'react';
import { useTask } from '../contexts/TaskContext';
import { X, Mail, Send, Users } from 'lucide-react';

interface ShareModalProps {
  isOpen: boolean;
  onClose: () => void;
  taskId: string | null;
}

const ShareModal: React.FC<ShareModalProps> = ({ isOpen, onClose, taskId }) => {
  const { tasks, shareTask } = useTask();
  const [email, setEmail] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const task = tasks.find(t => t.id === taskId);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!email.trim() || !taskId) return;

    setIsSubmitting(true);
    
    try {
      shareTask(taskId, email.trim());
      setEmail('');
      onClose();
    } catch (error) {
      console.error('Failed to share task:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (!isOpen || !task) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md">
        <div className="flex items-center justify-between p-6 border-b border-[#FF6363]/20">
          <h2 className="text-xl font-bold text-[#2D3748]">Share Task</h2>
          <button
            onClick={onClose}
            className="p-2 text-[#4A5568] hover:text-[#2D3748] hover:bg-[#FF6363]/10 rounded-lg transition-colors duration-200"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <div className="p-6">
          <div className="mb-6">
            <h3 className="font-medium text-[#2D3748] mb-2">{task.title}</h3>
            <p className="text-sm text-[#4A5568]">{task.description}</p>
          </div>

          {task.sharedWith && task.sharedWith.length > 0 && (
            <div className="mb-6">
              <div className="flex items-center text-sm font-medium text-[#2D3748] mb-3">
                <Users className="w-4 h-4 mr-2" />
                Shared with ({task.sharedWith.length})
              </div>
              <div className="space-y-2">
                {task.sharedWith.map((email, index) => (
                  <div key={index} className="flex items-center px-3 py-2 bg-[#FF6363]/10 rounded-lg">
                    <Mail className="w-4 h-4 text-[#4A5568] mr-2" />
                    <span className="text-sm text-[#2D3748]">{email}</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <label htmlFor="email" className="flex items-center text-sm font-medium text-[#2D3748] mb-2">
              <Mail className="w-4 h-4 mr-2" />
              Share with email
            </label>
            <div className="flex space-x-3">
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Enter email address..."
                className="flex-1 px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
                required
              />
              <button
                type="submit"
                disabled={isSubmitting}
                className="flex items-center px-4 py-3 bg-gradient-to-r from-[#FF6363] to-[#FF8A8A] text-white rounded-xl hover:from-[#FF4444] hover:to-[#FF6363] disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 font-medium"
              >
                <Send className="w-4 h-4" />
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ShareModal;