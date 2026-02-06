package com.school.aet.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
    @Query("SELECT DISTINCT r FROM Resource r " +
           "LEFT JOIN r.mappedStatements ms " +
           "WHERE (:search IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:domainId IS NULL OR ms.domain.id = :domainId) " +
           "AND (:statementId IS NULL OR ms.id = :statementId) " +
           "AND (:type IS NULL OR r.type = :type) " +
           "AND (:ageMin IS NULL OR r.ageRangeMin IS NULL OR r.ageRangeMin <= :ageMin) " +
           "AND (:ageMax IS NULL OR r.ageRangeMax IS NULL OR r.ageRangeMax >= :ageMax)")
    List<Resource> findByFilters(@Param("search") String search,
                                  @Param("domainId") UUID domainId,
                                  @Param("statementId") UUID statementId,
                                  @Param("type") ResourceType type,
                                  @Param("ageMin") Integer ageMin,
                                  @Param("ageMax") Integer ageMax);
}

