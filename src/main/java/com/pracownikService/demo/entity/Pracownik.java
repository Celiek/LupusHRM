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
    @Enumerated(EnumType.STRING)
    private TypPracownika typPracownika;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    private String zdjecie; // link do zdjecia na garage
    private LocalDate dataDolaczenia;
    private LocalDate dataRozpoczeciaPracy;
}
