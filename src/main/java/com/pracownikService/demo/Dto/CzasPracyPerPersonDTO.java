package com.pracownikService.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CzasPracyPerPersonDTO {
    private Long id;
    private Long idPracownik;
    private String nazwaPracownika;
    private LocalDate dataPracy;
    private LocalTime startPracy;
    private LocalTime stopPracy;
}
