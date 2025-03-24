package com.Lupus.lupus.Others.filter;

import com.Lupus.lupus.Others.ApplicationConstants;
import com.Lupus.lupus.entity.Pracownik;
import com.Lupus.lupus.repository.PracownikRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

//    @Autowired
    private final PracownikRepository pracownikRepository;

    public JWTTokenGeneratorFilter(PracownikRepository pracownikRepository){
        this.pracownikRepository = pracownikRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (request.getServletPath().equals("/api/auth/login")) {
            filterChain.doFilter(request, response); // Pomijamy JWT dla logowania
            System.out.println("Przechodze przez filtr");
            return;
        }

        if(null != authentication && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<Pracownik> optionalPracownik = pracownikRepository.findByLogin(username);

            Environment env = getEnvironment();

                    Pracownik pracownik = optionalPracownik.get();
                    String typPracownika = String.valueOf(pracownik.getTyp_pracownika());

                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + typPracownika));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            authentication.getPrincipal(),authentication.getCredentials(),authorities
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);


        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
