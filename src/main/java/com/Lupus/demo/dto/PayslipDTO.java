package com.Lupus.demo.dto;
import com.Lupus.demo.model.Worker;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayslipDTO implements Serializable{
    private Worker worker;
    private BigDecimal zaliczki;
    private BigDecimal wyplatyTygodniowe;
    private BigDecimal wyplataMiesieczna;
    private Date data_wyplaty;
}
