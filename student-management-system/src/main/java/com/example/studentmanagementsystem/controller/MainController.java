package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.entity.UserRole;
import com.example.studentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

/**
 * MainController Class
 * 
 * This controller handles basic web pages and user authentication
 * It manages home page, login, registration, and dashboard
 * 
 * @Controller: Marks this class as a Spring MVC controller
 */
@Controller
public class MainController {
    
    private final UserService userService;
    
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Home page
     * 
     * @return home page view
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    /**
     * Home page (alternative mapping)
     * 
     * @return home page view
     */
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    
    /**
     * Login page
     * 
     * @return login page view
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Registration page
     * 
     * @param model Spring model
     * @return registration page view
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }
    
    /**
     * Process user registration
     * 
     * @param user the user to register
     * @param bindingResult validation result
     * @param redirectAttributes for flash messages
     * @return redirect to login page on success
     */
    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") User user,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        try {
            // Register the user
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    /**
     * Dashboard page
     * 
     * @param model Spring model
     * @return dashboard page view
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.findByUsername(username).orElse(null);
        
        if (user != null) {
            model.addAttribute("user", user);
            
            // Add role-specific data
            switch (user.getRole()) {
                case ADMIN:
                    return "redirect:/admin/dashboard";
                case TEACHER:
                    return "redirect:/teacher/dashboard";
                case STUDENT:
                    return "redirect:/student/dashboard";
                default:
                    return "dashboard";
            }
        }
        
        return "dashboard";
    }
    
    /**
     * Access denied page
     * 
     * @return access denied page view
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
    
    /**
     * Error page
     * 
     * @return error page view
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}