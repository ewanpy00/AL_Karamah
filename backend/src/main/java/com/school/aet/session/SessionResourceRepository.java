package com.school.aet.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionResourceRepository extends JpaRepository<SessionResource, UUID> {
    List<SessionResource> findBySessionId(UUID sessionId);
}

