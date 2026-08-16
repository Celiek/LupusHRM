package com.pracownikService.demo.repo;

import com.Lupus.lupus.DTO.CzasPracyDTO;
import com.pracownikService.demo.entity.CzasPracy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CzasPracyRepository extends JpaRepository<CzasPracy, Long> {

    @Modifying
    @Transactional
    @Query(value = """
               UPDATE czas_pracy c
               SET c.start_pracy = CURRENT_TIMESTAMP
               WHERE c.pracownik.idPracownik = :id
            """,nativeQuery = true)
    void updateCzasPracyForPracownik(Long id);

    boolean existsByPracownikIdPracownikAndDataPracy(
            Long idPracownik,
            LocalDate dataPracy
    );

    Optional<CzasPracy> findByPracownikIdPracownikAndDataPracy(
            Long idPracownik,
            LocalDate dataPracy
    );

    List<CzasPracy> findAllByPracownik_IdPracownikInAndDataPracy(
            List<Long> idsPracownikow,
            LocalDate dataPracy
    );

    Optional<CzasPracy> findByPracownik_IdPracownikAndDataPracy(
            Long idPracownik,
            LocalDate dataPracy
    );

    @Query(
            value = """
            SELECT SUM(
                    FUNCTION('EXTRACT', 'EPOCH', (c.stopPracy - c.startPracy)) /3600
                ) FROM czas_pracy
                WHERE data_pracy BETWEEN :startPracy AND :stopPracy
            """,nativeQuery = true
    )
    Double sumGodzinyPracyBetweenDatesForPracownicy(LocalDate startPracy, LocalDate stopPracy);

    @Query(value = """
            Select COALESCE(
                SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy))/3600,0
            ) FROM czas_pracy c
            WHERE c.id:pracownika = :id 
                AND c.data_pracy BETWEEN :start AND :stop
            """,nativeQuery = true)
    Double sumGodiznyPracyBetweenDatesForPracownik(@Param("id")Long id,
                                                   @Param("start") LocalDate start,
                                                   @Param("stop") LocalDate stop);

    @Query(value= """
                SELECT p.nazwa,
                ROUND(SUM(EXTRACT(EPOCH FROM( c.stop_pracy - c.start_pracy , INTERVAL '0')) /3600)::numeric,2) AS godziny_pracy
                FROM czas_pracy c 
                join pracownik p on p.id_pracownik = c.id_pracownik
            """)
    List<CzasPracyDTO>findCzasPracyBetweenDates();
}
