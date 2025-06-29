import React, { useState, useEffect } from 'react';
import { useTask } from '../contexts/TaskContext';
import { useAuth } from '../contexts/AuthContext';
import { Task, TaskStatus, TaskPriority } from '../types/task';
import { X, Calendar, Flag, FileText, Save } from 'lucide-react';

interface TaskModalProps {
  isOpen: boolean;
  onClose: () => void;
  task?: Task | null;
}

const TaskModal: React.FC<TaskModalProps> = ({ isOpen, onClose, task }) => {
  const { addTask, updateTask } = useTask();
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    status: 'pending' as TaskStatus,
    priority: 'medium' as TaskPriority,
    dueDate: '',
  });

  useEffect(() => {
    if (task) {
      setFormData({
        title: task.title,
        description: task.description,
        status: task.status,
        priority: task.priority,
        dueDate: task.dueDate ? task.dueDate.split('T')[0] : '',
      });
    } else {
      setFormData({
        title: '',
        description: '',
        status: 'pending',
        priority: 'medium',
        dueDate: '',
      });
    }
  }, [task, isOpen]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.title.trim()) return;

    const taskData = {
      ...formData,
      dueDate: formData.dueDate ? new Date(formData.dueDate).toISOString() : undefined,
      assignedTo: user?.id || '',
      sharedWith: task?.sharedWith || [],
    };

    if (task) {
      updateTask(task.id, taskData);
    } else {
      addTask(taskData);
    }

    onClose();
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-2xl shadow-2xl w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between p-6 border-b border-[#FF6363]/20">
          <h2 className="text-2xl font-bold text-[#2D3748]">
            {task ? 'Edit Task' : 'Create New Task'}
          </h2>
          <button
            onClick={onClose}
            className="p-2 text-[#4A5568] hover:text-[#2D3748] hover:bg-[#FF6363]/10 rounded-lg transition-colors duration-200"
          >
            <X className="w-6 h-6" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6 space-y-6">
          {/* Title */}
          <div>
            <label htmlFor="title" className="flex items-center text-sm font-medium text-[#2D3748] mb-2">
              <FileText className="w-4 h-4 mr-2" />
              Title *
            </label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
              className="w-full px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
              placeholder="Enter task title..."
            />
          </div>

          {/* Description */}
          <div>
            <label htmlFor="description" className="block text-sm font-medium text-[#2D3748] mb-2">
              Description
            </label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows={4}
              className="w-full px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 resize-none text-[#2D3748]"
              placeholder="Enter task description..."
            />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* Status */}
            <div>
              <label htmlFor="status" className="block text-sm font-medium text-[#2D3748] mb-2">
                Status
              </label>
              <select
                id="status"
                name="status"
                value={formData.status}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 bg-white text-[#2D3748]"
              >
                <option value="pending">Pending</option>
                <option value="in-progress">In Progress</option>
                <option value="completed">Completed</option>
              </select>
            </div>

            {/* Priority */}
            <div>
              <label htmlFor="priority" className="flex items-center text-sm font-medium text-[#2D3748] mb-2">
                <Flag className="w-4 h-4 mr-2" />
                Priority
              </label>
              <select
                id="priority"
                name="priority"
                value={formData.priority}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 bg-white text-[#2D3748]"
              >
                <option value="low">Low</option>
                <option value="medium">Medium</option>
                <option value="high">High</option>
              </select>
            </div>

            {/* Due Date */}
            <div>
              <label htmlFor="dueDate" className="flex items-center text-sm font-medium text-[#2D3748] mb-2">
                <Calendar className="w-4 h-4 mr-2" />
                Due Date
              </label>
              <input
                type="date"
                id="dueDate"
                name="dueDate"
                value={formData.dueDate}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-[#FF6363]/20 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
              />
            </div>
          </div>

          <div className="flex justify-end space-x-4 pt-6 border-t border-[#FF6363]/20">
            <button
              type="button"
              onClick={onClose}
              className="px-6 py-3 border border-[#FF6363]/20 text-[#2D3748] rounded-xl hover:bg-[#FF6363]/5 transition-colors duration-200 font-medium"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="flex items-center px-6 py-3 bg-gradient-to-r from-[#FF6363] to-[#FF8A8A] text-white rounded-xl hover:from-[#FF4444] hover:to-[#FF6363] transition-all duration-200 font-medium"
            >
              <Save className="w-4 h-4 mr-2" />
              {task ? 'Update Task' : 'Create Task'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default TaskModal;