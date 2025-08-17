package com.example.studentmanagementsystem.entity;

/**
 * TeacherStatus Enum - Defines the different statuses a teacher can have
 * 
 * This enum is used to track the current status of teachers in the system
 */
public enum TeacherStatus {
    
    /**
     * ACTIVE - Teacher is currently employed and active
     */
    ACTIVE("Active"),
    
    /**
     * INACTIVE - Teacher is not currently active
     */
    INACTIVE("Inactive"),
    
    /**
     * ON_LEAVE - Teacher is on leave
     */
    ON_LEAVE("On Leave"),
    
    /**
     * RETIRED - Teacher has retired
     */
    RETIRED("Retired"),
    
    /**
     * TERMINATED - Teacher's employment has been terminated
     */
    TERMINATED("Terminated");
    
    private final String displayName;
    
    TeacherStatus(String displayName) {
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