package com.school.aet.student;

import com.school.aet.student.dto.StudentDetailDto;
import com.school.aet.student.dto.StudentSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentSummaryDto>> getStudents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer ageMin,
            @RequestParam(required = false) Integer ageMax) {
        return ResponseEntity.ok(studentService.getStudents(search, ageMin, ageMax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailDto> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }
}

