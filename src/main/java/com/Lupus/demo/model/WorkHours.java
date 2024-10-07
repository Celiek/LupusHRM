package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "godziny_pracy")
public class WorkHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_pracownika", nullable = false)
    private Integer idPracownika;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pracownika",nullable = false)
    private User user;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "start_pracy", nullable = false)
    private LocalTime startPracy;

    @Column(name = "stop_pracy", nullable = false)
    private LocalTime stopPracy;

    @Column(name = "opis")
    private String opis;

}
