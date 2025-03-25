package com.Lupus.lupus.Exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if(authorities.stream().anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN"))){
            response.sendRedirect("/admin-dashboard");
        }if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_FIZYCZNY"))){
            response.sendRedirect("/pracownik");
        } if(authorities.stream().anyMatch(a-> a.getAuthority().equals("ROLE_ADAS"))){
            response.sendRedirect("/admin-dashboard");
        } else {
            response.sendRedirect("/api/auth/login");
        }
    }
}
