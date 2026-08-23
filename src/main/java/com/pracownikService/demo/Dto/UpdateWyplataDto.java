package com.pracownikService.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWyplataDto {
    private LocalDate dataWyplaty;
    private LocalDate dataOd;
    private LocalDate dataDo;
    private BigDecimal kwotaWyplaty;
}
