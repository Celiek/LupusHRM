package com.Lupus.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PracownikDto {
    private Long idPracownika;
    private String imie;
    private String drugieImie;
    private String nazwisko;
    private String typPracownika;
    private String zdjecie; // Base64 zdjęcia lub null
    private LocalDate dataDolaczenia;
    private String login;
    private String haslo;
    private String krajPochodzenia;
    private String nrWhatsapp;
    private String email;
    private String nrKonta;
}
