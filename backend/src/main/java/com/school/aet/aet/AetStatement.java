package com.school.aet.aet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "aet_statements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AetStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    private AetDomain domain;

    @Column(nullable = false)
    private String code; // e.g., COMM-01

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "age_range_min")
    private Integer ageRangeMin;

    @Column(name = "age_range_max")
    private Integer ageRangeMax;

    @Enumerated(EnumType.STRING)
    private AetLevel level;
    
    // Explicit getters for compatibility
    public UUID getId() { return id; }
    public AetDomain getDomain() { return domain; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Integer getAgeRangeMin() { return ageRangeMin; }
    public Integer getAgeRangeMax() { return ageRangeMax; }
    public AetLevel getLevel() { return level; }
}

