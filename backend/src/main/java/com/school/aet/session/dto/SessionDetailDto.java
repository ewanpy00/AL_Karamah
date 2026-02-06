package com.school.aet.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailDto {
    private UUID id;
    private String title;
    private UUID groupId;
    private String groupName;
    private String startTime;
    private String endTime;
    private String location;
    private UUID ownerId;
    private String ownerName;
    private String status;
    private String notes;
    private List<AetStatementDto> aetFocusStatements;
    private List<ResourceDto> selectedResources;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setGroupId(UUID id) { this.groupId = id; }
    public void setGroupName(String name) { this.groupName = name; }
    public void setStartTime(String time) { this.startTime = time; }
    public void setEndTime(String time) { this.endTime = time; }
    public void setLocation(String location) { this.location = location; }
    public void setOwnerId(UUID id) { this.ownerId = id; }
    public void setOwnerName(String name) { this.ownerName = name; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setAetFocusStatements(List<AetStatementDto> statements) { this.aetFocusStatements = statements; }
    public void setSelectedResources(List<ResourceDto> resources) { this.selectedResources = resources; }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AetStatementDto {
        private UUID id;
        private String code;
        private String description;
        private String domainName;
        
        // Explicit setters for compatibility
        public void setId(UUID id) { this.id = id; }
        public void setCode(String code) { this.code = code; }
        public void setDescription(String description) { this.description = description; }
        public void setDomainName(String name) { this.domainName = name; }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResourceDto {
        private UUID id;
        private String title;
        private String type;
        private String role;
        
        // Explicit setters for compatibility
        public void setId(UUID id) { this.id = id; }
        public void setTitle(String title) { this.title = title; }
        public void setType(String type) { this.type = type; }
        public void setRole(String role) { this.role = role; }
    }
}

