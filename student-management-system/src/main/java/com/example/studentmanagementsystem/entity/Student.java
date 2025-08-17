package com.example.studentmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Student Entity - Represents student information in our system
 * This class has a One-to-One relationship with User entity
 * 
 * @Entity: Maps this class to a database table
 * @Table: Specifies table name and constraints
 */
@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Student ID is required")
    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Pattern(regexp = "^[MF]$", message = "Gender must be M or F")
    @Column(length = 1)
    private String gender;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    
    @Column(name = "graduation_date")
    private LocalDate graduationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentStatus status = StudentStatus.ACTIVE;
    
    @Column(name = "gpa")
    private Double gpa;
    
    @Column(name = "total_credits")
    private Integer totalCredits = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-One relationship with User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Many-to-One relationship with Teacher (advisor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advisor_id")
    private Teacher advisor;
    
    // Constructor
    public Student() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.enrollmentDate = LocalDate.now();
    }
    
    // Pre-persist method to set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }
    
    // Pre-update method to update timestamp
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Helper method to get age
    public Integer getAge() {
        if (dateOfBirth != null) {
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        }
        return null;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public LocalDate getGraduationDate() {
        return graduationDate;
    }
    
    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }
    
    public StudentStatus getStatus() {
        return status;
    }
    
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    
    public Double getGpa() {
        return gpa;
    }
    
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
    
    public Integer getTotalCredits() {
        return totalCredits;
    }
    
    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Teacher getAdvisor() {
        return advisor;
    }
    
    public void setAdvisor(Teacher advisor) {
        this.advisor = advisor;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", gpa=" + gpa +
                '}';
    }
}