package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pracownik")
@Data
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pracownika;
    private String imie;
    private String nazwisko;
    private String typ_pracownika;
    @Lob
    @Column(name = "zdjecie")
    private byte[] zdjecie;
    @Column(name = "data_Przyjazdu")
    private Date data_przyjazdu;
    @Column(name = "data_rozpoczecia_pracy")
    private Date data_rozpoczecia_pracy;
    @Column(name = "drugie_imie")
    private String drugieImie;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL)
    private List<WorkHours> workHoursUser;
}
