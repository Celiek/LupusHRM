package com.Lupus.lupus.repository;

import com.Lupus.lupus.DTO.CzasPracyDTO;
import com.Lupus.lupus.entity.czas_pracy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


//TODO:
//ilosc godzin przepracowanych przez     jednego pracownika danego dnia, i ilosc przepracowanych godzin od do DONE
//ilosc przepracowanych godzin przez wszystkich prcownikow danego dnia, i ilosc od do DONE
//update godzin pracy
//usuwanie godzin pracy
//obliczanie estymowanej wyplaty po ilosci przepracowanych godzin

//zmiana czasu startu i konca pracy dla zdefiniowanego pracownika pod konkretna date
//zmiana czasu startu i konca pracy dla wszystkich pracownikow

public interface CzasPracyRepository extends JpaRepository<czas_pracy,Long> {
    //aktualizuje przepracowanych godzin przez pracownika danego dnia
    @Modifying
    @Transactional
    @Query(value = "UPDATE czas_pracy SET start_pracy = CURRENT_TIME WHERE id_pracownik = :id AND data_pracy = CURRENT_DATE",nativeQuery = true)
    void updateStartPracy(Long id);

    //start pracy dla pracownikow
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO czas_pracy (id_pracownik, data_pracy, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME " +
            "FROM pracownik", nativeQuery = true)
    void insertStartDayForEmployees();

    //zlicza ilosc godzin przepracowanych godzin danego dnia dla wszystkich pracownikow
    @Query(value = "SELECT c.id_pracownik, c.data_pracy, c.start_pracy, c.stop_pracy, \n" +
            "       COALESCE(c.czas_przerwy, INTERVAL '0 seconds') AS czas_przerwy, \n" +
            "       NULL AS error_message\n" +
            "FROM czas_pracy c\n" +
            "WHERE c.data_pracy = :dataPracy",nativeQuery = true)
    List<Object[]> findCzasPracyByDate(@Param("dataPracy") LocalDate dataPracy);

    //zlicza ilosc godzin przepracowanych danego dnia dla jednego pracownika
    //poprawic tak zeby imie nazwisko i zdjecie zwracalo
    //dodac drugie zapytanie dla wszystkich pracownikow
    @Query(value = "SELECT id_pracownik, data_pracy, SUM(EXTRACT(EPOCH FROM (stop_pracy - start_pracy - COALESCE(czas_przerwy, INTERVAL '0')))/3600) AS godziny_pracy " +
            "FROM czas_pracy " +
            "WHERE id_pracownik = :idPracownika " +
            "AND data_pracy = :dataPracy " +
            "GROUP BY id_pracownik  , data_pracy", nativeQuery = true)
    List<Object[]> findGodzinyPracyForEmployeeOnDate(@Param("idPracownika") Long idPracownika,
                                                @Param("dataPracy") LocalDate dataPracy);

    //ilosc godzin przepracowanych przez jedego pracownika od do
//    @Query("SELECT new com.Lupus.lupus.dto.CzasPracyDTO(c.id_pracownik, c.data_pracy, c.start_pracy, c.stop_pracy, " +
//            "COALESCE(c.czas_przerw, 'PT0S'::interval)) " +
//            "FROM czas_pracy c WHERE c.data_pracy BETWEEN :startDate AND :endDate")
//    List<CzasPracyDTO> findGodzinyPracyBetweenDates(@Param("startDate") LocalDate startDate,
//                                                    @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT c.id_pracownik, " +
            "SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy - COALESCE(c.czas_przerwy, INTERVAL '0')))/3600) AS godziny_pracy " +
            "FROM czas_pracy c " +
            "WHERE c.id_pracownik = :id_pracownika " +
            "AND c.data_pracy BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id_pracownik",
            nativeQuery = true)
    List<Map<String, Object>> findGodzinyPracyBetweenDates(@Param("id_pracownika")Long id_pracownik, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



    //ilosc godzin przepracowanych przez pracownikod od do
    @Query(value = "SELECT c.id_pracownik, " +
            "SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy - COALESCE(c.czas_przerwy, INTERVAL '0')))/3600) " +
            "FROM czas_pracy c " +
            "WHERE c.data_pracy BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id_pracownik",
            nativeQuery = true)
    List<Object[]> sumGodzinyPracy(@Param("startDate") LocalDate startDate,
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
