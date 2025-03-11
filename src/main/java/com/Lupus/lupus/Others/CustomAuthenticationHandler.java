package com.Lupus.lupus.Others;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/dashboard";

        for(GrantedAuthority authority: authorities) {
            String role = authority.getAuthority();
            if(role.equals("ADMIN")) {
                redirectUrl = "/admin-dashboard";
                break;
            } else if(role.equals("ADAS")) {
                redirectUrl = "/admin-dashboard";
                break;
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
