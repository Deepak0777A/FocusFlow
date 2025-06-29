import React from 'react';
import { useTask } from '../contexts/TaskContext';
import { Search, Filter, RotateCcw } from 'lucide-react';

const FilterPanel: React.FC = () => {
  const { filters, setFilters } = useTask();

  const handleFilterChange = (key: string, value: string) => {
    setFilters({ ...filters, [key]: value });
  };

  const handleClearFilters = () => {
    setFilters({
      search: '',
      status: 'all',
      priority: 'all',
      dueDate: 'all',
    });
  };

  const hasActiveFilters = filters.search || 
    filters.status !== 'all' || 
    filters.priority !== 'all' || 
    filters.dueDate !== 'all';

  return (
    <div className="bg-white/90 backdrop-blur-md rounded-2xl shadow-lg p-6 h-fit sticky top-24 border border-[#FF6363]/20">
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center">
          <Filter className="w-5 h-5 text-[#FF6363] mr-2" />
          <h3 className="font-semibold text-[#2D3748]">Filters</h3>
        </div>
        {hasActiveFilters && (
          <button
            onClick={handleClearFilters}
            className="text-sm text-[#FF6363] hover:text-[#FF4444] flex items-center"
          >
            <RotateCcw className="w-4 h-4 mr-1" />
            Clear
          </button>
        )}
      </div>

      <div className="space-y-6">
        {/* Search */}
        <div>
          <label className="block text-sm font-medium text-[#2D3748] mb-2">
            Search
          </label>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#4A5568]" />
            <input
              type="text"
              value={filters.search}
              onChange={(e) => handleFilterChange('search', e.target.value)}
              placeholder="Search tasks..."
              className="w-full pl-10 pr-3 py-2 border border-[#FF6363]/20 rounded-lg bg-white/70 focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
            />
          </div>
        </div>

        {/* Status */}
        <div>
          <label className="block text-sm font-medium text-[#2D3748] mb-2">
            Status
          </label>
          <select
            value={filters.status}
            onChange={(e) => handleFilterChange('status', e.target.value)}
            className="w-full px-3 py-2 border border-[#FF6363]/20 rounded-lg bg-white/70 focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
          >
            <option value="all">All Status</option>
            <option value="pending">Pending</option>
            <option value="in-progress">In Progress</option>
            <option value="completed">Completed</option>
          </select>
        </div>

        {/* Priority */}
        <div>
          <label className="block text-sm font-medium text-[#2D3748] mb-2">
            Priority
          </label>
          <select
            value={filters.priority}
            onChange={(e) => handleFilterChange('priority', e.target.value)}
            className="w-full px-3 py-2 border border-[#FF6363]/20 rounded-lg bg-white/70 focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
          >
            <option value="all">All Priorities</option>
            <option value="low">Low</option>
            <option value="medium">Medium</option>
            <option value="high">High</option>
          </select>
        </div>

        {/* Due Date */}
        <div>
          <label className="block text-sm font-medium text-[#2D3748] mb-2">
            Due Date
          </label>
          <select
            value={filters.dueDate}
            onChange={(e) => handleFilterChange('dueDate', e.target.value)}
            className="w-full px-3 py-2 border border-[#FF6363]/20 rounded-lg bg-white/70 focus:outline-none focus:ring-2 focus:ring-[#FF6363] focus:border-transparent transition-all duration-200 text-[#2D3748]"
          >
            <option value="all">All Dates</option>
            <option value="overdue">Overdue</option>
            <option value="today">Due Today</option>
            <option value="tomorrow">Due Tomorrow</option>
            <option value="this-week">This Week</option>
            <option value="this-month">This Month</option>
          </select>
        </div>
      </div>
    </div>
  );
};

export default FilterPanel;