package com.LUPUS.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WyplataMiesiecznaDTO {
    private Long idPracownika;
    private String imie;
    private String nazwisko;
    private byte[] zdjecie;
    private Double wyplataMiesieczna;
    private Double sumaZaliczek;
    private Double wyplataPoZaliczkach;
}
