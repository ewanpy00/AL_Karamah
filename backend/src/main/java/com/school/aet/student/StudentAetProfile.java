package com.school.aet.student;

import com.school.aet.aet.AetLevel;
import com.school.aet.aet.AetStatement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "student_aet_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAetProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statement_id", nullable = false)
    private AetStatement statement;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_level")
    private AetLevel currentLevel;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;
    
    // Explicit getters for compatibility
    public AetStatement getStatement() { return statement; }
    public AetLevel getCurrentLevel() { return currentLevel; }
    public Instant getLastUpdated() { return lastUpdated; }
}

