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
public class ResourceSuggestionResponse {
    private UUID sessionId;
    private List<ResourceSuggestionDto> suggestions;
    
    // Explicit setters for compatibility
    public void setSessionId(UUID id) { this.sessionId = id; }
    public void setSuggestions(List<ResourceSuggestionDto> suggestions) { this.suggestions = suggestions; }
}

