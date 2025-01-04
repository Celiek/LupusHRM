package com.Lupus.lupus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "wyplata_miesieczna")
public class wyplataMiesieczna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wyplata_miesieczna")
    private Long id_wyplata_miesieczna;
    @Column(name = "kwota_miesieczna")
    private Double kwota_miesieczna;
    @Column(name = "suma_zaliczek")
    private Double suma_zaliczek;
    @Column(name = "data_wyplaty_miesiecznej")
    private Date data_wyplaty_miesiecznej;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pracownika",referencedColumnName = "id_pracownika")
    private pracownik pracownik;

}
