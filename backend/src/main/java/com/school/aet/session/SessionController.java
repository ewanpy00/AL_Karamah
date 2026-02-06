package com.school.aet.session;

import com.school.aet.session.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;
    
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<CalendarSessionDto>> getCalendarSessions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) UUID ownerId) {
        return ResponseEntity.ok(sessionService.getCalendarSessions(from, to, groupId, ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDetailDto> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody CreateSessionRequest request) {
        // In real implementation, get ownerId from security context
        UUID ownerId = UUID.randomUUID(); // Placeholder
        return ResponseEntity.ok(sessionService.createSession(request, ownerId));
    }

    @PostMapping("/{sessionId}/resources/suggestions")
    public ResponseEntity<ResourceSuggestionResponse> suggestResources(
            @PathVariable UUID sessionId,
            @RequestParam(defaultValue = "10") int maxResults) {
        return ResponseEntity.ok(sessionService.suggestResourcesForSession(sessionId, maxResults));
    }
}

