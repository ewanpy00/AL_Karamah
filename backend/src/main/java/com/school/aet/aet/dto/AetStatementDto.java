package com.school.aet.aet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AetStatementDto {
    private UUID id;
    private UUID domainId;
    private String code;
    private String description;
    private Integer ageRangeMin;
    private Integer ageRangeMax;
    private String level;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setDomainId(UUID id) { this.domainId = id; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setAgeRangeMin(Integer min) { this.ageRangeMin = min; }
    public void setAgeRangeMax(Integer max) { this.ageRangeMax = max; }
    public void setLevel(String level) { this.level = level; }
}

