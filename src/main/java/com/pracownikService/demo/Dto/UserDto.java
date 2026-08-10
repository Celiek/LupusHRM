package com.pracownikService.demo.Dto;

import com.pracownikService.demo.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDto {
    private String login;
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Long pracownikId;
}
