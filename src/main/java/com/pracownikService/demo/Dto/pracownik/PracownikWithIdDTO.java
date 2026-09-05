package com.pracownikService.demo.Dto.pracownik;

import com.pracownikService.demo.entity.Role;
import com.pracownikService.demo.entity.TypPracownika;

import java.time.LocalDate;

public interface PracownikWithIdDTO {
     Long getIdPracownik();
     String getNazwa();
     int getWiek();
     TypPracownika getTypPracownika();
     Role getRole();
     String getZdjecie(); // link do zdjecia na garage
     LocalDate getDataDolaczenia();
     LocalDate getDataRozpoczeciaPracy();
}
