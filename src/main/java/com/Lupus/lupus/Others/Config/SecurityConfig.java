package com.Lupus.lupus.Others.Config;

import com.Lupus.lupus.Others.filter.JwtFilterValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilterValidator filterValidator;

    public SecurityConfig(JwtFilterValidator filterValidator) {
        this.filterValidator = filterValidator;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of(
                                "https://lupus24.byst.re",
                                "http://localhost:5500",
                                "http://127.0.0.1:5500",
                                "http://localhost:80",
                                "https://127.0.0.1:80"
                        ));
                        config.setAllowCredentials(true);
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
                        config.setExposedHeaders(List.of("Authorization"));
                        config.setMaxAge(7200L);
                        return config;
                    }
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/ping").permitAll()
                        .requestMatchers("/admin-dashboard").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/listall").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/addPracownik").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/deleteByID").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/updatePracownik").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/findCzasPracyByDate").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/deleteByNameAndSurname").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/sumGodzinyPracy").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/insertweeklyPaychecks").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/insertWeeklyPaychek").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/getWeeklyPaycheks").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/getWeeklyPaychek").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/getMonthlyPaycheks").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/pierwszyStartDzisiaj").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/sumaPrzerwDzisiaj").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/api/pracownik/czasPracyToday").hasAnyAuthority("ROLE_ADMIN", "ROLE_ADAS")
                        .requestMatchers("/nowiDzis").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/getWeeklyPaycheksForEmployee").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/stopPracy").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/wszystkie").hasAnyAuthority("ADMIN", "ADAS")
                        .requestMatchers("/api/urlopy/**").hasAnyAuthority("ADMIN","ROLE_ADMIN", "ADAS")
                        .requestMatchers("/api/pracownik/godzinyPracy").hasAnyAuthority("ADMIN","ROLE_ADMIN", "ADAS")
                        .requestMatchers("/api/czasPracy/dniPracyZakres").hasAnyAuthority("ADMIN","ROLE_ADMIN", "ADAS")
                        .requestMatchers("/api/czasPracy/**").hasAnyAuthority("ADMIN","ROLE_ADMIN", "ADAS")
                        .requestMatchers("/api/uwagi/**").hasAnyAuthority("ADMIN","ROLE_ADMIN", "ADAS")
                        .requestMatchers("/api/czasPracy/**").hasAnyAuthority("ADMIN", "ADAS")
                        //.requestMatchers("/api/pracownik/listallWithHours").permitAll()
                        .requestMatchers("/pracownik").hasAuthority("FIZYCZNY")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filterValidator, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
