package com.pracownikService.demo.Dto.pracownik;

import com.pracownikService.demo.entity.TypPracownika;
import com.pracownikService.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// DTO domenowe
// tylko do reprezentacji pracownika w systemie
// zawiera link w bazie danych do RustFs (s3 comaptible fileStorage)
public class PracownikDTO { ;
    private String nazwa;
    private int wiek;
    private TypPracownika typ_pracownika;
    private Role role;
    private String zdjecie; // link do zdjecia na garage
    private LocalDate data_dolaczenia;
    private LocalDate data_rozpoczenia_pracy;
}
