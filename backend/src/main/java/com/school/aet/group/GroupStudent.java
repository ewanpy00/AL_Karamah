package com.school.aet.group;

import com.school.aet.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "group_students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    @Column(name = "leave_at")
    private Instant leaveAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private GroupStudentRole role = GroupStudentRole.CORE;
    
    // Explicit getters and setters for compatibility
    public Student getStudent() { return student; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public void setStudent(Student student) { this.student = student; }
    public void setJoinedAt(java.time.Instant joinedAt) { this.joinedAt = joinedAt; }
    public void setRole(GroupStudentRole role) { this.role = role; }
    public void setLeaveAt(java.time.Instant leaveAt) { this.leaveAt = leaveAt; }
}

