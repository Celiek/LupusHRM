package com.Lupus.lupus.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import com.Lupus.lupus.entity.Pracownik;

public class CustomUserDetails implements UserDetails {

    private final Pracownik pracownik;

    public CustomUserDetails(Pracownik pracownik) {
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
