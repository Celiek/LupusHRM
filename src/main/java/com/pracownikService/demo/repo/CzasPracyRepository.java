package com.pracownikService.demo.repo;

import com.pracownikService.demo.entity.CzasPracy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
            """)
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
            """
            SELECT SUM(
                    FUNCTION('EXTRACT', 'EPOCH', (c.stopPracy - c.startPracy)) /3600
                ) FROM CzasPracy c
                WHERE c.dataPracy BETWEEN :start AND :stop
            """
    )
    Double sumGodzinyPracyBetweenDates(LocalDate start, LocalDate stop);
}
