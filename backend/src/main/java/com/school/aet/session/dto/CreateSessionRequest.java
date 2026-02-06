package com.school.aet.session.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class CreateSessionRequest {
    @NotBlank
    private String title;

    @NotNull
    private UUID groupId;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotBlank
    private String location;

    private String notes;
    private List<UUID> aetStatementIds;
    
    // Explicit getters for compatibility
    public String getTitle() { return title; }
    public UUID getGroupId() { return groupId; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getNotes() { return notes; }
    public List<UUID> getAetStatementIds() { return aetStatementIds; }
}

