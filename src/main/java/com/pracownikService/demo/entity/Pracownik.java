package com.pracownikService.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="pracownik")
public class Pracownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pracownik")
    private Long idPracownik;
    private String nazwa;
    private int wiek;
    private TypPracownika typ_pracownika;
    private Uprawnienia uprawnienia;
    private String zdjecie; // link do zdjecia na garage
    private LocalDate data_dolaczenia;
    private LocalDate data_rozpoczecia_pracy;
}
