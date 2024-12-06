package com.LUPUS.lupus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "czas_pracy")
public class czas_pracy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pracy")
    private Long id_pracy;
    @Column(name = "id_pracownik")
    private Long id_pracownik;
    @Column(name = "data_pracy")
    private Date data_pracy;
    @Column(name = "start_pracy")
    private Date start_pracy;
    @Column(name = "stop_pracy")
    private Date stop_pracy;
    @Column(name = "czas_przerw")
    private Date czas_przerw;

}