package com.school.aet.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Query("SELECT s FROM Session s WHERE s.startTime >= :from AND s.startTime <= :to " +
           "AND s.group.active = true " +
           "AND (:groupId IS NULL OR s.group.id = :groupId) " +
           "AND (:ownerId IS NULL OR s.owner.id = :ownerId) " +
           "ORDER BY s.startTime ASC")
    List<Session> findCalendarSessions(@Param("from") Instant from,
                                        @Param("to") Instant to,
                                        @Param("groupId") UUID groupId,
                                        @Param("ownerId") UUID ownerId);
    
    List<Session> findByGroupId(UUID groupId);
}
