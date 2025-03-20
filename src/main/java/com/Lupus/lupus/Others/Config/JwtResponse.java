package com.Lupus.lupus.Others.Config;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
