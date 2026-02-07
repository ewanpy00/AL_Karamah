package com.school.aet.session.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UpdateSessionRequest {
    private String title;
    private Instant startTime;
    private Instant endTime;
    private String location;
    private String notes;

    public String getTitle() { return title; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public String getNotes() { return notes; }
}
