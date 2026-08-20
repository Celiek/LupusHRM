package com.pracownikService.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Setter @Getter
@Table(name = "wyplaty")
@Entity
public class Wyplaty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_wyplaty")
    private Long idWyplaty;
    @Column(name="data_wyplaty")
    private LocalDate dataWyplaty;
    @Column(name="data_od")
    private LocalDate dataOd;
    @Column(name="data_do")
    private LocalDate dataDo;
    @Column(name="kwota_wyplaty")
    private BigDecimal kwotaWyplaty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pracownik_id", nullable = false)
    private Pracownik pracownik;
}
