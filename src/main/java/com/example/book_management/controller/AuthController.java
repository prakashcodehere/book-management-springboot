package com.example.book_management.controller;

import com.example.book_management.config.jwt.JwtUtil;
import com.example.book_management.dto.AuthReqDTO;
import com.example.book_management.dto.AuthResDTO;
import com.example.book_management.entity.User;
import com.example.book_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResDTO login(@RequestBody AuthReqDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRoles().stream()
                        .map(r -> "ROLE_" + r.getName())
                        .collect(Collectors.toList())
        );

        return new AuthResDTO(token);
    }
}
