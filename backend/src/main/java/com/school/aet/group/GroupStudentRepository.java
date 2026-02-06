package com.school.aet.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, UUID> {
    List<GroupStudent> findByGroupId(UUID groupId);
    List<GroupStudent> findByStudentId(UUID studentId);
    
    @Query("SELECT gs FROM GroupStudent gs WHERE gs.group.id = :groupId " +
           "AND gs.student.id = :studentId " +
           "AND gs.leaveAt IS NULL")
    Optional<GroupStudent> findActiveByGroupAndStudent(@Param("groupId") UUID groupId, 
                                                        @Param("studentId") UUID studentId);
    
    @Query("SELECT gs.student.id FROM GroupStudent gs WHERE gs.group.id = :groupId " +
           "AND gs.leaveAt IS NULL")
    List<UUID> findActiveStudentIdsByGroupId(@Param("groupId") UUID groupId);
}

