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
public class ResourceSuggestionDto {
    private UUID resourceId;
    private String title;
    private String type;
    private List<String> tags;
    private Integer score;
    private String reason;
    
    // Explicit setters and getters for compatibility
    public void setResourceId(UUID id) { this.resourceId = id; }
    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setScore(Integer score) { this.score = score; }
    public void setReason(String reason) { this.reason = reason; }
    public Integer getScore() { return score; }
}

