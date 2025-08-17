package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.Student;
import com.example.studentmanagementsystem.entity.Teacher;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.entity.UserRole;
import com.example.studentmanagementsystem.service.StudentService;
import com.example.studentmanagementsystem.service.TeacherService;
import com.example.studentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * AdminController Class
 * 
 * This controller handles admin-specific operations
 * It manages admin dashboard and user management
 * 
 * @Controller: Marks this class as a Spring MVC controller
 * @RequestMapping: Maps all admin URLs to /admin
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    
    @Autowired
    public AdminController(UserService userService, StudentService studentService, TeacherService teacherService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }
    
    /**
     * Admin dashboard
     * 
     * @param model Spring model
     * @return admin dashboard view
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User admin = userService.findByUsername(username).orElse(null);
        model.addAttribute("admin", admin);
        
        // Get statistics
        long totalStudents = studentService.getTotalStudents();
        long totalTeachers = teacherService.getTotalTeachers();
        long totalUsers = userService.getAllUsers().size();
        
        long activeStudents = studentService.countStudentsByStatus(com.example.studentmanagementsystem.entity.StudentStatus.ACTIVE);
        long activeTeachers = teacherService.countTeachersByStatus(com.example.studentmanagementsystem.entity.TeacherStatus.ACTIVE);
        
        Double averageGpa = studentService.getAverageGpa();
        Double averageSalary = teacherService.getAverageSalary();
        
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalTeachers", totalTeachers);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("activeStudents", activeStudents);
        model.addAttribute("activeTeachers", activeTeachers);
        model.addAttribute("averageGpa", averageGpa);
        model.addAttribute("averageSalary", averageSalary);
        
        return "admin/dashboard";
    }
    
    /**
     * User management page
     * 
     * @param model Spring model
     * @return user management view
     */
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    /**
     * Student management page
     * 
     * @param model Spring model
     * @return student management view
     */
    @GetMapping("/students")
    public String manageStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "admin/students";
    }
    
    /**
     * Teacher management page
     * 
     * @param model Spring model
     * @return teacher management view
     */
    @GetMapping("/teachers")
    public String manageTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }
    
    /**
     * Activate user
     * 
     * @param userId the user ID
     * @param redirectAttributes for flash messages
     * @return redirect to users page
     */
    @PostMapping("/users/{userId}/activate")
    public String activateUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.activateUser(userId);
            redirectAttributes.addFlashAttribute("success", "User activated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    /**
     * Deactivate user
     * 
     * @param userId the user ID
     * @param redirectAttributes for flash messages
     * @return redirect to users page
     */
    @PostMapping("/users/{userId}/deactivate")
    public String deactivateUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.deactivateUser(userId);
            redirectAttributes.addFlashAttribute("success", "User deactivated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    /**
     * Delete user
     * 
     * @param userId the user ID
     * @param redirectAttributes for flash messages
     * @return redirect to users page
     */
    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    /**
     * Delete student
     * 
     * @param studentId the student ID
     * @param redirectAttributes for flash messages
     * @return redirect to students page
     */
    @PostMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable Long studentId, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(studentId);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }
    
    /**
     * Delete teacher
     * 
     * @param teacherId the teacher ID
     * @param redirectAttributes for flash messages
     * @return redirect to teachers page
     */
    @PostMapping("/teachers/{teacherId}/delete")
    public String deleteTeacher(@PathVariable Long teacherId, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(teacherId);
            redirectAttributes.addFlashAttribute("success", "Teacher deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/teachers";
    }
    
    /**
     * Search users
     * 
     * @param query search query
     * @param model Spring model
     * @return user management view with search results
     */
    @GetMapping("/users/search")
    public String searchUsers(@RequestParam String query, Model model) {
        List<User> users = userService.searchUsersByName(query);
        model.addAttribute("users", users);
        model.addAttribute("searchQuery", query);
        return "admin/users";
    }
    
    /**
     * Search students
     * 
     * @param query search query
     * @param model Spring model
     * @return student management view with search results
     */
    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String query, Model model) {
        List<Student> students = studentService.searchStudentsByName(query);
        model.addAttribute("students", students);
        model.addAttribute("searchQuery", query);
        return "admin/students";
    }
    
    /**
     * Search teachers
     * 
     * @param query search query
     * @param model Spring model
     * @return teacher management view with search results
     */
    @GetMapping("/teachers/search")
    public String searchTeachers(@RequestParam String query, Model model) {
        List<Teacher> teachers = teacherService.searchTeachersByName(query);
        model.addAttribute("teachers", teachers);
        model.addAttribute("searchQuery", query);
        return "admin/teachers";
    }
    
    /**
     * Get users by role
     * 
     * @param role the role to filter by
     * @param model Spring model
     * @return user management view with filtered results
     */
    @GetMapping("/users/role/{role}")
    public String getUsersByRole(@PathVariable UserRole role, Model model) {
        List<User> users = userService.getUsersByRole(role);
        model.addAttribute("users", users);
        model.addAttribute("selectedRole", role);
        return "admin/users";
    }
}