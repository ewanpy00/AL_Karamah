package com.school.aet.auth;

import com.school.aet.auth.dto.LoginRequest;
import com.school.aet.auth.dto.LoginResponse;
import com.school.aet.user.Role;
import com.school.aet.user.User;
import com.school.aet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        logger.debug("Attempting login for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed: user not found for email: {}", request.getEmail());
                    return new RuntimeException("Invalid email or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            logger.warn("Login failed: invalid password for email: {}", request.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.getActive()) {
            logger.warn("Login failed: inactive account for email: {}", request.getEmail());
            throw new RuntimeException("User account is inactive");
        }

        logger.info("Login successful for user: {} ({})", user.getEmail(), user.getRole());
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        LoginResponse.UserDto userDto = new LoginResponse.UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        
        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setUser(userDto);
        return response;
    }

    public User getCurrentUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

