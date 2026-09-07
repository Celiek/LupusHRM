package com.pracownikService.demo.Dto.pracownik;

import com.pracownikService.demo.entity.Role;
import com.pracownikService.demo.entity.TypPracownika;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PracownikUpdateDTO {
    private String nazwa;
    private Integer wiek;
    private TypPracownika typPracownika;
    private Role role;
    private String zdjecie;
    private LocalDate dataDolaczenia;
    private LocalDate dataRozpoczeciaPracy;
}
