package com.school.aet.resource.dto;

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
public class ResourceSummaryDto {
    private UUID id;
    private String title;
    private String type;
    private String description;
    private List<String> tags;
    private List<String> mappedStatementCodes;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setMappedStatementCodes(List<String> codes) { this.mappedStatementCodes = codes; }
}

