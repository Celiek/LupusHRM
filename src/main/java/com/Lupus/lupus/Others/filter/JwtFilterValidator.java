package com.Lupus.lupus.Others.filter;

import com.Lupus.lupus.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilterValidator extends OncePerRequestFilter {

    private final JwtTokenGenerator tokenGenerator;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilterValidator(JwtTokenGenerator tokenGenerator, CustomUserDetailsService userDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println("Naglowek authorization " + token);
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            Jws<Claims> claimsJws = tokenGenerator.parseToken(jwt); // Nowa metoda do parsowania tokena
            Claims claims = claimsJws.getBody();

            String username = claims.getSubject();
            String rolesString = claims.get("roles", String.class);
            System.out.println("Znalezione role w JWT: " + jwt);

            if (username != null && rolesString != null) {
                List<GrantedAuthority> authorities = Arrays.stream(rolesString.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        System.out.println("Sprawdzam shouldNotFilter dla ścieżki: " + path);
        return path.equals("/api/auth/login");
    }


}
