package com.school.aet.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByActiveTrue();
    
    @Query("SELECT g FROM Group g WHERE g.active = :active " +
           "AND (:focusDomainId IS NULL OR g.focusDomain.id = :focusDomainId) " +
           "AND (:ageMin IS NULL OR g.ageRangeMin <= :ageMin) " +
           "AND (:ageMax IS NULL OR g.ageRangeMax >= :ageMax)")
    List<Group> findByFilters(@Param("active") Boolean active,
                              @Param("focusDomainId") UUID focusDomainId,
                              @Param("ageMin") Integer ageMin,
                              @Param("ageMax") Integer ageMax);
}

