package com.school.aet.resource;

import com.school.aet.aet.AetStatement;
import com.school.aet.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "resources")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type;

    @Column
    private String url;

    @Column(name = "file_path")
    private String filePath;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
        name = "resource_tags",
        joinColumns = @JoinColumn(name = "resource_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private List<ResourceTag> tags = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "resource_aet_statements",
        joinColumns = @JoinColumn(name = "resource_id"),
        inverseJoinColumns = @JoinColumn(name = "statement_id")
    )
    @Builder.Default
    private List<AetStatement> mappedStatements = new ArrayList<>();

    @Column(name = "age_range_min")
    private Integer ageRangeMin;

    @Column(name = "age_range_max")
    private Integer ageRangeMax;
    
    // Explicit getters for compatibility
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public ResourceType getType() { return type; }
    public String getDescription() { return description; }
    public List<ResourceTag> getTags() { return tags; }
    public List<AetStatement> getMappedStatements() { return mappedStatements; }
    public Integer getAgeRangeMin() { return ageRangeMin; }
    public Integer getAgeRangeMax() { return ageRangeMax; }
}

