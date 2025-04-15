package com.Lupus.lupus.DTO;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Data
public class WyplataTygodniowaDTO {
    private Double kwota;
    private Double zaliczka;
    private Date data_wyplaty_tygodniowej;
}
