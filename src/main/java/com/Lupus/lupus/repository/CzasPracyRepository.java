package com.LUPUS.lupus.repository;

import com.LUPUS.lupus.DTO.CzasPracyDTO;
import com.LUPUS.lupus.entity.czas_pracy;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


//TODO:
//ilosc godzin przepracowanych przez jednego pracownika danego dnia, i ilosc przepracowanych godzin od do DONE
//ilosc przepracowanych godzin przez wszystkich prcownikow danego dnia, i ilosc od do DONE
//update godzin pracy
//usuwanie godzin pracy
//obliczanie estymowanej wyplaty po ilosci przepracowanych godzin

public interface CzasPracyRepository extends CrudRepository<czas_pracy,Long> {
    //aktualizuje przepracowanych godzin przez pracownika danego dnia
    @Modifying
    @Transactional
    @Query(value = "UPDATE GodzinyPracy g SET g.startPracy = CURRENT_TIME WHERE g.idGodzinyPracy = :id",nativeQuery = true)
    void updateStartPracy(Long id);

    //start pracy dla pracownikow
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO czas_pracy (id_pracownika, data_pracy, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME " +
            "FROM pracownik", nativeQuery = true)
    void insertStartDayForEmployees();

    //zlicza ilosc godzin przepracowanych godzin danego dnia dla wszystkich pracownikow
    @Query("SELECT new com.LUPUS.lupus.dto.CzasPracyDTO(g.idPracownika, g.dataPracy, g.startPracy, g.stopPracy, " +
            "COALESCE(g.czasPrzerwy, 'PT0S'::interval)) " +  // Konwersja na interval
            "FROM GodzinyPracy g WHERE g.dataPracy = :dataPracy")
    List<CzasPracyDTO> findGodzinyPracyByDate(@Param("dataPracy") LocalDate dataPracy);



    //zlicza ilosc godzin przepracowanych danego dnia dla jednego pracownika
    //poprawic tak zeby imie nazwisko i zdjecie zwracalo
    //dodac drugie zapytanie dla wszystkich pracownikow
    @Query(value = "SELECT id_pracownika, data_pracy, SUM(EXTRACT(EPOCH FROM (stop_pracy - start_pracy - COALESCE(czas_przerwy, INTERVAL '0')))/3600) AS godziny_pracy " +
            "FROM czas_pracy " +
            "WHERE id_pracownika = :idPracownika " +
            "AND data_pracy = :dataPracy " +
            "GROUP BY id_pracownika, data_pracy", nativeQuery = true)
    List<CzasPracyDTO> findGodzinyPracyForEmployeeOnDate(@Param("idPracownika") Long idPracownika,
                                                         @Param("dataPracy") LocalDate dataPracy);

    //ilosc godzin przepracowanych przez jedego pracownika od do
    @Query("SELECT new com.LUPUS.lupus.dto.CzasPracyDTO(g.idPracownika, g.dataPracy, g.startPracy, g.stopPracy, " +
            "COALESCE(g.czasPrzerwy, 'PT0S'::interval)) " +
            "FROM GodzinyPracy g WHERE g.dataPracy BETWEEN :startDate AND :endDate")
    List<CzasPracyDTO> findGodzinyPracyBetweenDates(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);


    //ilosc godzin przepracowanych przez pracownikod od do
    @Query(value = "SELECT id_pracownika, " +
            "SUM(EXTRACT(EPOCH FROM (stop_pracy - start_pracy - COALESCE(czas_przerwy, INTERVAL '0')))/3600) AS godziny_pracy " +
            "FROM czas_pracy " +
            "WHERE data_pracy BETWEEN :startDate AND :endDate " +
            "GROUP BY id_pracownika", nativeQuery = true)
    List<CzasPracyDTO> sumGodzinyPracy(@Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);

    //aktualizuje godzine startu czasu pracy dla pracownika
    @Modifying
    @Transactional
    @Query(value = "UPDATE czas_pracy SET start_pracy = CURRENT_TIMESTAMP " +
            "WHERE id_pracownik = :idPracownika AND data_pracy = :dataPracy",nativeQuery = true)
    void updateStartPracy(@Param("idPracownika") Long idPracownika,
                          @Param("dataPracy") LocalDate dataPracy);


    //aktualizuje start pracy dla wszystkich pracownikow
    @Modifying
    @Transactional
    @Query(value = "UPDATE czas_pracy SET start_pracy = CURRENT_TIME WHERE data_pracy = :data_pracy",nativeQuery = true)
    void updateStartPracyByDataPracy(@Param("data_pracy") LocalDate data_pracy);

}
