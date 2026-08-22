package com.pracownikService.demo.repo;

import com.pracownikService.demo.Dto.WyplatyDTO;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WyplatyRepository {

    @Query(
            value = """
                    SELECT new com.pracownikService.demo.DTO.WyplatyDTO(
                        w.idWyplaty,
                        w.idPracownik,
                        p.nazwa,
                        w.dataWyplaty,
                        w.dataOd,
                        w.dataDo,
                        w.kwotaWyplaty
                    )
                    FROM Wyplaty w 
                    JOIN w.pracownik p 
                    WHERE p.idPracownik = :idPracownik
                    """
    )
    List<WyplatyDTO> findAllWyplatyForPracownik(Long idPracownik);

    // konkretna wyplata pracownika
    Optional<WyplatyDTO> findByPracownik_IdPracownikAndIdWyplaty(Long idPracownik, Long idWyplaty);

    //wyplaty z konkretnego dnia
    Optional<WyplatyDTO> findAllByDataWyplaty(LocalDate dataWyplaty);

    //wyplaty pracownika z konkretnego dnia
    List<WyplatyDTO> findByPracownik_IdPracownikAndDataWyplaty(Long idPracownik, LocalDate dataWyplaty);
}
