package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.service.StudentService;
import com.example.studentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

/**
 * StudentController Class
 * 
 * This controller handles student-specific operations
 * It manages student dashboard and profile management
 * 
 * @Controller: Marks this class as a Spring MVC controller
 * @RequestMapping: Maps all student URLs to /student
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    
    private final UserService userService;
    private final StudentService studentService;
    
    @Autowired
    public StudentController(UserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }
    
    /**
     * Student dashboard
     * 
     * @param model Spring model
     * @return student dashboard view
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("user", user);
            model.addAttribute("student", student);
        }
        
        return "student/dashboard";
    }
    
    /**
     * Student profile page
     * 
     * @param model Spring model
     * @return student profile view
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("user", user);
            model.addAttribute("student", student);
        }
        
        return "student/profile";
    }
    
    /**
     * Edit student profile page
     * 
     * @param model Spring model
     * @return edit profile view
     */
    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("user", user);
            model.addAttribute("student", student);
        }
        
        return "student/edit-profile";
    }
    
    /**
     * Update student profile
     * 
     * @param updatedStudent the updated student information
     * @param bindingResult validation result
     * @param redirectAttributes for flash messages
     * @return redirect to profile page
     */
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("student") Student updatedStudent,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "student/edit-profile";
        }
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            User user = userService.findByUsername(username).orElse(null);
            if (user != null && user.getStudent() != null) {
                Student existingStudent = user.getStudent();
                
                // Update student information
                existingStudent.setFirstName(updatedStudent.getFirstName());
                existingStudent.setLastName(updatedStudent.getLastName());
                existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
                existingStudent.setAddress(updatedStudent.getAddress());
                
                studentService.updateStudent(existingStudent.getId(), existingStudent);
                
                redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Student profile not found!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/student/profile";
    }
    
    /**
     * Change password page
     * 
     * @return change password view
     */
    @GetMapping("/change-password")
    public String changePassword() {
        return "student/change-password";
    }
    
    /**
     * Process password change
     * 
     * @param currentPassword current password
     * @param newPassword new password
     * @param confirmPassword password confirmation
     * @param redirectAttributes for flash messages
     * @return redirect to profile page
     */
    @PostMapping("/change-password")
    public String processPasswordChange(@RequestParam String currentPassword,
                                      @RequestParam String newPassword,
                                      @RequestParam String confirmPassword,
                                      RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "New passwords do not match!");
            return "redirect:/student/change-password";
        }
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                userService.changePassword(user.getId(), newPassword);
                redirectAttributes.addFlashAttribute("success", "Password changed successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/student/profile";
    }
    
    /**
     * View academic records
     * 
     * @param model Spring model
     * @return academic records view
     */
    @GetMapping("/academic-records")
    public String academicRecords(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("student", student);
        }
        
        return "student/academic-records";
    }
    
    /**
     * View schedule
     * 
     * @param model Spring model
     * @return schedule view
     */
    @GetMapping("/schedule")
    public String schedule(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("student", student);
        }
        
        return "student/schedule";
    }
    
    /**
     * View grades
     * 
     * @param model Spring model
     * @return grades view
     */
    @GetMapping("/grades")
    public String grades(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Student student = user.getStudent();
            model.addAttribute("student", student);
        }
        
        return "student/grades";
    }
}