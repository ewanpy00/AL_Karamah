package com.school.aet.session;

import com.school.aet.resource.Resource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "session_resources")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SessionResourceRole role = SessionResourceRole.SUGGESTED;
    
    // Explicit getters for compatibility
    public Resource getResource() { return resource; }
    public SessionResourceRole getRole() { return role; }
}

