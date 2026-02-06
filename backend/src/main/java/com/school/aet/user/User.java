package com.school.aet.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "slack_user_id")
    private String slackUserId;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
    
    // Explicit getters and setters for compatibility
    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public Boolean getActive() { return active; }
    
    public void setFullName(String name) { this.fullName = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String hash) { this.passwordHash = hash; }
    public void setRole(Role role) { this.role = role; }
    public void setActive(Boolean active) { this.active = active; }
}

