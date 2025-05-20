package com.Lupus.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlopDTO {
    private Long id;
    private Long idPracownika;
    private LocalDate data_od;
    private LocalDate data_do;
    private String typ_urlopu;
    private String powod;
}
