package com.Lupus.lupus.Others;

import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String login;
    private String haslo;
}
