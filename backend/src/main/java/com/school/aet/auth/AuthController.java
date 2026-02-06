package com.school.aet.auth;

import com.school.aet.auth.dto.LoginRequest;
import com.school.aet.auth.dto.LoginResponse;
import com.school.aet.user.User;
import com.school.aet.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    public AuthController(AuthService authService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("=== AuthController.login called ===");
        System.out.println("Email: " + request.getEmail());
        LoginResponse response = authService.login(request);
        System.out.println("=== Login successful ===");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse.UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoginResponse.UserDto userDto = new LoginResponse.UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return ResponseEntity.ok(userDto);
    }
}

