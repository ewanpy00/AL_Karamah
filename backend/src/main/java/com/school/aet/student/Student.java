package com.school.aet.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "primary_needs_notes", columnDefinition = "TEXT")
    private String primaryNeedsNotes;

    @Column(name = "communication_notes", columnDefinition = "TEXT")
    private String communicationNotes;

    @Column(name = "sensory_notes", columnDefinition = "TEXT")
    private String sensoryNotes;

    @Column(name = "general_notes", columnDefinition = "TEXT")
    private String generalNotes;

    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    // Explicit getters and setters for compatibility
    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPrimaryNeedsNotes() { return primaryNeedsNotes; }
    public String getCommunicationNotes() { return communicationNotes; }
    public String getSensoryNotes() { return sensoryNotes; }
    public String getGeneralNotes() { return generalNotes; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    
    public void setFirstName(String name) { this.firstName = name; }
    public void setLastName(String name) { this.lastName = name; }
    public void setDateOfBirth(LocalDate dob) { this.dateOfBirth = dob; }
    public void setPrimaryNeedsNotes(String notes) { this.primaryNeedsNotes = notes; }
    public void setCommunicationNotes(String notes) { this.communicationNotes = notes; }
    public void setSensoryNotes(String notes) { this.sensoryNotes = notes; }
    public void setGeneralNotes(String notes) { this.generalNotes = notes; }
}

