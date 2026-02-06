package com.school.aet.group.dto;

import com.school.aet.student.dto.StudentSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentsToGroupResponse {
    private List<StudentSummaryDto> addedStudents;
    private List<CompatibilityIssueDto> compatibilityWarnings;
    
    // Explicit setters for compatibility
    public void setAddedStudents(List<StudentSummaryDto> students) { this.addedStudents = students; }
    public void setCompatibilityWarnings(List<CompatibilityIssueDto> warnings) { this.compatibilityWarnings = warnings; }
}

