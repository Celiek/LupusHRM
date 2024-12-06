package com.LUPUS.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WyplataTygodniowaDTO {
    private Long idPracownika;
    private String imie;
    private String nazwisko;
    private Double kwotaTygodniowa;
    private Double zaliczkaTygodniowa;
    private Date dataWyplatyTygodniowej;
}
