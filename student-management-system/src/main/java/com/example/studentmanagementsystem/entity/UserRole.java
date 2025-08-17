package com.example.studentmanagementsystem.entity;

/**
 * UserRole Enum - Defines the different roles a user can have in the system
 * 
 * This enum is used to control access and permissions throughout the application
 */
public enum UserRole {
    
    /**
     * ADMIN - Has full access to all features
     * Can manage users, students, teachers, courses, etc.
     */
    ADMIN("Administrator"),
    
    /**
     * TEACHER - Can manage students and view their own courses
     * Can view and update student information
     */
    TEACHER("Teacher"),
    
    /**
     * STUDENT - Can view and update their own information
     * Limited access to system features
     */
    STUDENT("Student");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}