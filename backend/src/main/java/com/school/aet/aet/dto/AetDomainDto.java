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
public class AetDomainDto {
    private UUID id;
    private String code;
    private String name;
    private String description;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}

