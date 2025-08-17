package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.service.StudentService;
import com.example.studentmanagementsystem.service.TeacherService;
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
import java.util.List;

/**
 * TeacherController Class
 * 
 * This controller handles teacher-specific operations
 * It manages teacher dashboard and student management
 * 
 * @Controller: Marks this class as a Spring MVC controller
 * @RequestMapping: Maps all teacher URLs to /teacher
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    
    @Autowired
    public TeacherController(UserService userService, TeacherService teacherService, StudentService studentService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
    
    /**
     * Teacher dashboard
     * 
     * @param model Spring model
     * @return teacher dashboard view
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Teacher teacher = user.getTeacher();
            model.addAttribute("user", user);
            model.addAttribute("teacher", teacher);
            
            if (teacher != null) {
                // Get advisees count
                long adviseesCount = studentService.countStudentsByAdvisor(teacher.getId());
                model.addAttribute("adviseesCount", adviseesCount);
            }
        }
        
        return "teacher/dashboard";
    }
    
    /**
     * Teacher profile page
     * 
     * @param model Spring model
     * @return teacher profile view
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            Teacher teacher = user.getTeacher();
            model.addAttribute("user", user);
            model.addAttribute("teacher", teacher);
        }
        
        return "teacher/profile";
    }
    
    /**
     * Edit teacher profile page
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
            Teacher teacher = user.getTeacher();
            model.addAttribute("user", user);
            model.addAttribute("teacher", teacher);
        }
        
        return "teacher/edit-profile";
    }
    
    /**
     * Update teacher profile
     * 
     * @param updatedTeacher the updated teacher information
     * @param bindingResult validation result
     * @param redirectAttributes for flash messages
     * @return redirect to profile page
     */
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("teacher") Teacher updatedTeacher,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "teacher/edit-profile";
        }
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            User user = userService.findByUsername(username).orElse(null);
            if (user != null && user.getTeacher() != null) {
                Teacher existingTeacher = user.getTeacher();
                
                // Update teacher information
                existingTeacher.setFirstName(updatedTeacher.getFirstName());
                existingTeacher.setLastName(updatedTeacher.getLastName());
                existingTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());
                existingTeacher.setEmail(updatedTeacher.getEmail());
                existingTeacher.setAddress(updatedTeacher.getAddress());
                
                teacherService.updateTeacher(existingTeacher.getId(), existingTeacher);
                
                redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Teacher profile not found!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/teacher/profile";
    }
    
    /**
     * Change password page
     * 
     * @return change password view
     */
    @GetMapping("/change-password")
    public String changePassword() {
        return "teacher/change-password";
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
            return "redirect:/teacher/change-password";
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
        
        return "redirect:/teacher/profile";
    }
    
    /**
     * View advisees (students assigned to teacher)
     * 
     * @param model Spring model
     * @return advisees view
     */
    @GetMapping("/advisees")
    public String viewAdvisees(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        if (user != null && user.getTeacher() != null) {
            Teacher teacher = user.getTeacher();
            List<Student> advisees = teacherService.getAdvisees(teacher.getId());
            
            model.addAttribute("teacher", teacher);
            model.addAttribute("advisees", advisees);
        }
        
        return "teacher/advisees";
    }
    
    /**
     * View specific advisee details
     * 
     * @param studentId the student ID
     * @param model Spring model
     * @return advisee details view
     */
    @GetMapping("/advisees/{studentId}")
    public String viewAdviseeDetails(@PathVariable Long studentId, Model model) {
        Student student = studentService.findById(studentId).orElse(null);
        if (student != null) {
            model.addAttribute("student", student);
        }
        
        return "teacher/advisee-details";
    }
    
    /**
     * Update student GPA
     * 
     * @param studentId the student ID
     * @param gpa the new GPA
     * @param redirectAttributes for flash messages
     * @return redirect to advisee details
     */
    @PostMapping("/advisees/{studentId}/update-gpa")
    public String updateStudentGpa(@PathVariable Long studentId,
                                 @RequestParam Double gpa,
                                 RedirectAttributes redirectAttributes) {
        try {
            studentService.updateGpa(studentId, gpa);
            redirectAttributes.addFlashAttribute("success", "Student GPA updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/teacher/advisees/" + studentId;
    }
    
    /**
     * Update student credits
     * 
     * @param studentId the student ID
     * @param credits the new total credits
     * @param redirectAttributes for flash messages
     * @return redirect to advisee details
     */
    @PostMapping("/advisees/{studentId}/update-credits")
    public String updateStudentCredits(@PathVariable Long studentId,
                                     @RequestParam Integer credits,
                                     RedirectAttributes redirectAttributes) {
        try {
            studentService.updateCredits(studentId, credits);
            redirectAttributes.addFlashAttribute("success", "Student credits updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/teacher/advisees/" + studentId;
    }
    
    /**
     * Change student status
     * 
     * @param studentId the student ID
     * @param status the new status
     * @param redirectAttributes for flash messages
     * @return redirect to advisee details
     */
    @PostMapping("/advisees/{studentId}/change-status")
    public String changeStudentStatus(@PathVariable Long studentId,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) {
        try {
            studentService.changeStatus(studentId, com.example.studentmanagementsystem.entity.StudentStatus.valueOf(status));
            redirectAttributes.addFlashAttribute("success", "Student status updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/teacher/advisees/" + studentId;
    }
    
    /**
     * View all students (for teachers)
     * 
     * @param model Spring model
     * @return all students view
     */
    @GetMapping("/students")
    public String viewAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "teacher/students";
    }
    
    /**
     * Search students
     * 
     * @param query search query
     * @param model Spring model
     * @return students view with search results
     */
    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String query, Model model) {
        List<Student> students = studentService.searchStudentsByName(query);
        model.addAttribute("students", students);
        model.addAttribute("searchQuery", query);
        return "teacher/students";
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
            Teacher teacher = user.getTeacher();
            model.addAttribute("teacher", teacher);
        }
        
        return "teacher/schedule";
    }
}