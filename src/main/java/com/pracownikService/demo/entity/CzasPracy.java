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
    @Column(name = "id_czas_pracy")
    private Long id_czasPracy;
    @Column(name = "data_pracy", nullable = false)
    private LocalDate dataPracy;
    @Column(name = "start_pracy", nullable = false)
    private LocalTime startPracy;
    @Column(name = "stop_pracy")
    private LocalTime stopPracy;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pracownik_id", nullable = false)
    private Pracownik pracownik;
}
