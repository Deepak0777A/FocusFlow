package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * StudentRepository Interface
 * 
 * This interface extends JpaRepository to provide CRUD operations for Student entity
 * Spring Data JPA automatically implements this interface at runtime
 * 
 * @Repository: Marks this interface as a Spring Data repository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Find student by student ID
     * 
     * @param studentId the student ID to search for
     * @return Optional containing the student if found
     */
    Optional<Student> findByStudentId(String studentId);
    
    /**
     * Find students by status
     * 
     * @param status the status to search for
     * @return List of students with the specified status
     */
    List<Student> findByStatus(StudentStatus status);
    
    /**
     * Find students by advisor
     * 
     * @param advisorId the advisor's ID
     * @return List of students assigned to the advisor
     */
    List<Student> findByAdvisorId(Long advisorId);
    
    /**
     * Find students by enrollment date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of students enrolled in the date range
     */
    List<Student> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find students by graduation date
     * 
     * @param graduationDate the graduation date
     * @return List of students graduating on the specified date
     */
    List<Student> findByGraduationDate(LocalDate graduationDate);
    
    /**
     * Find students by gender
     * 
     * @param gender the gender (M or F)
     * @return List of students with the specified gender
     */
    List<Student> findByGender(String gender);
    
    /**
     * Find students with GPA above a certain value
     * 
     * @param gpa the minimum GPA
     * @return List of students with GPA above the specified value
     */
    List<Student> findByGpaGreaterThan(Double gpa);
    
    /**
     * Find students with GPA below a certain value
     * 
     * @param gpa the maximum GPA
     * @return List of students with GPA below the specified value
     */
    List<Student> findByGpaLessThan(Double gpa);
    
    /**
     * Find students by total credits
     * 
     * @param totalCredits the total credits
     * @return List of students with the specified total credits
     */
    List<Student> findByTotalCredits(Integer totalCredits);
    
    /**
     * Find students with credits above a certain value
     * 
     * @param totalCredits the minimum credits
     * @return List of students with credits above the specified value
     */
    List<Student> findByTotalCreditsGreaterThan(Integer totalCredits);
    
    /**
     * Custom query to find students by partial name match
     * 
     * @param name partial name to search for (first name or last name)
     * @return List of students whose name contains the search term
     */
    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Student> findByNameContaining(@Param("name") String name);
    
    /**
     * Custom query to find students by status and partial name match
     * 
     * @param status the status to search for
     * @param name partial name to search for
     * @return List of students matching both criteria
     */
    @Query("SELECT s FROM Student s WHERE s.status = :status AND (s.firstName LIKE %:name% OR s.lastName LIKE %:name%)")
    List<Student> findByStatusAndNameContaining(@Param("status") StudentStatus status, @Param("name") String name);
    
    /**
     * Custom query to find students by advisor and status
     * 
     * @param advisorId the advisor's ID
     * @param status the status to search for
     * @return List of students assigned to the advisor with the specified status
     */
    @Query("SELECT s FROM Student s WHERE s.advisor.id = :advisorId AND s.status = :status")
    List<Student> findByAdvisorIdAndStatus(@Param("advisorId") Long advisorId, @Param("status") StudentStatus status);
    
    /**
     * Custom query to find top students by GPA
     * 
     * @param limit the number of students to return
     * @return List of top students by GPA
     */
    @Query("SELECT s FROM Student s WHERE s.gpa IS NOT NULL ORDER BY s.gpa DESC")
    List<Student> findTopStudentsByGpa(@Param("limit") int limit);
    
    /**
     * Count students by status
     * 
     * @param status the status to count
     * @return number of students with the specified status
     */
    long countByStatus(StudentStatus status);
    
    /**
     * Count students by advisor
     * 
     * @param advisorId the advisor's ID
     * @return number of students assigned to the advisor
     */
    long countByAdvisorId(Long advisorId);
    
    /**
     * Count students by gender
     * 
     * @param gender the gender to count
     * @return number of students with the specified gender
     */
    long countByGender(String gender);
    
    /**
     * Check if student exists by student ID
     * 
     * @param studentId the student ID to check
     * @return true if student exists, false otherwise
     */
    boolean existsByStudentId(String studentId);
}