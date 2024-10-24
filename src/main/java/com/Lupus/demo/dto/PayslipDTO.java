package com.Lupus.demo.dto;
import com.Lupus.demo.model.User;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayslipDTO implements Serializable{
    private User user;
    private BigDecimal zaliczki;
    private BigDecimal wyplatyTygodniowe;
    private BigDecimal wyplataMiesieczna;
    private Date data_wyplaty;
}
