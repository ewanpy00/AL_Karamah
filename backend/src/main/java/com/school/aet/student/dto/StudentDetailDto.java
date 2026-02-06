package com.school.aet.student.dto;

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
public class StudentDetailDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String primaryNeedsNotes;
    private String communicationNotes;
    private String sensoryNotes;
    private String generalNotes;
    private List<AetProfileEntryDto> aetProfile;
    
    // Explicit getters and setters for compatibility
    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Integer getAge() { return age; }
    public String getPrimaryNeedsNotes() { return primaryNeedsNotes; }
    public String getCommunicationNotes() { return communicationNotes; }
    public String getSensoryNotes() { return sensoryNotes; }
    public String getGeneralNotes() { return generalNotes; }
    public List<AetProfileEntryDto> getAetProfile() { return aetProfile; }
    
    public void setId(UUID id) { this.id = id; }
    public void setFirstName(String name) { this.firstName = name; }
    public void setLastName(String name) { this.lastName = name; }
    public void setAge(Integer age) { this.age = age; }
    public void setPrimaryNeedsNotes(String notes) { this.primaryNeedsNotes = notes; }
    public void setCommunicationNotes(String notes) { this.communicationNotes = notes; }
    public void setSensoryNotes(String notes) { this.sensoryNotes = notes; }
    public void setGeneralNotes(String notes) { this.generalNotes = notes; }
    public void setAetProfile(List<AetProfileEntryDto> profile) { this.aetProfile = profile; }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AetProfileEntryDto {
        private UUID domainId;
        private String domainName;
        private UUID statementId;
        private String statementCode;
        private String statementDescription;
        private String currentLevel;
        private String lastUpdated;
        
        // Explicit getters and setters for compatibility
        public UUID getDomainId() { return domainId; }
        public String getDomainName() { return domainName; }
        public UUID getStatementId() { return statementId; }
        public String getStatementCode() { return statementCode; }
        public String getStatementDescription() { return statementDescription; }
        public String getCurrentLevel() { return currentLevel; }
        public String getLastUpdated() { return lastUpdated; }
        
        public void setDomainId(UUID id) { this.domainId = id; }
        public void setDomainName(String name) { this.domainName = name; }
        public void setStatementId(UUID id) { this.statementId = id; }
        public void setStatementCode(String code) { this.statementCode = code; }
        public void setStatementDescription(String desc) { this.statementDescription = desc; }
        public void setCurrentLevel(String level) { this.currentLevel = level; }
        public void setLastUpdated(String updated) { this.lastUpdated = updated; }
    }
}

