package com.LUPUS.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class CzasPracyDTO {
    //private Long id_pracy;
    private Long id_pracownik;
    private LocalDate data_pracy;
    private LocalDateTime start_pracy;
    private LocalDateTime stop_pracy;
    private Duration czas_przerw;
    private String Error;


    public String getErrorMessage() {
        return Error;
    }

    public void setErrorMessage(String errorMessage) {
        this.Error = errorMessage;
    }

}
