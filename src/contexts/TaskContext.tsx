import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { Task, TaskStatus, TaskPriority, TaskFilters } from '../types/task';
import { useAuth } from './AuthContext';
import toast from 'react-hot-toast';

interface TaskContextType {
  tasks: Task[];
  filteredTasks: Task[];
  filters: TaskFilters;
  isLoading: boolean;
  currentPage: number;
  tasksPerPage: number;
  totalPages: number;
  setFilters: (filters: TaskFilters) => void;
  setCurrentPage: (page: number) => void;
  setTasksPerPage: (count: number) => void;
  addTask: (task: Omit<Task, 'id' | 'createdAt' | 'updatedAt'>) => void;
  updateTask: (id: string, updates: Partial<Task>) => void;
  deleteTask: (id: string) => void;
  shareTask: (taskId: string, email: string) => void;
  refreshTasks: () => void;
}

const TaskContext = createContext<TaskContextType | undefined>(undefined);

export const useTask = () => {
  const context = useContext(TaskContext);
  if (context === undefined) {
    throw new Error('useTask must be used within a TaskProvider');
  }
  return context;
};

interface TaskProviderProps {
  children: ReactNode;
}

export const TaskProvider: React.FC<TaskProviderProps> = ({ children }) => {
  const { user } = useAuth();
  const [tasks, setTasks] = useState<Task[]>([]);
  const [filters, setFilters] = useState<TaskFilters>({
    search: '',
    status: 'all',
    priority: 'all',
    dueDate: 'all',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [tasksPerPage, setTasksPerPage] = useState(10);

  // Load tasks on mount and set up polling
  useEffect(() => {
    if (user) {
      loadTasks();
      const interval = setInterval(loadTasks, 30000); // Poll every 30 seconds
      return () => clearInterval(interval);
    }
  }, [user]);

  // Filter tasks based on current filters
  const filteredTasks = tasks.filter(task => {
    const matchesSearch = filters.search === '' || 
      task.title.toLowerCase().includes(filters.search.toLowerCase()) ||
      task.description.toLowerCase().includes(filters.search.toLowerCase());

    const matchesStatus = filters.status === 'all' || task.status === filters.status;
    const matchesPriority = filters.priority === 'all' || task.priority === filters.priority;

    let matchesDueDate = true;
    if (filters.dueDate !== 'all' && task.dueDate) {
      const today = new Date();
      const dueDate = new Date(task.dueDate);
      const diffDays = Math.ceil((dueDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));

      switch (filters.dueDate) {
        case 'overdue':
          matchesDueDate = diffDays < 0;
          break;
        case 'today':
          matchesDueDate = diffDays === 0;
          break;
        case 'tomorrow':
          matchesDueDate = diffDays === 1;
          break;
        case 'this-week':
          matchesDueDate = diffDays >= 0 && diffDays <= 7;
          break;
        case 'this-month':
          matchesDueDate = diffDays >= 0 && diffDays <= 30;
          break;
      }
    }

    return matchesSearch && matchesStatus && matchesPriority && matchesDueDate;
  });

  const totalPages = Math.ceil(filteredTasks.length / tasksPerPage);

  const loadTasks = async () => {
    if (!user) return;
    
    try {
      setIsLoading(true);
      // Simulate API call - in real app, this would be an API request
      const storedTasks = localStorage.getItem('focusflow_tasks');
      if (storedTasks) {
        setTasks(JSON.parse(storedTasks));
      } else {
        // Initialize with sample tasks
        const sampleTasks: Task[] = [
          {
            id: '1',
            title: 'Complete project proposal',
            description: 'Finalize and submit the Q4 project proposal for review',
            status: 'pending',
            priority: 'high',
            dueDate: new Date(Date.now() + 86400000).toISOString(), // Tomorrow
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
            assignedTo: user.id,
            sharedWith: [],
          },
          {
            id: '2',
            title: 'Team meeting preparation',
            description: 'Prepare agenda and materials for the weekly team sync',
            status: 'in-progress',
            priority: 'medium',
            dueDate: new Date().toISOString(), // Today
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
            assignedTo: user.id,
            sharedWith: [],
          },
          {
            id: '3',
            title: 'Code review',
            description: 'Review pull requests from team members',
            status: 'completed',
            priority: 'low',
            dueDate: new Date(Date.now() - 86400000).toISOString(), // Yesterday
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
            assignedTo: user.id,
            sharedWith: [],
          },
        ];
        setTasks(sampleTasks);
        localStorage.setItem('focusflow_tasks', JSON.stringify(sampleTasks));
      }
    } catch (error) {
      toast.error('Failed to load tasks');
    } finally {
      setIsLoading(false);
    }
  };

  const addTask = (newTask: Omit<Task, 'id' | 'createdAt' | 'updatedAt'>) => {
    const task: Task = {
      ...newTask,
      id: Date.now().toString(),
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };

    const updatedTasks = [...tasks, task];
    setTasks(updatedTasks);
    localStorage.setItem('focusflow_tasks', JSON.stringify(updatedTasks));
    toast.success('Task created successfully!');
  };

  const updateTask = (id: string, updates: Partial<Task>) => {
    const updatedTasks = tasks.map(task =>
      task.id === id
        ? { ...task, ...updates, updatedAt: new Date().toISOString() }
        : task
    );
    setTasks(updatedTasks);
    localStorage.setItem('focusflow_tasks', JSON.stringify(updatedTasks));
    toast.success('Task updated successfully!');
  };

  const deleteTask = (id: string) => {
    const updatedTasks = tasks.filter(task => task.id !== id);
    setTasks(updatedTasks);
    localStorage.setItem('focusflow_tasks', JSON.stringify(updatedTasks));
    toast.success('Task deleted successfully!');
  };

  const shareTask = (taskId: string, email: string) => {
    const updatedTasks = tasks.map(task =>
      task.id === taskId
        ? {
            ...task,
            sharedWith: [...(task.sharedWith || []), email],
            updatedAt: new Date().toISOString(),
          }
        : task
    );
    setTasks(updatedTasks);
    localStorage.setItem('focusflow_tasks', JSON.stringify(updatedTasks));
    toast.success(`Task shared with ${email}!`);
  };

  const refreshTasks = () => {
    loadTasks();
  };

  const value = {
    tasks,
    filteredTasks,
    filters,
    isLoading,
    currentPage,
    tasksPerPage,
    totalPages,
    setFilters,
    setCurrentPage,
    setTasksPerPage,
    addTask,
    updateTask,
    deleteTask,
    shareTask,
    refreshTasks,
  };

  return <TaskContext.Provider value={value}>{children}</TaskContext.Provider>;
};