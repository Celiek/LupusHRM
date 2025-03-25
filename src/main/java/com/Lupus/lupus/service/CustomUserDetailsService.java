package com.Lupus.lupus.service;

import com.Lupus.lupus.entity.Pracownik;
import com.Lupus.lupus.repository.PracownikRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PracownikRepository pracownikRepository;

    public CustomUserDetailsService(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pracownik pracownik = pracownikRepository.findByLogin(username)
                .orElseThrow( () -> new UsernameNotFoundException("Nie znaleziono uztkownika z loginem: " + username));
        String role = pracownik.getTyp_pracownika().name();
        System.out.println("Rola uzytkownika" + role);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new User(pracownik.getLogin(), pracownik.getHaslo(), authorities);
    }
}
