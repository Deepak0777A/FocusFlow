package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.entity.UserRole;
import com.example.studentmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * UserService Class
 * 
 * This service class contains business logic for user operations
 * It handles user registration, authentication, and management
 * 
 * @Service: Marks this class as a Spring service component
 * @Transactional: Ensures database operations are wrapped in transactions
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Load user by username for Spring Security
     * 
     * @param username the username to load
     * @return UserDetails object
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    
    /**
     * Register a new user
     * 
     * @param user the user to register
     * @return the saved user
     * @throws RuntimeException if username or email already exists
     */
    public User registerUser(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default values
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        
        // If no role is specified, default to STUDENT
        if (user.getRole() == null) {
            user.setRole(UserRole.STUDENT);
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Find user by ID
     * 
     * @param id the user ID
     * @return Optional containing the user if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Find user by username
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find user by email
     * 
     * @param email the email
     * @return Optional containing the user if found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Get all users
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get users by role
     * 
     * @param role the role to filter by
     * @return List of users with the specified role
     */
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    /**
     * Get active users
     * 
     * @return List of active users
     */
    public List<User> getActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }
    
    /**
     * Get inactive users
     * 
     * @return List of inactive users
     */
    public List<User> getInactiveUsers() {
        return userRepository.findByIsActiveFalse();
    }
    
    /**
     * Update user information
     * 
     * @param id the user ID
     * @param updatedUser the updated user information
     * @return the updated user
     * @throws RuntimeException if user not found
     */
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Update fields
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        // Only update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        return userRepository.save(existingUser);
    }
    
    /**
     * Change user password
     * 
     * @param id the user ID
     * @param newPassword the new password
     * @return the updated user
     * @throws RuntimeException if user not found
     */
    public User changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    /**
     * Activate user
     * 
     * @param id the user ID
     * @return the activated user
     * @throws RuntimeException if user not found
     */
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    /**
     * Deactivate user
     * 
     * @param id the user ID
     * @return the deactivated user
     * @throws RuntimeException if user not found
     */
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    /**
     * Delete user
     * 
     * @param id the user ID
     * @throws RuntimeException if user not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    /**
     * Search users by name
     * 
     * @param name the name to search for
     * @return List of users whose name contains the search term
     */
    public List<User> searchUsersByName(String name) {
        return userRepository.findByFullNameContaining(name);
    }
    
    /**
     * Search users by role and name
     * 
     * @param role the role to filter by
     * @param name the name to search for
     * @return List of users matching both criteria
     */
    public List<User> searchUsersByRoleAndName(UserRole role, String name) {
        return userRepository.findByRoleAndFullNameContaining(role, name);
    }
    
    /**
     * Count users by role
     * 
     * @param role the role to count
     * @return number of users with the specified role
     */
    public long countUsersByRole(UserRole role) {
        return userRepository.countByRole(role);
    }
    
    /**
     * Count active users by role
     * 
     * @param role the role to count
     * @return number of active users with the specified role
     */
    public long countActiveUsersByRole(UserRole role) {
        return userRepository.countByRoleAndIsActiveTrue(role);
    }
    
    /**
     * Check if username exists
     * 
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    /**
     * Check if email exists
     * 
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}