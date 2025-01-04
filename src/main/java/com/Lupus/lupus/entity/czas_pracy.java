    package com.Lupus.lupus.entity;

    import jakarta.persistence.*;
    import lombok.Data;
    import com.Lupus.lupus.Others.DurationConverter;

    import java.time.Duration;
    import java.time.LocalDate;
    import java.time.LocalTime;

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
        private LocalDate data_pracy;
        @Column(name = "start_pracy")
        private LocalTime start_pracy;
        @Column(name = "stop_pracy")
        private LocalTime stop_pracy;
        @Convert(converter = DurationConverter.class)
        @Column(name = "czas_przerwy")
        private Duration czas_przerwy;

    }