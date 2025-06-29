import React from 'react';
import { useTask } from '../contexts/TaskContext';
import { Task, TaskStatus } from '../types/task';
import { 
  Calendar, 
  Flag, 
  Share2, 
  Edit3, 
  Trash2, 
  Clock,
  CheckCircle2,
  Circle,
  PlayCircle
} from 'lucide-react';
import { format, isToday, isTomorrow, isPast } from 'date-fns';
import toast from 'react-hot-toast';

interface TaskCardProps {
  task: Task;
  onEdit: () => void;
  onShare: () => void;
}

const TaskCard: React.FC<TaskCardProps> = ({ task, onEdit, onShare }) => {
  const { updateTask, deleteTask } = useTask();

  const handleStatusChange = (newStatus: TaskStatus) => {
    updateTask(task.id, { status: newStatus });
  };

  const handleDelete = () => {
    if (confirm('Are you sure you want to delete this task?')) {
      deleteTask(task.id);
    }
  };

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case 'high': return 'text-red-700 bg-red-100';
      case 'medium': return 'text-orange-700 bg-orange-100';
      case 'low': return 'text-[#FF6363] bg-[#FF6363]/10';
      default: return 'text-gray-600 bg-gray-100';
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'completed': return 'text-green-700 bg-green-100';
      case 'in-progress': return 'text-[#FF6363] bg-[#FF6363]/10';
      case 'pending': return 'text-gray-600 bg-gray-100';
      default: return 'text-gray-600 bg-gray-100';
    }
  };

  const formatDueDate = (dueDate: string) => {
    const date = new Date(dueDate);
    if (isToday(date)) return 'Today';
    if (isTomorrow(date)) return 'Tomorrow';
    return format(date, 'MMM d');
  };

  const getDueDateColor = (dueDate: string) => {
    const date = new Date(dueDate);
    if (isPast(date) && !isToday(date)) return 'text-red-600';
    if (isToday(date)) return 'text-orange-600';
    return 'text-black/70';
  };

  const getStatusIcon = () => {
    switch (task.status) {
      case 'completed':
        return <CheckCircle2 className="w-5 h-5 text-green-600" />;
      case 'in-progress':
        return <PlayCircle className="w-5 h-5 text-[#FF6363]" />;
      default:
        return <Circle className="w-5 h-5 text-gray-400" />;
    }
  };

  return (
    <div className="bg-white border border-[#FF6363]/20 rounded-xl p-6 hover:shadow-lg hover:border-[#FF6363]/40 transition-all duration-200 group">
      <div className="flex items-start justify-between">
        <div className="flex items-start space-x-4 flex-1">
          <button
            onClick={() => {
              const nextStatus = task.status === 'pending' ? 'in-progress' : 
                              task.status === 'in-progress' ? 'completed' : 'pending';
              handleStatusChange(nextStatus);
            }}
            className="mt-1 hover:scale-110 transition-transform duration-200"
          >
            {getStatusIcon()}
          </button>

          <div className="flex-1 min-w-0">
            <div className="flex items-center flex-wrap gap-2 mb-2">
              <h3 className={`font-semibold text-black ${task.status === 'completed' ? 'line-through' : ''}`}>
                {task.title}
              </h3>
              <span className={`px-2 py-1 rounded-full text-xs font-medium ${getPriorityColor(task.priority)}`}>
                {task.priority}
              </span>
              <span className={`px-2 py-1 rounded-full text-xs font-medium ${getStatusColor(task.status)}`}>
                {task.status.replace('-', ' ')}
              </span>
            </div>

            {task.description && (
              <p className={`text-black/80 text-sm mb-3 ${task.status === 'completed' ? 'line-through' : ''}`}>
                {task.description}
              </p>
            )}

            <div className="flex items-center space-x-4 text-sm">
              {task.dueDate && (
                <div className={`flex items-center ${getDueDateColor(task.dueDate)}`}>
                  <Calendar className="w-4 h-4 mr-1" />
                  <span>{formatDueDate(task.dueDate)}</span>
                </div>
              )}
              
              <div className="flex items-center text-black/60">
                <Clock className="w-4 h-4 mr-1" />
                <span>Created {format(new Date(task.createdAt), 'MMM d')}</span>
              </div>

              {task.sharedWith && task.sharedWith.length > 0 && (
                <div className="flex items-center text-[#FF6363]">
                  <Share2 className="w-4 h-4 mr-1" />
                  <span>Shared ({task.sharedWith.length})</span>
                </div>
              )}
            </div>
          </div>
        </div>

        <div className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 flex items-center space-x-2 ml-4">
          <button
            onClick={onShare}
            className="p-2 text-black/60 hover:text-[#FF6363] hover:bg-[#FF6363]/10 rounded-lg transition-colors duration-200"
            title="Share task"
          >
            <Share2 className="w-4 h-4" />
          </button>
          <button
            onClick={onEdit}
            className="p-2 text-black/60 hover:text-[#FF6363] hover:bg-[#FF6363]/10 rounded-lg transition-colors duration-200"
            title="Edit task"
          >
            <Edit3 className="w-4 h-4" />
          </button>
          <button
            onClick={handleDelete}
            className="p-2 text-black/60 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors duration-200"
            title="Delete task"
          >
            <Trash2 className="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default TaskCard;