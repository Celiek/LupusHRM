package com.pracownikService.demo.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticatioManager;

    public Authentication authenticate(String login, String password){
        return authenticatioManager.authenticate(new UsernamePasswordAuthenticationToken(login,password));
    }
}
