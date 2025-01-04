package com.Lupus.lupus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "wyplata_tygodniowa")
public class wyplataTygodniowa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wyplata_tygodniowa")
    private Long id_wyplata_tygodniowa;
    @Column(name = "kwota_tygodniowa")
    private Double kwota_tygodniowa;
    @Column(name = "kwota_zaliczki")
    private Double kwota_zaliczki;
    @Column(name = "data_wyplaty_tygodniowej")
    private Date data_wyplaty_tygodniowej;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_pracownika",referencedColumnName ="id_pracownika")
    private pracownik pracownik;
}
