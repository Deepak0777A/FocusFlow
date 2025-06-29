import React, { useState } from 'react';
import Header from '../components/Header';
import TaskList from '../components/TaskList';
import TaskModal from '../components/TaskModal';
import ShareModal from '../components/ShareModal';
import FilterPanel from '../components/FilterPanel';
import { Plus } from 'lucide-react';
import { Task } from '../types/task';

const Dashboard: React.FC = () => {
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);
  const [isShareModalOpen, setIsShareModalOpen] = useState(false);
  const [editingTask, setEditingTask] = useState<Task | null>(null);
  const [sharingTaskId, setSharingTaskId] = useState<string | null>(null);

  const handleCreateTask = () => {
    setEditingTask(null);
    setIsTaskModalOpen(true);
  };

  const handleEditTask = (task: Task) => {
    setEditingTask(task);
    setIsTaskModalOpen(true);
  };

  const handleShareTask = (taskId: string) => {
    setSharingTaskId(taskId);
    setIsShareModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsTaskModalOpen(false);
    setEditingTask(null);
  };

  const handleCloseShareModal = () => {
    setIsShareModalOpen(false);
    setSharingTaskId(null);
  };

  return (
    <div className="min-h-screen bg-white">
      <Header />
      
      <main className="transition-all duration-300">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="mb-8">
            <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
              <div>
                <h1 className="text-3xl font-bold text-[#2D3748] mb-2">Dashboard</h1>
                <p className="text-[#4A5568]">Manage your tasks and stay productive</p>
              </div>
              <button
                onClick={handleCreateTask}
                className="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#FF6363] to-[#FF8A8A] text-white rounded-xl hover:from-[#FF4444] hover:to-[#FF6363] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#FF6363] transition-all duration-200 transform hover:scale-105 shadow-lg font-medium"
              >
                <Plus className="w-5 h-5 mr-2" />
                New Task
              </button>
            </div>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
            <div className="lg:col-span-1">
              <FilterPanel />
            </div>
            <div className="lg:col-span-3">
              <TaskList 
                onEditTask={handleEditTask}
                onShareTask={handleShareTask}
              />
            </div>
          </div>
        </div>
      </main>

      <TaskModal
        isOpen={isTaskModalOpen}
        onClose={handleCloseModal}
        task={editingTask}
      />

      <ShareModal
        isOpen={isShareModalOpen}
        onClose={handleCloseShareModal}
        taskId={sharingTaskId}
      />
    </div>
  );
};

export default Dashboard;