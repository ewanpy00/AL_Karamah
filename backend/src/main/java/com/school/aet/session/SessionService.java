package com.school.aet.session;

import com.school.aet.aet.AetStatement;
import com.school.aet.aet.AetStatementRepository;
import com.school.aet.group.Group;
import com.school.aet.group.GroupRepository;
import com.school.aet.group.GroupStudent;
import com.school.aet.group.GroupStudentRepository;
import com.school.aet.resource.Resource;
import com.school.aet.resource.ResourceRepository;
import com.school.aet.session.dto.*;
import com.school.aet.student.Student;
import com.school.aet.student.StudentAetProfile;
import com.school.aet.student.StudentAetProfileRepository;
import com.school.aet.user.User;
import com.school.aet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final GroupRepository groupRepository;
    private final AetStatementRepository aetStatementRepository;
    private final ResourceRepository resourceRepository;
    private final SessionResourceRepository sessionResourceRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final StudentAetProfileRepository studentAetProfileRepository;
    private final UserRepository userRepository;
    
    public SessionService(SessionRepository sessionRepository,
                         GroupRepository groupRepository,
                         AetStatementRepository aetStatementRepository,
                         ResourceRepository resourceRepository,
                         SessionResourceRepository sessionResourceRepository,
                         GroupStudentRepository groupStudentRepository,
                         StudentAetProfileRepository studentAetProfileRepository,
                         UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.groupRepository = groupRepository;
        this.aetStatementRepository = aetStatementRepository;
        this.resourceRepository = resourceRepository;
        this.sessionResourceRepository = sessionResourceRepository;
        this.groupStudentRepository = groupStudentRepository;
        this.studentAetProfileRepository = studentAetProfileRepository;
        this.userRepository = userRepository;
    }

    public List<CalendarSessionDto> getCalendarSessions(java.time.Instant from, java.time.Instant to, UUID groupId, UUID ownerId) {
        List<Session> sessions = sessionRepository.findCalendarSessions(from, to, groupId, ownerId);
        return sessions.stream()
                .map(this::toCalendarDto)
                .collect(Collectors.toList());
    }

    public SessionDetailDto getSessionById(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        List<SessionDetailDto.AetStatementDto> aetStatements = session.getAetFocusStatements().stream()
                .map(s -> {
                    SessionDetailDto.AetStatementDto dto = new SessionDetailDto.AetStatementDto();
                    dto.setId(s.getId());
                    dto.setCode(s.getCode());
                    dto.setDescription(s.getDescription());
                    dto.setDomainName(s.getDomain().getName());
                    return dto;
                })
                .collect(Collectors.toList());

        List<SessionResource> sessionResources = sessionResourceRepository.findBySessionId(sessionId);
        List<SessionDetailDto.ResourceDto> resources = sessionResources.stream()
                .map(sr -> {
                    SessionDetailDto.ResourceDto dto = new SessionDetailDto.ResourceDto();
                    dto.setId(sr.getResource().getId());
                    dto.setTitle(sr.getResource().getTitle());
                    dto.setType(sr.getResource().getType().name());
                    dto.setRole(sr.getRole().name());
                    return dto;
                })
                .collect(Collectors.toList());

        SessionDetailDto dto = new SessionDetailDto();
        dto.setId(session.getId());
        dto.setTitle(session.getTitle());
        dto.setGroupId(session.getGroup().getId());
        dto.setGroupName(session.getGroup().getName());
        dto.setStartTime(session.getStartTime().toString());
        dto.setEndTime(session.getEndTime().toString());
        dto.setLocation(session.getLocation());
        dto.setOwnerId(session.getOwner().getId());
        dto.setOwnerName(session.getOwner().getFullName());
        dto.setStatus(session.getStatus().name());
        dto.setNotes(session.getNotes());
        dto.setAetFocusStatements(aetStatements);
        dto.setSelectedResources(resources);
        return dto;
    }

    @Transactional
    public Session createSession(CreateSessionRequest request) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (request.getOwnerId() == null) {
            throw new RuntimeException("Owner ID is required");
        }

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Session session = new Session();
        session.setTitle(request.getTitle());
        session.setGroup(group);
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setLocation(request.getLocation());
        session.setNotes(request.getNotes());
        session.setStatus(SessionStatus.PLANNED);
        session.setOwner(owner);

        if (request.getAetStatementIds() != null && !request.getAetStatementIds().isEmpty()) {
            List<AetStatement> statements = aetStatementRepository.findAllById(request.getAetStatementIds());
            session.setAetFocusStatements(statements);
        }

        return sessionRepository.save(session);
    }

    @Transactional
    public void updateSession(UUID sessionId, UpdateSessionRequest request) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            session.setTitle(request.getTitle().trim());
        }
        if (request.getStartTime() != null) {
            session.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            session.setEndTime(request.getEndTime());
        }
        if (request.getLocation() != null && !request.getLocation().trim().isEmpty()) {
            session.setLocation(request.getLocation().trim());
        }
        if (request.getNotes() != null) {
            session.setNotes(request.getNotes());
        }

        if (session.getStartTime() != null && session.getEndTime() != null &&
                session.getEndTime().isBefore(session.getStartTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        sessionRepository.save(session);
    }

    @Transactional
    public ResourceSuggestionResponse suggestResourcesForSession(UUID sessionId, int maxResults) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Group group = session.getGroup();
        List<UUID> studentIds = groupStudentRepository.findActiveStudentIdsByGroupId(group.getId());

        // Get AET statements from session
        Set<UUID> sessionStatementIds = session.getAetFocusStatements().stream()
                .map(AetStatement::getId)
                .collect(Collectors.toSet());

        // Get student AET profiles
        Map<UUID, List<UUID>> studentStatementMap = new HashMap<>();
        for (UUID studentId : studentIds) {
            List<StudentAetProfile> profiles = studentAetProfileRepository.findByStudentId(studentId);
            List<UUID> statementIds = profiles.stream()
                    .map(p -> p.getStatement().getId())
                    .collect(Collectors.toList());
            studentStatementMap.put(studentId, statementIds);
        }

        // Calculate age range from students
        List<GroupStudent> groupStudents = groupStudentRepository.findByGroupId(group.getId());
        OptionalInt minAge = groupStudents.stream()
                .mapToInt(gs -> gs.getStudent().getAge())
                .min();
        OptionalInt maxAge = groupStudents.stream()
                .mapToInt(gs -> gs.getStudent().getAge())
                .max();

        // Find resources matching session statements
        List<Resource> allResources = resourceRepository.findAll();
        List<ResourceSuggestionDto> suggestions = allResources.stream()
                .map(resource -> {
                    int score = calculateResourceScore(resource, sessionStatementIds, 
                            minAge.orElse(6), maxAge.orElse(25));
                    if (score > 0) {
                        ResourceSuggestionDto dto = new ResourceSuggestionDto();
                        dto.setResourceId(resource.getId());
                        dto.setTitle(resource.getTitle());
                        dto.setType(resource.getType().name());
                        dto.setTags(resource.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));
                        dto.setScore(score);
                        dto.setReason(generateReason(resource, sessionStatementIds));
                        return dto;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ResourceSuggestionDto::getScore).reversed())
                .limit(maxResults)
                .collect(Collectors.toList());

        ResourceSuggestionResponse response = new ResourceSuggestionResponse();
        response.setSessionId(sessionId);
        response.setSuggestions(suggestions);
        return response;
    }

    private int calculateResourceScore(Resource resource, Set<UUID> sessionStatementIds, 
                                       int minAge, int maxAge) {
        int score = 0;

        // Check statement overlap
        Set<UUID> resourceStatementIds = resource.getMappedStatements().stream()
                .map(AetStatement::getId)
                .collect(Collectors.toSet());

        long matchingStatements = sessionStatementIds.stream()
                .filter(resourceStatementIds::contains)
                .count();
        score += matchingStatements * 30;

        // Check age range
        if (resource.getAgeRangeMin() != null && resource.getAgeRangeMax() != null) {
            if (minAge >= resource.getAgeRangeMin() && maxAge <= resource.getAgeRangeMax()) {
                score += 20;
            }
        }

        return score;
    }

    private String generateReason(Resource resource, Set<UUID> sessionStatementIds) {
        List<String> matchingCodes = resource.getMappedStatements().stream()
                .filter(s -> sessionStatementIds.contains(s.getId()))
                .map(AetStatement::getCode)
                .collect(Collectors.toList());

        if (!matchingCodes.isEmpty()) {
            return "Matches " + String.join(", ", matchingCodes);
        }
        return "General match";
    }

    private CalendarSessionDto toCalendarDto(Session session) {
        List<String> aetTags = session.getAetFocusStatements().stream()
                .map(AetStatement::getCode)
                .collect(Collectors.toList());

        CalendarSessionDto dto = new CalendarSessionDto();
        dto.setId(session.getId());
        dto.setTitle(session.getTitle());
        dto.setGroupId(session.getGroup().getId());
        dto.setGroupName(session.getGroup().getName());
        dto.setStartTime(session.getStartTime().toString());
        dto.setEndTime(session.getEndTime().toString());
        if (session.getGroup().getFocusDomain() != null) {
            dto.setFocusDomainCode(session.getGroup().getFocusDomain().getCode());
            dto.setFocusDomainName(session.getGroup().getFocusDomain().getName());
        }
        dto.setStatus(session.getStatus().name());
        dto.setAetTags(aetTags);
        return dto;
    }
}
