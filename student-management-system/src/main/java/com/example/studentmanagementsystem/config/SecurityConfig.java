package com.example.studentmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig Class
 * 
 * This class configures Spring Security for our application
 * It defines authentication and authorization rules
 * 
 * @Configuration: Marks this class as a Spring configuration class
 * @EnableWebSecurity: Enables Spring Security web security support
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Password Encoder Bean
     * 
     * BCryptPasswordEncoder is used to hash passwords securely
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Security Filter Chain
     * 
     * This method configures the security rules for our application
     * 
     * @param http HttpSecurity object to configure
     * @return SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity (in production, you should enable it)
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules
            .authorizeHttpRequests(authz -> authz
                // Public URLs - anyone can access
                .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Admin URLs - only ADMIN role can access
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Teacher URLs - TEACHER and ADMIN roles can access
                .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                
                // Student URLs - STUDENT, TEACHER, and ADMIN roles can access
                .requestMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                
                // API URLs - authenticated users can access
                .requestMatchers("/api/**").authenticated()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            
            // Configure form login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            // Configure logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Configure session management
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
            );
        
        return http.build();
    }
}