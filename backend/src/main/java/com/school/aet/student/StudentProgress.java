package com.school.aet.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "student_progress",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_progress_item", columnNames = {"student_id", "item_key"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "item_key", nullable = false, length = 64)
    private String itemKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentProgressStatus status;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public UUID getId() { return id; }
    public Student getStudent() { return student; }
    public String getItemKey() { return itemKey; }
    public StudentProgressStatus getStatus() { return status; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setStudent(Student student) { this.student = student; }
    public void setItemKey(String itemKey) { this.itemKey = itemKey; }
    public void setStatus(StudentProgressStatus status) { this.status = status; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
