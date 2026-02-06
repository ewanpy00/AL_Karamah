package com.school.aet.aet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "aet_domains")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AetDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code; // e.g., COMM, SENS, SOC

    @Column(nullable = false)
    private String name; // e.g., Communication, Sensory, Social

    @Column(columnDefinition = "TEXT")
    private String description;
    
    // Explicit getters for compatibility
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
}

