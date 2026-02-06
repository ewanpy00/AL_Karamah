package com.school.aet.group.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddStudentsToGroupRequest {
    @NotEmpty
    private List<UUID> studentIds;
    
    // Explicit getter for compatibility
    public List<UUID> getStudentIds() {
        return studentIds;
    }
    
    public void setStudentIds(List<UUID> studentIds) {
        this.studentIds = studentIds;
    }
}

