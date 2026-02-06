package com.school.aet.aet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AetStatementRepository extends JpaRepository<AetStatement, UUID> {
    List<AetStatement> findByDomainId(UUID domainId);
    
    @Query("SELECT s FROM AetStatement s WHERE s.domain.id = :domainId " +
           "AND (s.ageRangeMin IS NULL OR s.ageRangeMin <= :age) " +
           "AND (s.ageRangeMax IS NULL OR s.ageRangeMax >= :age)")
    List<AetStatement> findByDomainIdAndAge(@Param("domainId") UUID domainId, @Param("age") Integer age);
}

