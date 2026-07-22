package com.pracownikService.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name="czas_pracy")
public class CzasPracy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_czasPracy;
    private Long id_pracownik;
    private LocalDate data_pracy;
    private LocalTime stop_pracy;
}
