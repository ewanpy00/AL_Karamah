package com.school.aet.config;

import com.school.aet.student.Student;
import com.school.aet.student.StudentRepository;
import com.school.aet.user.Role;
import com.school.aet.user.User;
import com.school.aet.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void init() {
        // Create test users if they don't exist
        if (userRepository.count() == 0) {
            // Admin user
            User admin = new User();
            admin.setFullName("Admin User");
            admin.setEmail("admin@school.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);

            // Teacher user
            User teacher = new User();
            teacher.setFullName("John Teacher");
            teacher.setEmail("teacher@school.com");
            teacher.setPasswordHash(passwordEncoder.encode("teacher123"));
            teacher.setRole(Role.TEACHER);
            teacher.setActive(true);
            userRepository.save(teacher);

            // Therapist user
            User therapist = new User();
            therapist.setFullName("Jane Therapist");
            therapist.setEmail("therapist@school.com");
            therapist.setPasswordHash(passwordEncoder.encode("therapist123"));
            therapist.setRole(Role.THERAPIST);
            therapist.setActive(true);
            userRepository.save(therapist);

            System.out.println("========================================");
            System.out.println("Test users created:");
            System.out.println("Admin: admin@school.com / admin123");
            System.out.println("Teacher: teacher@school.com / teacher123");
            System.out.println("Therapist: therapist@school.com / therapist123");
            System.out.println("========================================");
        }
        
        // Create test students if they don't exist
        if (studentRepository.count() == 0) {
            // Student 1
            Student student1 = new Student();
            student1.setFirstName("Ahmed");
            student1.setLastName("Al-Mansouri");
            student1.setDateOfBirth(LocalDate.of(2015, 3, 15));
            student1.setPrimaryNeedsNotes("Strong interest in numbers and patterns. Enjoys structured activities.");
            student1.setCommunicationNotes("Uses PECS system effectively. Responds well to visual supports.");
            student1.setSensoryNotes("Sensitive to loud noises. Prefers quiet environments.");
            student1.setGeneralNotes("Very focused when working with numbers. Needs clear transitions between activities.");
            studentRepository.save(student1);
            
            // Student 2
            Student student2 = new Student();
            student2.setFirstName("Fatima");
            student2.setLastName("Hassan");
            student2.setDateOfBirth(LocalDate.of(2016, 7, 22));
            student2.setPrimaryNeedsNotes("Excellent social motivation. Enjoys group activities.");
            student2.setCommunicationNotes("Uses verbal communication with some echolalia. Working on conversation skills.");
            student2.setSensoryNotes("Seeks proprioceptive input. Benefits from movement breaks.");
            student2.setGeneralNotes("Very creative and enjoys art activities. Sometimes needs support with emotional regulation.");
            studentRepository.save(student2);
            
            // Student 3
            Student student3 = new Student();
            student3.setFirstName("Omar");
            student3.setLastName("Ibrahim");
            student3.setDateOfBirth(LocalDate.of(2014, 11, 8));
            student3.setPrimaryNeedsNotes("Strong visual learner. Benefits from visual schedules and supports.");
            student3.setCommunicationNotes("Uses AAC device. Working on expanding vocabulary.");
            student3.setSensoryNotes("Sensitive to textures. Prefers soft materials.");
            student3.setGeneralNotes("Very independent with daily routines. Enjoys helping others.");
            studentRepository.save(student3);
            
            System.out.println("========================================");
            System.out.println("Test students created: 3 students");
            System.out.println("========================================");
        }
    }
}

