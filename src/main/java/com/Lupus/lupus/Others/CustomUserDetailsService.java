package com.Lupus.lupus.Others;

import com.Lupus.lupus.entity.pracownik;
import com.Lupus.lupus.repository.PracownikRepository;
import com.Lupus.lupus.service.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private final PracownikRepository pracownikRepository;

    public CustomUserDetailsService(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        pracownik pracownik = pracownikRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
        return new CustomUserDetails(pracownik);
    }
}
