package com.Lupus.lupus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Entity
@Table(name = "uwagi_pracownikow")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class uwagiPracownikow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUwagi;

    @Column(nullable = false)
    private Long idPracownika;

    private LocalDate dataDodania = LocalDate.now();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String trescUwagi;

    private String autorUwagi;
}