package com.pracownikService.demo.config.security;

import com.pracownikService.demo.entity.User;
import com.pracownikService.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        User user = userRepo.findByLogin(login)
                            .orElseThrow(() ->
                                    new UsernameNotFoundException("Nie znaleziono użytkownika: " + login));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(
                        user.getRole()
                                .name()
                                .replace("ROLE_","")
                )
                .disabled(!user.isEnabled())
                .build();
    }
}
