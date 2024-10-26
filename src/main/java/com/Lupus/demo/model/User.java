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
    private String Nazwisko;
    private String Typ_pracownika;
    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;
    @Column(name = "data_Przyjazdu")
    private Date data_przyjazdu;
    @Column(name = "data_rozpoczecia_pracy")
    private LocalDate data_rozpoczecia_pracy;
    @Column(name = "drugie_imie")
    private String drugieImie;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL)
    private List<WorkHours> workHoursUser;
}
