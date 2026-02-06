package com.school.aet.session;

import com.school.aet.aet.AetStatement;
import com.school.aet.group.Group;
import com.school.aet.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.PLANNED;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToMany
    @JoinTable(
        name = "session_aet_statements",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "statement_id")
    )
    @Builder.Default
    private List<AetStatement> aetFocusStatements = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_suggestions_status")
    @Builder.Default
    private ResourceSuggestionsStatus resourceSuggestionsStatus = ResourceSuggestionsStatus.NOT_REQUESTED;
    
    // Explicit getters and setters for compatibility
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public Group getGroup() { return group; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public User getOwner() { return owner; }
    public SessionStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public List<AetStatement> getAetFocusStatements() { return aetFocusStatements; }
    public void setAetFocusStatements(List<AetStatement> statements) { this.aetFocusStatements = statements; }
    public void setTitle(String title) { this.title = title; }
    public void setGroup(Group group) { this.group = group; }
    public void setStartTime(Instant time) { this.startTime = time; }
    public void setEndTime(Instant time) { this.endTime = time; }
    public void setLocation(String location) { this.location = location; }
    public void setOwner(User owner) { this.owner = owner; }
    public void setStatus(SessionStatus status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
}

