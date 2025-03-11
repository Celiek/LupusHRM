package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.PracownikRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

import com.Lupus.lupus.entity.pracownik;

import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final pracownik pracownik;

    public CustomUserDetails(pracownik pracownik) {
        this.pracownik = pracownik;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(pracownik.getTyp_pracownika().name()));
    }

    @Override
    public String getPassword() {
        return pracownik.getHaslo();
    }

    @Override
    public String getUsername() {
        return pracownik.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
