package com.Lupus.lupus.Others;

import com.Lupus.lupus.entity.pracownik;
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
        pracownik pracownik = pracownikRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
        return User.withUsername(pracownik.getLogin())
                .password(pracownik.getHaslo())
                .roles(String.valueOf(pracownik.getTyp_pracownika()))
                .build();
    }
}
