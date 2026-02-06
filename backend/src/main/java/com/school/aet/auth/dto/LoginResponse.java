package com.school.aet.auth.dto;

import com.school.aet.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class LoginResponse {
    private String accessToken;
    private UserDto user;
    
    // Explicit getters and setters for compatibility (without Lombok)
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String token) { this.accessToken = token; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public static class UserDto {
        private UUID id;
        private String fullName;
        private String email;
        private Role role;
        
        // Explicit getters and setters for compatibility (without Lombok)
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String name) { this.fullName = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }
}

