package com.example.studentmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class
 * This is the entry point of our Student Management System
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Marks this class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath
 * - @ComponentScan: Tells Spring to look for other components in the com.example package
 */
@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        // This method starts the Spring Boot application
        SpringApplication.run(StudentManagementSystemApplication.class, args);
        System.out.println("🚀 Student Management System is running on http://localhost:8080");
    }
}