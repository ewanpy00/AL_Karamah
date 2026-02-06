package com.school.aet.resource;

import com.school.aet.resource.dto.ResourceSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceRepository resourceRepository;
    
    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @GetMapping
    public ResponseEntity<List<ResourceSummaryDto>> getResources(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID domainId,
            @RequestParam(required = false) UUID statementId,
            @RequestParam(required = false) ResourceType type,
            @RequestParam(required = false) Integer ageMin,
            @RequestParam(required = false) Integer ageMax) {
        
        List<Resource> resources = resourceRepository.findByFilters(search, domainId, statementId, type, ageMin, ageMax);
        
        List<ResourceSummaryDto> dtos = resources.stream()
                .map(r -> {
                    ResourceSummaryDto dto = new ResourceSummaryDto();
                    dto.setId(r.getId());
                    dto.setTitle(r.getTitle());
                    dto.setType(r.getType().name());
                    dto.setDescription(r.getDescription());
                    dto.setTags(r.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));
                    dto.setMappedStatementCodes(r.getMappedStatements().stream()
                            .map(s -> s.getCode())
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable UUID id) {
        return ResponseEntity.ok(resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found")));
    }
}

