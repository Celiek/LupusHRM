package com.Lupus.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class WyplataRequest {
    private Long idPracownika;
    private Double kwota;
    private Double zaliczka;
    private Date data_wyplaty;
}
