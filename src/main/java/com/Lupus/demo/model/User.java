package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
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
    private String DrugieImie;
    private String Nazwisko;
    private String Typ_pracownika;
    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;
    @Column(name = "data_dolaczenia")
    private LocalDate Data_Dolaczenia;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL)
    private List<WorkHours> workHoursUser;
}
