package com.LUPUS.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class GodzinyPracyDTO {
    private Long idPracownika;
    private LocalDate data_pracy;
    private LocalTime start_pracy;
    private LocalTime stop_pracy;
    private Duration czas_przerw;
}
