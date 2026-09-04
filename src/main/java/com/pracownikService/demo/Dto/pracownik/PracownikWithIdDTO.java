package com.pracownikService.demo.Dto.pracownik;

import com.pracownikService.demo.entity.Role;
import com.pracownikService.demo.entity.TypPracownika;

import java.time.LocalDate;

public interface PracownikWithIdDTO {
     Long getId();
     String getNazwa();
     int getWiek();
     TypPracownika getTyp_pracownika();
     Role getRole();
     String getZdjecie(); // link do zdjecia na garage
     LocalDate getData_dolaczenia();
     LocalDate getData_rozpoczenia_pracy();
}
