package com.school.aet.group;

import com.school.aet.group.dto.*;
import com.school.aet.student.Student;
import com.school.aet.student.StudentAetProfile;
import com.school.aet.student.StudentAetProfileRepository;
import com.school.aet.student.StudentRepository;
import com.school.aet.student.dto.StudentSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final StudentRepository studentRepository;
    private final StudentAetProfileRepository studentAetProfileRepository;
    
    public GroupService(GroupRepository groupRepository, 
                        GroupStudentRepository groupStudentRepository,
                        StudentRepository studentRepository,
                        StudentAetProfileRepository studentAetProfileRepository) {
        this.groupRepository = groupRepository;
        this.groupStudentRepository = groupStudentRepository;
        this.studentRepository = studentRepository;
        this.studentAetProfileRepository = studentAetProfileRepository;
    }

    public List<GroupSummaryDto> getAllGroups(Boolean active, UUID focusDomainId, Integer ageMin, Integer ageMax) {
        List<Group> groups = groupRepository.findByFilters(active, focusDomainId, ageMin, ageMax);
        return groups.stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    public GroupDetailDto getGroupById(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<GroupStudent> groupStudents = groupStudentRepository.findByGroupId(groupId);
        List<GroupDetailDto.StudentInGroupDto> students = new ArrayList<>();
        for (GroupStudent gs : groupStudents) {
            Student s = gs.getStudent();
            List<String> flags = calculateCompatibilityFlags(s, group);
            GroupDetailDto.StudentInGroupDto dto = new GroupDetailDto.StudentInGroupDto();
            dto.setId(s.getId());
            dto.setName(s.getFirstName() + " " + s.getLastName());
            dto.setAge(s.getAge());
            dto.setCompatibilityFlags(flags);
            students.add(dto);
        }

        GroupDetailDto.CompatibilitySummaryDto compatibility = calculateCompatibility(groupId);

        GroupDetailDto dto = new GroupDetailDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setAgeRangeMin(group.getAgeRangeMin());
        dto.setAgeRangeMax(group.getAgeRangeMax());
        dto.setFocusDomainId(group.getFocusDomain().getId());
        dto.setFocusDomainName(group.getFocusDomain().getName());
        dto.setDescription(group.getDescription());
        dto.setStudents(students);
        dto.setCompatibilitySummary(compatibility);
        return dto;
    }

    @Transactional
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public AddStudentsToGroupResponse addStudentsToGroup(UUID groupId, List<UUID> studentIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<StudentSummaryDto> addedStudents = new ArrayList<>();
        List<CompatibilityIssueDto> warnings = new ArrayList<>();

        for (UUID studentId : studentIds) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

            // Check if already in group
            if (groupStudentRepository.findActiveByGroupAndStudent(groupId, studentId).isPresent()) {
                continue;
            }

            // Check compatibility
            List<CompatibilityIssueDto> studentWarnings = checkStudentCompatibility(student, group);
            warnings.addAll(studentWarnings);

            // Add to group
            GroupStudent groupStudent = new GroupStudent();
            groupStudent.setGroup(group);
            groupStudent.setStudent(student);
            groupStudent.setJoinedAt(Instant.now());
            groupStudent.setRole(GroupStudentRole.CORE);
            groupStudentRepository.save(groupStudent);

            StudentSummaryDto studentDto = new StudentSummaryDto();
            studentDto.setId(student.getId());
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            studentDto.setAge(student.getAge());
            studentDto.setKeyNotes(student.getPrimaryNeedsNotes());
            addedStudents.add(studentDto);
        }

        AddStudentsToGroupResponse response = new AddStudentsToGroupResponse();
        response.setAddedStudents(addedStudents);
        response.setCompatibilityWarnings(warnings);
        return response;
    }

    @Transactional
    public void removeStudentFromGroup(UUID groupId, UUID studentId) {
        GroupStudent groupStudent = groupStudentRepository.findActiveByGroupAndStudent(groupId, studentId)
                .orElseThrow(() -> new RuntimeException("Student not in group"));
        groupStudent.setLeaveAt(Instant.now());
        groupStudentRepository.save(groupStudent);
    }

    public GroupDetailDto.CompatibilitySummaryDto calculateCompatibility(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<GroupStudent> groupStudents = groupStudentRepository.findByGroupId(groupId);
        List<CompatibilityIssueDto> allIssues = new ArrayList<>();
        int totalChecks = 0;
        int passedChecks = 0;

        for (GroupStudent gs : groupStudents) {
            Student student = gs.getStudent();
            List<CompatibilityIssueDto> issues = checkStudentCompatibility(student, group);
            allIssues.addAll(issues);
            totalChecks += 3; // age, sensory, communication
            if (issues.isEmpty()) {
                passedChecks += 3;
            } else {
                passedChecks += (3 - issues.size());
            }
        }

        int score = totalChecks > 0 ? (passedChecks * 100 / totalChecks) : 100;

        GroupDetailDto.CompatibilitySummaryDto summary = new GroupDetailDto.CompatibilitySummaryDto();
        summary.setScore(score);
        summary.setIssues(allIssues);
        return summary;
    }

    private List<CompatibilityIssueDto> checkStudentCompatibility(Student student, Group group) {
        List<CompatibilityIssueDto> issues = new ArrayList<>();
        int age = student.getAge();

        // Age check
        if (age < group.getAgeRangeMin() || age > group.getAgeRangeMax()) {
            CompatibilityIssueDto issue = new CompatibilityIssueDto();
            issue.setType("AGE_MISMATCH");
            issue.setMessage(String.format("%s (age %d) is outside group age range (%d-%d)",
                    student.getFirstName() + " " + student.getLastName(),
                    age, group.getAgeRangeMin(), group.getAgeRangeMax()));
            issue.setSeverity("WARNING");
            issues.add(issue);
        }

        // Simplified sensory/communication checks (can be enhanced)
        if (student.getSensoryNotes() != null && student.getSensoryNotes().toLowerCase().contains("high")) {
            CompatibilityIssueDto issue = new CompatibilityIssueDto();
            issue.setType("SENSORY_CONFLICT");
            issue.setMessage("High sensory needs - review group composition");
            issue.setSeverity("INFO");
            issues.add(issue);
        }

        return issues;
    }

    private List<String> calculateCompatibilityFlags(Student student, Group group) {
        List<String> flags = new ArrayList<>();
        int age = student.getAge();

        if (age < group.getAgeRangeMin() || age > group.getAgeRangeMax()) {
            flags.add("Age mismatch");
        }

        if (student.getSensoryNotes() != null && student.getSensoryNotes().toLowerCase().contains("high")) {
            flags.add("High sensory");
        }

        return flags;
    }

    private GroupSummaryDto toSummaryDto(Group group) {
        int studentCount = groupStudentRepository.findByGroupId(group.getId()).size();
        GroupDetailDto.CompatibilitySummaryDto compatibility = calculateCompatibility(group.getId());

        GroupSummaryDto dto = new GroupSummaryDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setAgeRangeMin(group.getAgeRangeMin());
        dto.setAgeRangeMax(group.getAgeRangeMax());
        dto.setFocusDomainName(group.getFocusDomain().getName());
        dto.setStudentCount(studentCount);
        dto.setCompatibilityScore(compatibility.getScore());
        return dto;
    }
}

