package com.school.aet.aet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AetDomainRepository extends JpaRepository<AetDomain, UUID> {
}

