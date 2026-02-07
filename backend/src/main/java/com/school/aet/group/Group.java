package com.school.aet.group;

import com.school.aet.aet.AetDomain;
import com.school.aet.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "age_range_min", nullable = false)
    private Integer ageRangeMin;

    @Column(name = "age_range_max", nullable = false)
    private Integer ageRangeMax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_domain_id")
    private AetDomain focusDomain;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "compatibility_notes", columnDefinition = "TEXT")
    private String compatibilityNotes;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
    
    // Explicit getters for compatibility
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Integer getAgeRangeMin() { return ageRangeMin; }
    public Integer getAgeRangeMax() { return ageRangeMax; }
    public AetDomain getFocusDomain() { return focusDomain; }
    public String getDescription() { return description; }
}
