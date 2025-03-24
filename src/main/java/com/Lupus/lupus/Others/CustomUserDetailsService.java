package com.Lupus.lupus.Others;

import com.Lupus.lupus.entity.Pracownik;
import com.Lupus.lupus.repository.PracownikRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final PracownikRepository pracownikRepository;

    public CustomUserDetailsService(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Szukam użytkownika: " + username);

        Pracownik pracownik = pracownikRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
        System.out.println("Znaleziono uzytkownika " + pracownikRepository.findUserByName(username) + " z rola: " + pracownik.getTyp_pracownika());
        return User.withUsername(pracownik.getLogin())
                .password(pracownik.getHaslo())
                .authorities(String.valueOf(pracownik.getTyp_pracownika()))
                .build();
    }
}
