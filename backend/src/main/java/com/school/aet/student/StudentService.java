package com.school.aet.student;

import com.school.aet.student.dto.StudentDetailDto;
import com.school.aet.student.dto.StudentSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentAetProfileRepository studentAetProfileRepository;
    private final StudentProgressRepository studentProgressRepository;
    
    public StudentService(StudentRepository studentRepository, StudentAetProfileRepository studentAetProfileRepository, StudentProgressRepository studentProgressRepository) {
        this.studentRepository = studentRepository;
        this.studentAetProfileRepository = studentAetProfileRepository;
        this.studentProgressRepository = studentProgressRepository;
    }

    public List<StudentSummaryDto> getStudents(String search, Integer ageMin, Integer ageMax) {
        List<Student> students = studentRepository.findByFilters(search, ageMin, ageMax);
        return students.stream()
                .map(s -> {
                    StudentSummaryDto dto = new StudentSummaryDto();
                    dto.setId(s.getId());
                    dto.setFirstName(s.getFirstName());
                    dto.setLastName(s.getLastName());
                    dto.setAge(s.getAge());
                    dto.setKeyNotes(s.getPrimaryNeedsNotes());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public StudentDetailDto getStudentById(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<StudentAetProfile> profiles = studentAetProfileRepository.findByStudentId(studentId);
        List<StudentDetailDto.AetProfileEntryDto> aetProfile = profiles.stream()
                .map(p -> {
                    StudentDetailDto.AetProfileEntryDto dto = new StudentDetailDto.AetProfileEntryDto();
                    dto.setDomainId(p.getStatement().getDomain().getId());
                    dto.setDomainName(p.getStatement().getDomain().getName());
                    dto.setStatementId(p.getStatement().getId());
                    dto.setStatementCode(p.getStatement().getCode());
                    dto.setStatementDescription(p.getStatement().getDescription());
                    dto.setCurrentLevel(p.getCurrentLevel() != null ? p.getCurrentLevel().name() : null);
                    dto.setLastUpdated(p.getLastUpdated().toString());
                    return dto;
                })
                .collect(Collectors.toList());

        StudentDetailDto dto = new StudentDetailDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setAge(student.getAge());
        dto.setPrimaryNeedsNotes(student.getPrimaryNeedsNotes());
        dto.setCommunicationNotes(student.getCommunicationNotes());
        dto.setSensoryNotes(student.getSensoryNotes());
        dto.setGeneralNotes(student.getGeneralNotes());
        dto.setAetProfile(aetProfile);
        return dto;
    }

    public List<com.school.aet.student.dto.StudentProgressDto> getStudentProgress(UUID studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentProgressRepository.findByStudentId(studentId).stream()
                .map(p -> {
                    com.school.aet.student.dto.StudentProgressDto dto = new com.school.aet.student.dto.StudentProgressDto();
                    dto.setId(p.getId());
                    dto.setItemKey(p.getItemKey());
                    dto.setStatus(p.getStatus().name());
                    dto.setUpdatedAt(p.getUpdatedAt().toString());
                    return dto;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public com.school.aet.student.dto.StudentProgressDto updateStudentProgress(UUID studentId, String itemKey, StudentProgressStatus status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentProgress progress = studentProgressRepository.findByStudentIdAndItemKey(studentId, itemKey)
                .orElseGet(() -> {
                    StudentProgress p = new StudentProgress();
                    p.setStudent(student);
                    p.setItemKey(itemKey);
                    return p;
                });

        progress.setStatus(status);
        progress.setUpdatedAt(java.time.Instant.now());
        StudentProgress saved = studentProgressRepository.save(progress);

        com.school.aet.student.dto.StudentProgressDto dto = new com.school.aet.student.dto.StudentProgressDto();
        dto.setId(saved.getId());
        dto.setItemKey(saved.getItemKey());
        dto.setStatus(saved.getStatus().name());
        dto.setUpdatedAt(saved.getUpdatedAt().toString());
        return dto;
    }

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(UUID studentId, Student student) {
        Student existing = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setDateOfBirth(student.getDateOfBirth());
        existing.setPrimaryNeedsNotes(student.getPrimaryNeedsNotes());
        existing.setCommunicationNotes(student.getCommunicationNotes());
        existing.setSensoryNotes(student.getSensoryNotes());
        existing.setGeneralNotes(student.getGeneralNotes());
        
        return studentRepository.save(existing);
    }
}
