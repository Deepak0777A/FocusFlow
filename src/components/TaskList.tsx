import React from 'react';
import { useTask } from '../contexts/TaskContext';
import TaskCard from './TaskCard';
import Pagination from './Pagination';
import { Task } from '../types/task';
import { Package } from 'lucide-react';

interface TaskListProps {
  onEditTask: (task: Task) => void;
  onShareTask: (taskId: string) => void;
}

const TaskList: React.FC<TaskListProps> = ({ onEditTask, onShareTask }) => {
  const { 
    filteredTasks, 
    isLoading, 
    currentPage, 
    tasksPerPage, 
    totalPages,
    setCurrentPage,
    setTasksPerPage 
  } = useTask();

  // Calculate pagination
  const startIndex = (currentPage - 1) * tasksPerPage;
  const endIndex = startIndex + tasksPerPage;
  const currentTasks = filteredTasks.slice(startIndex, endIndex);

  if (isLoading) {
    return (
      <div className="bg-white/90 backdrop-blur-md rounded-2xl shadow-lg p-8 border border-[#FF6363]/20">
        <div className="animate-pulse space-y-4">
          {[...Array(3)].map((_, i) => (
            <div key={i} className="h-24 bg-[#FF6363]/10 rounded-xl"></div>
          ))}
        </div>
      </div>
    );
  }

  if (filteredTasks.length === 0) {
    return (
      <div className="bg-white/90 backdrop-blur-md rounded-2xl shadow-lg p-12 text-center border border-[#FF6363]/20">
        <div className="bg-[#FF6363]/10 p-4 rounded-full w-16 h-16 mx-auto mb-4 flex items-center justify-center">
          <Package className="w-8 h-8 text-[#FF6363]" />
        </div>
        <h3 className="text-lg font-medium text-[#2D3748] mb-2">No tasks found</h3>
        <p className="text-[#4A5568] mb-6">
          Create your first task or adjust your filters to see results.
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-white/90 backdrop-blur-md rounded-2xl shadow-lg p-6 border border-[#FF6363]/20">
        <div className="flex items-center justify-between mb-6">
          <div>
            <h2 className="text-xl font-semibold text-[#2D3748]">
              Tasks ({filteredTasks.length})
            </h2>
            <p className="text-sm text-[#4A5568] mt-1">
              Showing {startIndex + 1}-{Math.min(endIndex, filteredTasks.length)} of {filteredTasks.length} tasks
            </p>
          </div>
          
          <div className="flex items-center space-x-2">
            <label htmlFor="tasksPerPage" className="text-sm text-[#4A5568]">
              Show:
            </label>
            <select
              id="tasksPerPage"
              value={tasksPerPage}
              onChange={(e) => {
                setTasksPerPage(Number(e.target.value));
                setCurrentPage(1);
              }}
              className="px-3 py-1 border border-[#FF6363]/20 rounded-lg bg-white/70 text-sm focus:outline-none focus:ring-2 focus:ring-[#FF6363] text-[#2D3748]"
            >
              <option value={5}>5</option>
              <option value={10}>10</option>
              <option value={20}>20</option>
              <option value={50}>50</option>
            </select>
          </div>
        </div>

        <div className="space-y-4">
          {currentTasks.map((task) => (
            <TaskCard
              key={task.id}
              task={task}
              onEdit={() => onEditTask(task)}
              onShare={() => onShareTask(task.id)}
            />
          ))}
        </div>
      </div>

      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      )}
    </div>
  );
};

export default TaskList;