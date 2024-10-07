package com.Lupus.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class User {

    private Long Id_pracownika;
    private String Imie;
    private String Nazwisko;
    private String Typ_pracownika;
    @Lob
    private byte[] zdjecie;
}
