package com.pracownikService.demo.repo;

import com.pracownikService.demo.entity.CzasPracy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface CzasPracyRepository extends JpaRepository<CzasPracy, Long> {

    @Modifying
    @Transactional
    @Query(value = """
               UPDATE czas_pracy c
               SET c.start_pracy = CURRENT_TIMESTAMP
               WHERE c.pracownik.idPracownik = :id
            """
            ,nativeQuery = true)
    void updateCzasPracyForPracownik(Long id);

    boolean existsByPracownikIdPracownikAndDataPracy(
            Long idPracownik,
            LocalDate dataPracy
    );

    Optional<CzasPracy> findByPracownikIdPracownikAndDataPracy(
            Long idPracownik,
            LocalDate dataPracy
    );
}
