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
public class StudentSummaryDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String keyNotes;
    
    // Explicit getters and setters for compatibility
    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Integer getAge() { return age; }
    public String getKeyNotes() { return keyNotes; }
    
    public void setId(UUID id) { this.id = id; }
    public void setFirstName(String name) { this.firstName = name; }
    public void setLastName(String name) { this.lastName = name; }
    public void setAge(Integer age) { this.age = age; }
    public void setKeyNotes(String notes) { this.keyNotes = notes; }
}

