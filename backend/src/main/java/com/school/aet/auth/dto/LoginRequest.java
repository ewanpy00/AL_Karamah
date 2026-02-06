package com.school.aet.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    
    // Explicit getters for compatibility
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

