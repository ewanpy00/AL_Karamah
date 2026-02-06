package com.school.aet.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityIssueDto {
    private String type;
    private String message;
    private String severity;
    
    // Explicit setters for compatibility
    public void setType(String type) { this.type = type; }
    public void setMessage(String message) { this.message = message; }
    public void setSeverity(String severity) { this.severity = severity; }
}

