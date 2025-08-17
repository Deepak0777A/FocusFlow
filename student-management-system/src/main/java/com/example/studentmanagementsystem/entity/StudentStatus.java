package com.example.studentmanagementsystem.entity;

/**
 * StudentStatus Enum - Defines the different statuses a student can have
 * 
 * This enum is used to track the current status of students in the system
 */
public enum StudentStatus {
    
    /**
     * ACTIVE - Student is currently enrolled and active
     */
    ACTIVE("Active"),
    
    /**
     * INACTIVE - Student is not currently active
     */
    INACTIVE("Inactive"),
    
    /**
     * GRADUATED - Student has completed their studies
     */
    GRADUATED("Graduated"),
    
    /**
     * SUSPENDED - Student is temporarily suspended
     */
    SUSPENDED("Suspended"),
    
    /**
     * DROPPED - Student has dropped out
     */
    DROPPED("Dropped");
    
    private final String displayName;
    
    StudentStatus(String displayName) {
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