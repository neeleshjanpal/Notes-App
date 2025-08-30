package com.notesapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;
        
        @NotBlank
        @Size(min = 6)
        private String password;
        
        // Constructors
        public LoginRequest() {}
        
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        
        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        @Email
        @NotBlank
        private String email;
        
        @NotBlank
        @Size(min = 6)
        private String password;
        
        @NotBlank
        @Size(max = 100)
        private String firstName;
        
        @NotBlank
        @Size(max = 100)
        private String lastName;
        
        // Constructors
        public RegisterRequest() {}
        
        public RegisterRequest(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }
        
        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
}