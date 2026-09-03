package com.pracownikService.demo.Dto.pracownik;

import com.pracownikService.demo.entity.Role;
import com.pracownikService.demo.entity.TypPracownika;

import java.time.LocalDate;

public class PracownikWithIdDTO {
    private Long pracownikId;
    private String nazwa;
    private int wiek;
    private TypPracownika typ_pracownika;
    private Role role;
    private String zdjecie; // link do zdjecia na garage
    private LocalDate data_dolaczenia;
    private LocalDate data_rozpoczenia_pracy;
}
