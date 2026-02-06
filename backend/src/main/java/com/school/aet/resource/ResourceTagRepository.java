package com.school.aet.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceTagRepository extends JpaRepository<ResourceTag, UUID> {
    Optional<ResourceTag> findByName(String name);
}

