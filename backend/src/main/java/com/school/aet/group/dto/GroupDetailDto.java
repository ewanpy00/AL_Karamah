package com.school.aet.group.dto;

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
public class GroupDetailDto {
    private UUID id;
    private String name;
    private Integer ageRangeMin;
    private Integer ageRangeMax;
    private UUID focusDomainId;
    private String focusDomainName;
    private String description;
    private List<StudentInGroupDto> students;
    private CompatibilitySummaryDto compatibilitySummary;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAgeRangeMin(Integer min) { this.ageRangeMin = min; }
    public void setAgeRangeMax(Integer max) { this.ageRangeMax = max; }
    public void setFocusDomainId(UUID id) { this.focusDomainId = id; }
    public void setFocusDomainName(String name) { this.focusDomainName = name; }
    public void setDescription(String desc) { this.description = desc; }
    public void setStudents(List<StudentInGroupDto> students) { this.students = students; }
    public void setCompatibilitySummary(CompatibilitySummaryDto summary) { this.compatibilitySummary = summary; }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentInGroupDto {
        private UUID id;
        private String name;
        private Integer age;
        private List<String> compatibilityFlags;
        
        // Explicit setters for compatibility
        public void setId(UUID id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setAge(Integer age) { this.age = age; }
        public void setCompatibilityFlags(List<String> flags) { this.compatibilityFlags = flags; }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompatibilitySummaryDto {
        private Integer score;
        private List<CompatibilityIssueDto> issues;
        
        // Explicit setters and getters for compatibility
        public void setScore(Integer score) { this.score = score; }
        public void setIssues(List<CompatibilityIssueDto> issues) { this.issues = issues; }
        public Integer getScore() { return score; }
    }
}

