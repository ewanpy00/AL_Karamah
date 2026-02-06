package com.school.aet.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("SELECT s FROM Student s WHERE " +
           "(:search IS NULL OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:ageMin IS NULL OR YEAR(CURRENT_DATE) - YEAR(s.dateOfBirth) >= :ageMin) " +
           "AND (:ageMax IS NULL OR YEAR(CURRENT_DATE) - YEAR(s.dateOfBirth) <= :ageMax)")
    List<Student> findByFilters(@Param("search") String search, 
                                 @Param("ageMin") Integer ageMin, 
                                 @Param("ageMax") Integer ageMax);
}

