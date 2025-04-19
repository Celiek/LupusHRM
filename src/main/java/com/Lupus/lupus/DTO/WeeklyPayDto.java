package com.Lupus.lupus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeeklyPayDto {
    private String imie;
    private String nazwisko;
    private BigDecimal kwotaTygodniowa;
    private BigDecimal zaliczkaTygodniowa;
    private LocalDate dataWyplatyTygodniowej;
}
