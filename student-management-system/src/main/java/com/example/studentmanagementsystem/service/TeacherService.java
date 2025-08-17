package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.entity.TeacherStatus;
import com.example.studentmanagementsystem.repository.StudentRepository;
import com.example.studentmanagementsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * TeacherService Class
 * 
 * This service class contains business logic for teacher operations
 * It handles teacher registration, management, and data processing
 * 
 * @Service: Marks this class as a Spring service component
 * @Transactional: Ensures database operations are wrapped in transactions
 */
@Service
@Transactional
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    
    @Autowired
    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }
    
    /**
     * Register a new teacher
     * 
     * @param teacher the teacher to register
     * @return the saved teacher
     * @throws RuntimeException if teacher ID already exists
     */
    public Teacher registerTeacher(Teacher teacher) {
        // Check if teacher ID already exists
        if (teacherRepository.existsByTeacherId(teacher.getTeacherId())) {
            throw new RuntimeException("Teacher ID already exists: " + teacher.getTeacherId());
        }
        
        // Set default values
        if (teacher.getHireDate() == null) {
            teacher.setHireDate(LocalDate.now());
        }
        
        if (teacher.getStatus() == null) {
            teacher.setStatus(TeacherStatus.ACTIVE);
        }
        
        return teacherRepository.save(teacher);
    }
    
    /**
     * Find teacher by ID
     * 
     * @param id the teacher ID
     * @return Optional containing the teacher if found
     */
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }
    
    /**
     * Find teacher by teacher ID
     * 
     * @param teacherId the teacher ID
     * @return Optional containing the teacher if found
     */
    public Optional<Teacher> findByTeacherId(String teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }
    
    /**
     * Get all teachers
     * 
     * @return List of all teachers
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    
    /**
     * Get teachers by status
     * 
     * @param status the status to filter by
     * @return List of teachers with the specified status
     */
    public List<Teacher> getTeachersByStatus(TeacherStatus status) {
        return teacherRepository.findByStatus(status);
    }
    
    /**
     * Get active teachers
     * 
     * @return List of active teachers
     */
    public List<Teacher> getActiveTeachers() {
        return teacherRepository.findByStatus(TeacherStatus.ACTIVE);
    }
    
    /**
     * Get teachers by department
     * 
     * @param department the department to filter by
     * @return List of teachers in the specified department
     */
    public List<Teacher> getTeachersByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }
    
    /**
     * Get teachers by specialization
     * 
     * @param specialization the specialization to filter by
     * @return List of teachers with the specified specialization
     */
    public List<Teacher> getTeachersBySpecialization(String specialization) {
        return teacherRepository.findBySpecialization(specialization);
    }
    
    /**
     * Get teachers by hire date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of teachers hired in the date range
     */
    public List<Teacher> getTeachersByHireDateRange(LocalDate startDate, LocalDate endDate) {
        return teacherRepository.findByHireDateBetween(startDate, endDate);
    }
    
    /**
     * Get teachers by qualification
     * 
     * @param qualification the qualification to filter by
     * @return List of teachers with the specified qualification
     */
    public List<Teacher> getTeachersByQualification(String qualification) {
        return teacherRepository.findByQualification(qualification);
    }
    
    /**
     * Get teachers with salary above a certain value
     * 
     * @param salary the minimum salary
     * @return List of teachers with salary above the specified value
     */
    public List<Teacher> getTeachersWithSalaryAbove(Double salary) {
        return teacherRepository.findBySalaryGreaterThan(salary);
    }
    
    /**
     * Get teachers with salary below a certain value
     * 
     * @param salary the maximum salary
     * @return List of teachers with salary below the specified value
     */
    public List<Teacher> getTeachersWithSalaryBelow(Double salary) {
        return teacherRepository.findBySalaryLessThan(salary);
    }
    
    /**
     * Update teacher information
     * 
     * @param id the teacher ID
     * @param updatedTeacher the updated teacher information
     * @return the updated teacher
     * @throws RuntimeException if teacher not found
     */
    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        
        // Update fields
        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setDateOfBirth(updatedTeacher.getDateOfBirth());
        existingTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());
        existingTeacher.setEmail(updatedTeacher.getEmail());
        existingTeacher.setAddress(updatedTeacher.getAddress());
        existingTeacher.setDepartment(updatedTeacher.getDepartment());
        existingTeacher.setSpecialization(updatedTeacher.getSpecialization());
        existingTeacher.setQualification(updatedTeacher.getQualification());
        existingTeacher.setStatus(updatedTeacher.getStatus());
        existingTeacher.setSalary(updatedTeacher.getSalary());
        
        return teacherRepository.save(existingTeacher);
    }
    
    /**
     * Update teacher salary
     * 
     * @param id the teacher ID
     * @param salary the new salary
     * @return the updated teacher
     * @throws RuntimeException if teacher not found
     */
    public Teacher updateSalary(Long id, Double salary) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        
        teacher.setSalary(salary);
        
        return teacherRepository.save(teacher);
    }
    
    /**
     * Change teacher status
     * 
     * @param id the teacher ID
     * @param status the new status
     * @return the updated teacher
     * @throws RuntimeException if teacher not found
     */
    public Teacher changeStatus(Long id, TeacherStatus status) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        
        teacher.setStatus(status);
        
        return teacherRepository.save(teacher);
    }
    
    /**
     * Get advisees (students) for a teacher
     * 
     * @param teacherId the teacher ID
     * @return List of students assigned to the teacher as advisor
     */
    public List<Student> getAdvisees(Long teacherId) {
        return studentRepository.findByAdvisorId(teacherId);
    }
    
    /**
     * Get advisees by status for a teacher
     * 
     * @param teacherId the teacher ID
     * @param status the student status to filter by
     * @return List of students assigned to the teacher with the specified status
     */
    public List<Student> getAdviseesByStatus(Long teacherId, String status) {
        return studentRepository.findByAdvisorIdAndStatus(teacherId, status);
    }
    
    /**
     * Delete teacher
     * 
     * @param id the teacher ID
     * @throws RuntimeException if teacher not found
     */
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
    
    /**
     * Search teachers by name
     * 
     * @param name the name to search for
     * @return List of teachers whose name contains the search term
     */
    public List<Teacher> searchTeachersByName(String name) {
        return teacherRepository.findByNameContaining(name);
    }
    
    /**
     * Search teachers by department and name
     * 
     * @param department the department to filter by
     * @param name the name to search for
     * @return List of teachers matching both criteria
     */
    public List<Teacher> searchTeachersByDepartmentAndName(String department, String name) {
        return teacherRepository.findByDepartmentAndNameContaining(department, name);
    }
    
    /**
     * Search teachers by status and department
     * 
     * @param status the status to filter by
     * @param department the department to filter by
     * @return List of teachers matching both criteria
     */
    public List<Teacher> searchTeachersByStatusAndDepartment(TeacherStatus status, String department) {
        return teacherRepository.findByStatusAndDepartment(status, department);
    }
    
    /**
     * Search teachers by specialization and status
     * 
     * @param specialization the specialization to filter by
     * @param status the status to filter by
     * @return List of teachers matching both criteria
     */
    public List<Teacher> searchTeachersBySpecializationAndStatus(String specialization, TeacherStatus status) {
        return teacherRepository.findBySpecializationAndStatus(specialization, status);
    }
    
    /**
     * Get top teachers by salary
     * 
     * @param limit the number of teachers to return
     * @return List of top teachers by salary
     */
    public List<Teacher> getTopTeachersBySalary(int limit) {
        return teacherRepository.findTopTeachersBySalary(limit);
    }
    
    /**
     * Get teachers by minimum years of experience
     * 
     * @param minYears minimum years of experience
     * @return List of teachers with at least the specified years of experience
     */
    public List<Teacher> getTeachersByMinYearsOfExperience(int minYears) {
        return teacherRepository.findByMinYearsOfExperience(minYears);
    }
    
    /**
     * Count teachers by status
     * 
     * @param status the status to count
     * @return number of teachers with the specified status
     */
    public long countTeachersByStatus(TeacherStatus status) {
        return teacherRepository.countByStatus(status);
    }
    
    /**
     * Count teachers by department
     * 
     * @param department the department to count
     * @return number of teachers in the specified department
     */
    public long countTeachersByDepartment(String department) {
        return teacherRepository.countByDepartment(department);
    }
    
    /**
     * Count teachers by specialization
     * 
     * @param specialization the specialization to count
     * @return number of teachers with the specified specialization
     */
    public long countTeachersBySpecialization(String specialization) {
        return teacherRepository.countBySpecialization(specialization);
    }
    
    /**
     * Count teachers by qualification
     * 
     * @param qualification the qualification to count
     * @return number of teachers with the specified qualification
     */
    public long countTeachersByQualification(String qualification) {
        return teacherRepository.countByQualification(qualification);
    }
    
    /**
     * Check if teacher exists by teacher ID
     * 
     * @param teacherId the teacher ID to check
     * @return true if teacher exists, false otherwise
     */
    public boolean teacherExists(String teacherId) {
        return teacherRepository.existsByTeacherId(teacherId);
    }
    
    /**
     * Get all departments
     * 
     * @return List of unique departments
     */
    public List<String> getAllDepartments() {
        return teacherRepository.findAllDepartments();
    }
    
    /**
     * Get all specializations
     * 
     * @return List of unique specializations
     */
    public List<String> getAllSpecializations() {
        return teacherRepository.findAllSpecializations();
    }
    
    /**
     * Get average salary of all teachers
     * 
     * @return average salary or 0.0 if no teachers have salary
     */
    public Double getAverageSalary() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .filter(t -> t.getSalary() != null)
                .mapToDouble(Teacher::getSalary)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Get total number of teachers
     * 
     * @return total number of teachers
     */
    public long getTotalTeachers() {
        return teacherRepository.count();
    }
}