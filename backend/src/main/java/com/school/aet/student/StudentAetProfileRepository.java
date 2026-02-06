package com.school.aet.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentAetProfileRepository extends JpaRepository<StudentAetProfile, UUID> {
    List<StudentAetProfile> findByStudentId(UUID studentId);
    List<StudentAetProfile> findByStudentIdAndStatementId(UUID studentId, UUID statementId);
}

