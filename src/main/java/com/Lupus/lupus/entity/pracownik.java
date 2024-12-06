package com.LUPUS.lupus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pracownik")
public class pracownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pracownika")
    private Long id_pracownika;
    @Column(name = "imie")
    private String imie;
    @Column(name = "drugie_imie")
    private String drugie_imie;
    @Column(name="nazwisko")
    private String nazwisko;
    @Column(name = "typ_pracownika")
    private String typ_pracownika;
    @Column(name = "zdjecie")
    private byte[] zdjecie;
    @Column(name = "data_dolaczenia")
    private Date data_dolaczenia;
    @Column(name = "data_rozpoczecia_pracy")
    private Date data_rozpoczecie_pracy;
    @Column(name = "login")
    private String login;
    @Column(name = "haslo")
    private String haslo;
//    @OneToMany(mappedBy = "pracownik",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<wyplataMiesieczna> wyplataMiesieczna;
//    @OneToMany(mappedBy = "pracownik",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<wyplataTygodniowa> wyplataTygodniowa;

}