package com.Lupus.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CzasPracyDTO {
    private Long id_pracownik;
    private LocalDate data_pracy;
    private LocalTime start_pracy;
    private LocalTime stop_pracy;
    private Duration czas_przerwy;
    private String Error;


    public String getErrorMessage() {
        return Error;
    }

    public void setErrorMessage(String errorMessage) {
        this.Error = errorMessage;
    }

}
