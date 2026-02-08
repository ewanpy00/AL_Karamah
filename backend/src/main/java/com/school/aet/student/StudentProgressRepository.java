package com.school.aet.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, UUID> {
    List<StudentProgress> findByStudentId(UUID studentId);
    Optional<StudentProgress> findByStudentIdAndItemKey(UUID studentId, String itemKey);
}
