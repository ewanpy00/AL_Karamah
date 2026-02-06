package com.school.aet.aet;

import com.school.aet.aet.dto.AetDomainDto;
import com.school.aet.aet.dto.AetStatementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/aet")
public class AetController {
    private final AetService aetService;
    
    public AetController(AetService aetService) {
        this.aetService = aetService;
    }

    @GetMapping("/domains")
    public ResponseEntity<List<AetDomainDto>> getAllDomains() {
        return ResponseEntity.ok(aetService.getAllDomains());
    }

    @GetMapping("/domains/{domainId}/statements")
    public ResponseEntity<List<AetStatementDto>> getStatementsByDomain(@PathVariable UUID domainId) {
        return ResponseEntity.ok(aetService.getStatementsByDomain(domainId));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<AetStatementDto>> getStatements(
            @RequestParam(required = false) UUID domainId,
            @RequestParam(required = false) Integer ageMin,
            @RequestParam(required = false) Integer ageMax) {
        return ResponseEntity.ok(aetService.getAllStatements(domainId, ageMin, ageMax));
    }
}

