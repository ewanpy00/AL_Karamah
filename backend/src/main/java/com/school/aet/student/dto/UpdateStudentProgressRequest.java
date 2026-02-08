package com.school.aet.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStudentProgressRequest {
    @NotBlank
    private String itemKey;

    @NotNull
    private String status;

    public String getItemKey() { return itemKey; }
    public String getStatus() { return status; }
}
