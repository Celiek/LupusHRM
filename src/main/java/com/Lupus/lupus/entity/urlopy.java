package com.Lupus.lupus.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="urlopy")
public class urlopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pracownika", nullable = false)
    private Pracownik pracownik;

    @Column(name = "data_od", nullable = false)
    private LocalDate dataOd;

    @Column(name = "data_do", nullable = false)
    private LocalDate dataDo;

    @Column(name = "typ_urlopu")
    private String typUrlopu;

    @Column(name = "powod")
    private String powod;
}
