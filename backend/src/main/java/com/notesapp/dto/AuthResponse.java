package com.notesapp.dto;

import com.notesapp.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthResponse {
    private UserDto user;
    private String token;
    
    public AuthResponse() {}
    
    public AuthResponse(User user, String token) {
        this.user = new UserDto(user);
        this.token = token;
    }
    
    // Getters and Setters
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public static class UserDto {
        private UUID id;
        private String email;
        private String firstName;
        private String lastName;
        private LocalDateTime createdAt;
        
        public UserDto() {}
        
        public UserDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.createdAt = user.getCreatedAt();
        }
        
        // Getters and Setters
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}