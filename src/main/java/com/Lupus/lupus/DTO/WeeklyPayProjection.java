package com.Lupus.lupus.DTO;


import java.math.BigDecimal;
import java.time.LocalDate;


public interface WeeklyPayProjection {
    String getImie();
    String getNazwisko();
    BigDecimal getKwotaTygodniowa();
    BigDecimal getZaliczkaTygodniowa();
    LocalDate getDataWyplatyTygodniowej();
}
