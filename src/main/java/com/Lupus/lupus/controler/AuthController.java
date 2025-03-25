package com.Lupus.lupus.controler;

import com.Lupus.lupus.Others.filter.JwtTokenGenerator;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator tokenGenerator;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        System.out.println("Dane z frontu: " + authRequest.getUsername()+ " haslo " + authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String token = tokenGenerator.generateToken(authentication.getName());
        return ResponseEntity.ok(token);
    }

}

@Data
class AuthRequest {
    private String username;
    private String password;
}

