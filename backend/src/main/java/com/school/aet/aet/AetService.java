package com.school.aet.aet;

import com.school.aet.aet.dto.AetDomainDto;
import com.school.aet.aet.dto.AetStatementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AetService {
    private final AetDomainRepository domainRepository;
    private final AetStatementRepository statementRepository;
    
    public AetService(AetDomainRepository domainRepository, AetStatementRepository statementRepository) {
        this.domainRepository = domainRepository;
        this.statementRepository = statementRepository;
    }

    public List<AetDomainDto> getAllDomains() {
        return domainRepository.findAll().stream()
                .map(d -> {
                    AetDomainDto dto = new AetDomainDto();
                    dto.setId(d.getId());
                    dto.setCode(d.getCode());
                    dto.setName(d.getName());
                    dto.setDescription(d.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AetStatementDto> getStatementsByDomain(UUID domainId) {
        return statementRepository.findByDomainId(domainId).stream()
                .map(s -> {
                    AetStatementDto dto = new AetStatementDto();
                    dto.setId(s.getId());
                    dto.setDomainId(s.getDomain().getId());
                    dto.setCode(s.getCode());
                    dto.setDescription(s.getDescription());
                    dto.setAgeRangeMin(s.getAgeRangeMin());
                    dto.setAgeRangeMax(s.getAgeRangeMax());
                    dto.setLevel(s.getLevel() != null ? s.getLevel().name() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AetStatementDto> getAllStatements(UUID domainId, Integer ageMin, Integer ageMax) {
        List<AetStatement> statements;
        if (domainId != null) {
            statements = statementRepository.findByDomainId(domainId);
        } else {
            statements = statementRepository.findAll();
        }

        // Filter by age if provided
        if (ageMin != null || ageMax != null) {
            int age = ageMin != null ? ageMin : (ageMax != null ? ageMax : 10);
            statements = statements.stream()
                    .filter(s -> (s.getAgeRangeMin() == null || s.getAgeRangeMin() <= age) &&
                                (s.getAgeRangeMax() == null || s.getAgeRangeMax() >= age))
                    .collect(Collectors.toList());
        }

        return statements.stream()
                .map(s -> {
                    AetStatementDto dto = new AetStatementDto();
                    dto.setId(s.getId());
                    dto.setDomainId(s.getDomain().getId());
                    dto.setCode(s.getCode());
                    dto.setDescription(s.getDescription());
                    dto.setAgeRangeMin(s.getAgeRangeMin());
                    dto.setAgeRangeMax(s.getAgeRangeMax());
                    dto.setLevel(s.getLevel() != null ? s.getLevel().name() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

