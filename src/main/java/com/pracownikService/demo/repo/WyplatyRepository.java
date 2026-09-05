package com.pracownikService.demo.repo;

import com.pracownikService.demo.Dto.WyplatyDTO;
import com.pracownikService.demo.entity.Wyplaty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WyplatyRepository extends JpaRepository<Wyplaty, Long> {

    @Query("""
    SELECT new com.pracownikService.demo.Dto.WyplatyDTO(
        w.idWyplaty,
        w.pracownik.idPracownik,
        p.nazwa,
        w.dataWyplaty,
        w.dataOd,
        w.dataDo,
        w.kwotaWyplaty
    )
    FROM Wyplaty w
    JOIN w.pracownik p
    WHERE p.idPracownik = :idPracownik
    """)
    List<WyplatyDTO> findAllWyplatyForPracownik(@Param("idPracownik")
                                                Long idPracownik);

    // zwraca wypłąty z zakresu dat od do
    @Query("""
            SELECT new com.pracownikService.demo.Dto.WyplatyDTO(
                w.idWyplaty,
                w.pracownik.idPracownik,
                p.nazwa,
                w.dataWyplaty,
                w.dataOd,
                w.dataDo,
                w.kwotaWyplaty
        )
            FROM Wyplaty w
            JOIN w.pracownik p
            WHERE w.dataWyplaty BETWEEN :start AND :stop
        """)
    List<WyplatyDTO> findAllWyplatyBetweenDates(@Param("start") LocalDate start,
                                                @Param("stop") LocalDate stop);

    // konkretna wyplata pracownika
    Optional<WyplatyDTO> findByPracownik_IdPracownikAndIdWyplaty(Long idPracownik, Long idWyplaty);

    //wyplaty z konkretnego dnia dla wszsytkich pracowników
    List<WyplatyDTO> findAllByDataWyplaty(LocalDate dataWyplaty);

    //wyplaty pracownika z konkretnego dnia
    List<WyplatyDTO> findByPracownik_IdPracownikAndDataWyplaty(Long idPracownik, LocalDate dataWyplaty);

    //zwraca Wyplaty dla pracownika po id
    Optional<Wyplaty> findByIdWyplatyAndIdPracownik(Long idWyplaty, Long idPracownik);

    //zwraca Wyplaty dla pracownikow po idWyplaty
    List<Wyplaty> findAllWyplatyAndPracownik_IdPracownikIn(Long idWyplaty, List<Long> idPracownika);

//    void saveAll(List<Wyplaty> wyplaty);

    //do aktualizacji wyplat dla wielu pracownikow
    List<Wyplaty> findAllByPracownik_IdPracownikInAndDataWyplaty(
            List<Long> idPracownikow,
            LocalDate dataWyplaty
    );
}
