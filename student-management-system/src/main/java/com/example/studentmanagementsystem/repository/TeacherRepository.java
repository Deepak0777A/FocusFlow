package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.entity.TeacherStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * TeacherRepository Interface
 * 
 * This interface extends JpaRepository to provide CRUD operations for Teacher entity
 * Spring Data JPA automatically implements this interface at runtime
 * 
 * @Repository: Marks this interface as a Spring Data repository
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    /**
     * Find teacher by teacher ID
     * 
     * @param teacherId the teacher ID to search for
     * @return Optional containing the teacher if found
     */
    Optional<Teacher> findByTeacherId(String teacherId);
    
    /**
     * Find teachers by status
     * 
     * @param status the status to search for
     * @return List of teachers with the specified status
     */
    List<Teacher> findByStatus(TeacherStatus status);
    
    /**
     * Find teachers by department
     * 
     * @param department the department to search for
     * @return List of teachers in the specified department
     */
    List<Teacher> findByDepartment(String department);
    
    /**
     * Find teachers by specialization
     * 
     * @param specialization the specialization to search for
     * @return List of teachers with the specified specialization
     */
    List<Teacher> findBySpecialization(String specialization);
    
    /**
     * Find teachers by hire date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of teachers hired in the date range
     */
    List<Teacher> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find teachers by qualification
     * 
     * @param qualification the qualification to search for
     * @return List of teachers with the specified qualification
     */
    List<Teacher> findByQualification(String qualification);
    
    /**
     * Find teachers with salary above a certain value
     * 
     * @param salary the minimum salary
     * @return List of teachers with salary above the specified value
     */
    List<Teacher> findBySalaryGreaterThan(Double salary);
    
    /**
     * Find teachers with salary below a certain value
     * 
     * @param salary the maximum salary
     * @return List of teachers with salary below the specified value
     */
    List<Teacher> findBySalaryLessThan(Double salary);
    
    /**
     * Custom query to find teachers by partial name match
     * 
     * @param name partial name to search for (first name or last name)
     * @return List of teachers whose name contains the search term
     */
    @Query("SELECT t FROM Teacher t WHERE t.firstName LIKE %:name% OR t.lastName LIKE %:name%")
    List<Teacher> findByNameContaining(@Param("name") String name);
    
    /**
     * Custom query to find teachers by department and partial name match
     * 
     * @param department the department to search for
     * @param name partial name to search for
     * @return List of teachers matching both criteria
     */
    @Query("SELECT t FROM Teacher t WHERE t.department = :department AND (t.firstName LIKE %:name% OR t.lastName LIKE %:name%)")
    List<Teacher> findByDepartmentAndNameContaining(@Param("department") String department, @Param("name") String name);
    
    /**
     * Custom query to find teachers by status and department
     * 
     * @param status the status to search for
     * @param department the department to search for
     * @return List of teachers matching both criteria
     */
    @Query("SELECT t FROM Teacher t WHERE t.status = :status AND t.department = :department")
    List<Teacher> findByStatusAndDepartment(@Param("status") TeacherStatus status, @Param("department") String department);
    
    /**
     * Custom query to find teachers by specialization and status
     * 
     * @param specialization the specialization to search for
     * @param status the status to search for
     * @return List of teachers matching both criteria
     */
    @Query("SELECT t FROM Teacher t WHERE t.specialization = :specialization AND t.status = :status")
    List<Teacher> findBySpecializationAndStatus(@Param("specialization") String specialization, @Param("status") TeacherStatus status);
    
    /**
     * Custom query to find top teachers by salary
     * 
     * @param limit the number of teachers to return
     * @return List of top teachers by salary
     */
    @Query("SELECT t FROM Teacher t WHERE t.salary IS NOT NULL ORDER BY t.salary DESC")
    List<Teacher> findTopTeachersBySalary(@Param("limit") int limit);
    
    /**
     * Custom query to find teachers by years of experience
     * 
     * @param minYears minimum years of experience
     * @return List of teachers with at least the specified years of experience
     */
    @Query("SELECT t FROM Teacher t WHERE YEAR(CURRENT_DATE) - YEAR(t.hireDate) >= :minYears")
    List<Teacher> findByMinYearsOfExperience(@Param("minYears") int minYears);
    
    /**
     * Count teachers by status
     * 
     * @param status the status to count
     * @return number of teachers with the specified status
     */
    long countByStatus(TeacherStatus status);
    
    /**
     * Count teachers by department
     * 
     * @param department the department to count
     * @return number of teachers in the specified department
     */
    long countByDepartment(String department);
    
    /**
     * Count teachers by specialization
     * 
     * @param specialization the specialization to count
     * @return number of teachers with the specified specialization
     */
    long countBySpecialization(String specialization);
    
    /**
     * Count teachers by qualification
     * 
     * @param qualification the qualification to count
     * @return number of teachers with the specified qualification
     */
    long countByQualification(String qualification);
    
    /**
     * Check if teacher exists by teacher ID
     * 
     * @param teacherId the teacher ID to check
     * @return true if teacher exists, false otherwise
     */
    boolean existsByTeacherId(String teacherId);
    
    /**
     * Find all departments
     * 
     * @return List of unique departments
     */
    @Query("SELECT DISTINCT t.department FROM Teacher t WHERE t.department IS NOT NULL")
    List<String> findAllDepartments();
    
    /**
     * Find all specializations
     * 
     * @return List of unique specializations
     */
    @Query("SELECT DISTINCT t.specialization FROM Teacher t WHERE t.specialization IS NOT NULL")
    List<String> findAllSpecializations();
}