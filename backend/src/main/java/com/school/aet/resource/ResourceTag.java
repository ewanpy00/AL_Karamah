package com.school.aet.resource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "resource_tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "low-arousal", "small group"
    
    // Explicit getters for compatibility
    public String getName() { return name; }
}

