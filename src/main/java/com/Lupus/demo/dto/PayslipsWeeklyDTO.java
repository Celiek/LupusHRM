package com.Lupus.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayslipsWeeklyDTO {
    private Long id_pracownika;
    private BigDecimal kwota;
    private Date data_wyplaty;
}
