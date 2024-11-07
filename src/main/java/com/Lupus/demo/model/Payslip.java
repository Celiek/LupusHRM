package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wyplaty")
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pracownika", nullable = false)
    private Worker worker;

    @Column(name = "zaliczki", precision = 10, scale = 2)
    private BigDecimal zaliczki;

    @Column(name = "wyplaty_tygodniowe", precision = 10, scale = 2)
    private BigDecimal wyplatyTygodniowe;

    @Column(name = "wyplata_miesieczna", precision = 10, scale = 2)
    private BigDecimal wyplataMiesieczna;

    @Column(name = "data_wyplaty")
    private Date data_wyplaty;

}
