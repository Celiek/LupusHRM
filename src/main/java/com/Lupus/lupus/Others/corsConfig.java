package com.Lupus.lupus.Others;

import com.Lupus.lupus.repository.PracownikRepository;
import com.Lupus.lupus.Others.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class corsConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationHandler successHandler = new CustomAuthenticationHandler();
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Włączenie CORS
                .csrf(csrf -> csrf.disable()) // Wyłączenie CSRF dla testów (opcjonalne)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/pracownik/**").hasAnyAuthority("PRACOWNIK", "ADMIN", "ADAS")
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/login") // Punkt logowania
                        .successHandler((request, response, authentication) ->
                                response.getWriter().write("Zalogowano pomyślnie")
                        )
                        .failureHandler((request, response, exception) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Błędne dane logowania")
                        )
                )
                .logout(logout -> logout.logoutUrl("/api/auth/logout").logoutSuccessUrl("/"));


        return http.build();
    }

    //do ustawienia
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000")); // Dostosuj do swojego frontendu
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // Możesz ustawić konkretne domeny zamiast "*"
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(PracownikRepository pracownikRepository) {
        return new CustomUserDetailsService(pracownikRepository);
    }
}