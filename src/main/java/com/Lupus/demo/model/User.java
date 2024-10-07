package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pracownik")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_pracownika;
    private String Imie;
    private String Nazwisko;
    private String Typ_pracownika;
    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL)
    private List<WorkHours> workHoursUser;
}
