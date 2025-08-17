package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository Interface
 * 
 * This interface extends JpaRepository to provide CRUD operations for User entity
 * Spring Data JPA automatically implements this interface at runtime
 * 
 * @Repository: Marks this interface as a Spring Data repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by username
     * Spring Data JPA automatically creates this method based on the method name
     * 
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email
     * 
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by username
     * 
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if user exists by email
     * 
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by role
     * 
     * @param role the role to search for
     * @return List of users with the specified role
     */
    List<User> findByRole(UserRole role);
    
    /**
     * Find active users
     * 
     * @return List of active users
     */
    List<User> findByIsActiveTrue();
    
    /**
     * Find inactive users
     * 
     * @return List of inactive users
     */
    List<User> findByIsActiveFalse();
    
    /**
     * Find users by role and active status
     * 
     * @param role the role to search for
     * @param isActive the active status
     * @return List of users matching the criteria
     */
    List<User> findByRoleAndIsActive(UserRole role, boolean isActive);
    
    /**
     * Custom query to find users by partial name match
     * 
     * @param fullName partial name to search for
     * @return List of users whose full name contains the search term
     */
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:fullName%")
    List<User> findByFullNameContaining(@Param("fullName") String fullName);
    
    /**
     * Custom query to find users by role and partial name match
     * 
     * @param role the role to search for
     * @param fullName partial name to search for
     * @return List of users matching both criteria
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.fullName LIKE %:fullName%")
    List<User> findByRoleAndFullNameContaining(@Param("role") UserRole role, @Param("fullName") String fullName);
    
    /**
     * Count users by role
     * 
     * @param role the role to count
     * @return number of users with the specified role
     */
    long countByRole(UserRole role);
    
    /**
     * Count active users by role
     * 
     * @param role the role to count
     * @return number of active users with the specified role
     */
    long countByRoleAndIsActiveTrue(UserRole role);
}