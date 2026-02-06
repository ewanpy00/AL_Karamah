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
public class CalendarSessionDto {
    private UUID id;
    private String title;
    private UUID groupId;
    private String groupName;
    private String startTime;
    private String endTime;
    private String focusDomainCode;
    private String focusDomainName;
    private String status;
    private List<String> aetTags;
    
    // Explicit setters for compatibility
    public void setId(UUID id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setGroupId(UUID id) { this.groupId = id; }
    public void setGroupName(String name) { this.groupName = name; }
    public void setStartTime(String time) { this.startTime = time; }
    public void setEndTime(String time) { this.endTime = time; }
    public void setFocusDomainCode(String code) { this.focusDomainCode = code; }
    public void setFocusDomainName(String name) { this.focusDomainName = name; }
    public void setStatus(String status) { this.status = status; }
    public void setAetTags(List<String> tags) { this.aetTags = tags; }
}

