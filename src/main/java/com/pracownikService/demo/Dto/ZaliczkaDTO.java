package com.pracownikService.demo.Dto;

import com.pracownikService.demo.entity.Pracownik;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZaliczkaDTO {
    Long idZaliczki;
    BigDecimal kwota;
    LocalDate dataZaliczki;
    Long idPracownik;
}
