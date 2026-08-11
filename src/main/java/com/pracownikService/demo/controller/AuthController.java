package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.LoginRequest;
import com.pracownikService.demo.config.security.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session){
        Authentication authentication = authService.authenticate(
                request.getLogin(),
                request.getPassword()
        );

        session.setAttribute("SPRING_SECURITY_CONTEXT",
                org.springframework.security.core.context
                        .SecurityContextHolder.getContext());
        return ResponseEntity.ok("Zalogowano pomyślnie");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication){
        return ResponseEntity.ok(authentication.getName());
    }
}
