package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.StudentStatus;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.repository.StudentRepository;
import com.example.studentmanagementsystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * StudentService Class
 * 
 * This service class contains business logic for student operations
 * It handles student registration, management, and data processing
 * 
 * @Service: Marks this class as a Spring service component
 * @Transactional: Ensures database operations are wrapped in transactions
 */
@Service
@Transactional
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }
    
    /**
     * Register a new student
     * 
     * @param student the student to register
     * @return the saved student
     * @throws RuntimeException if student ID already exists
     */
    public Student registerStudent(Student student) {
        // Check if student ID already exists
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new RuntimeException("Student ID already exists: " + student.getStudentId());
        }
        
        // Set default values
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());
        }
        
        if (student.getStatus() == null) {
            student.setStatus(StudentStatus.ACTIVE);
        }
        
        if (student.getTotalCredits() == null) {
            student.setTotalCredits(0);
        }
        
        return studentRepository.save(student);
    }
    
    /**
     * Find student by ID
     * 
     * @param id the student ID
     * @return Optional containing the student if found
     */
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }
    
    /**
     * Find student by student ID
     * 
     * @param studentId the student ID
     * @return Optional containing the student if found
     */
    public Optional<Student> findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }
    
    /**
     * Get all students
     * 
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    /**
     * Get students by status
     * 
     * @param status the status to filter by
     * @return List of students with the specified status
     */
    public List<Student> getStudentsByStatus(StudentStatus status) {
        return studentRepository.findByStatus(status);
    }
    
    /**
     * Get active students
     * 
     * @return List of active students
     */
    public List<Student> getActiveStudents() {
        return studentRepository.findByStatus(StudentStatus.ACTIVE);
    }
    
    /**
     * Get students by advisor
     * 
     * @param advisorId the advisor's ID
     * @return List of students assigned to the advisor
     */
    public List<Student> getStudentsByAdvisor(Long advisorId) {
        return studentRepository.findByAdvisorId(advisorId);
    }
    
    /**
     * Get students by enrollment date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of students enrolled in the date range
     */
    public List<Student> getStudentsByEnrollmentDateRange(LocalDate startDate, LocalDate endDate) {
        return studentRepository.findByEnrollmentDateBetween(startDate, endDate);
    }
    
    /**
     * Get students by gender
     * 
     * @param gender the gender (M or F)
     * @return List of students with the specified gender
     */
    public List<Student> getStudentsByGender(String gender) {
        return studentRepository.findByGender(gender);
    }
    
    /**
     * Get students with GPA above a certain value
     * 
     * @param gpa the minimum GPA
     * @return List of students with GPA above the specified value
     */
    public List<Student> getStudentsWithGpaAbove(Double gpa) {
        return studentRepository.findByGpaGreaterThan(gpa);
    }
    
    /**
     * Get students with GPA below a certain value
     * 
     * @param gpa the maximum GPA
     * @return List of students with GPA below the specified value
     */
    public List<Student> getStudentsWithGpaBelow(Double gpa) {
        return studentRepository.findByGpaLessThan(gpa);
    }
    
    /**
     * Get students by total credits
     * 
     * @param totalCredits the total credits
     * @return List of students with the specified total credits
     */
    public List<Student> getStudentsByTotalCredits(Integer totalCredits) {
        return studentRepository.findByTotalCredits(totalCredits);
    }
    
    /**
     * Get students with credits above a certain value
     * 
     * @param totalCredits the minimum credits
     * @return List of students with credits above the specified value
     */
    public List<Student> getStudentsWithCreditsAbove(Integer totalCredits) {
        return studentRepository.findByTotalCreditsGreaterThan(totalCredits);
    }
    
    /**
     * Update student information
     * 
     * @param id the student ID
     * @param updatedStudent the updated student information
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Update fields
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
        existingStudent.setGender(updatedStudent.getGender());
        existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setGraduationDate(updatedStudent.getGraduationDate());
        existingStudent.setStatus(updatedStudent.getStatus());
        existingStudent.setGpa(updatedStudent.getGpa());
        existingStudent.setTotalCredits(updatedStudent.getTotalCredits());
        
        return studentRepository.save(existingStudent);
    }
    
    /**
     * Update student GPA
     * 
     * @param id the student ID
     * @param gpa the new GPA
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student updateGpa(Long id, Double gpa) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setGpa(gpa);
        
        return studentRepository.save(student);
    }
    
    /**
     * Update student credits
     * 
     * @param id the student ID
     * @param credits the new total credits
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student updateCredits(Long id, Integer credits) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setTotalCredits(credits);
        
        return studentRepository.save(student);
    }
    
    /**
     * Assign advisor to student
     * 
     * @param studentId the student ID
     * @param advisorId the advisor's ID
     * @return the updated student
     * @throws RuntimeException if student or advisor not found
     */
    public Student assignAdvisor(Long studentId, Long advisorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        Teacher advisor = teacherRepository.findById(advisorId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + advisorId));
        
        student.setAdvisor(advisor);
        
        return studentRepository.save(student);
    }
    
    /**
     * Change student status
     * 
     * @param id the student ID
     * @param status the new status
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student changeStatus(Long id, StudentStatus status) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setStatus(status);
        
        return studentRepository.save(student);
    }
    
    /**
     * Graduate student
     * 
     * @param id the student ID
     * @return the updated student
     * @throws RuntimeException if student not found
     */
    public Student graduateStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setStatus(StudentStatus.GRADUATED);
        student.setGraduationDate(LocalDate.now());
        
        return studentRepository.save(student);
    }
    
    /**
     * Delete student
     * 
     * @param id the student ID
     * @throws RuntimeException if student not found
     */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    /**
     * Search students by name
     * 
     * @param name the name to search for
     * @return List of students whose name contains the search term
     */
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }
    
    /**
     * Search students by status and name
     * 
     * @param status the status to filter by
     * @param name the name to search for
     * @return List of students matching both criteria
     */
    public List<Student> searchStudentsByStatusAndName(StudentStatus status, String name) {
        return studentRepository.findByStatusAndNameContaining(status, name);
    }
    
    /**
     * Get top students by GPA
     * 
     * @param limit the number of students to return
     * @return List of top students by GPA
     */
    public List<Student> getTopStudentsByGpa(int limit) {
        return studentRepository.findTopStudentsByGpa(limit);
    }
    
    /**
     * Count students by status
     * 
     * @param status the status to count
     * @return number of students with the specified status
     */
    public long countStudentsByStatus(StudentStatus status) {
        return studentRepository.countByStatus(status);
    }
    
    /**
     * Count students by advisor
     * 
     * @param advisorId the advisor's ID
     * @return number of students assigned to the advisor
     */
    public long countStudentsByAdvisor(Long advisorId) {
        return studentRepository.countByAdvisorId(advisorId);
    }
    
    /**
     * Count students by gender
     * 
     * @param gender the gender to count
     * @return number of students with the specified gender
     */
    public long countStudentsByGender(String gender) {
        return studentRepository.countByGender(gender);
    }
    
    /**
     * Check if student exists by student ID
     * 
     * @param studentId the student ID to check
     * @return true if student exists, false otherwise
     */
    public boolean studentExists(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }
    
    /**
     * Get average GPA of all students
     * 
     * @return average GPA or null if no students have GPA
     */
    public Double getAverageGpa() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .filter(s -> s.getGpa() != null)
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Get total number of students
     * 
     * @return total number of students
     */
    public long getTotalStudents() {
        return studentRepository.count();
    }
}