package com.pracownikService.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Setter @Getter
@Table(name="zaliczki")
public class Zaliczki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_zaliczki")
    private Long idZaliczki;
    private BigDecimal kwota;
    @Column(name="data_zaliczki")
    private LocalDate dataZaliczki;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pracownik_id", nullable = false)
    private Pracownik pracownik;
}
