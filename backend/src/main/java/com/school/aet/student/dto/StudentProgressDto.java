package com.school.aet.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgressDto {
    private UUID id;
    private String itemKey;
    private String status;
    private String updatedAt;

    public void setId(UUID id) { this.id = id; }
    public void setItemKey(String itemKey) { this.itemKey = itemKey; }
    public void setStatus(String status) { this.status = status; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
