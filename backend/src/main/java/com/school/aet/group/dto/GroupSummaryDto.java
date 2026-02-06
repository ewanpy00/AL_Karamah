package com.school.aet.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupSummaryDto {
    private UUID id;
    private String name;
    private Integer ageRangeMin;
    private Integer ageRangeMax;
    private String focusDomainName;
    private Integer studentCount;
    private Integer compatibilityScore;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAgeRangeMin(Integer min) { this.ageRangeMin = min; }
    public void setAgeRangeMax(Integer max) { this.ageRangeMax = max; }
    public void setFocusDomainName(String name) { this.focusDomainName = name; }
    public void setStudentCount(Integer count) { this.studentCount = count; }
    public void setCompatibilityScore(Integer score) { this.compatibilityScore = score; }
}

