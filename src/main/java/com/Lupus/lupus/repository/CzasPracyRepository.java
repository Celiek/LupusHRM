package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.czas_pracy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO czas_pracy (id_pracownik,data_pracy,start_pracy) 
        VALUES (:id_pracownik,
                :data,
                :start)
    """,nativeQuery =
            true)
    void setStartPracyForPracownik(@Param("id_pracownik")Long idPracownik,
                                   @Param("data")LocalDate data,
                                   @Param("start")LocalTime start);

    //start pracy dla pracownikow
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO czas_pracy (id_pracownik, data_pracy, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME " +
            "FROM pracownik", nativeQuery = true)
    void insertStartDayForEmployees();

    // dodaje przerwe w ciagu rpacy dla pracownikow
    @Modifying
    @Transactional
    @Query(value="UPDATE czas_pracy \n" +
            "    SET czas_przerwy = COALESCE(czas_przerwy, INTERVAL '0') + CAST(:przerwa AS INTERVAL) \n" +
            "    WHERE data_pracy = :data", nativeQuery = true)
    void insertPrzerwa(@Param("przerwa") String przerwa,
                       @Param("data") LocalDate data);


    //stop pracy dla pojedynczego uzytkownika
    @Modifying
    @Transactional
    @Query("UPDATE czas_pracy c SET c.stop_pracy = CURRENT_TIME WHERE c.id_pracownik = :id AND c.data_pracy = CURRENT_DATE")
    void stopPracyDlaPracownika(@Param("id") Long id);

    //stop pracy dla wielu pracowników
    @Modifying
    @Transactional
    @Query("UPDATE czas_pracy c SET c.stop_pracy = CURRENT_TIME WHERE c.id_pracownik IN :ids AND c.data_pracy = CURRENT_DATE")
    void stopPracaDlaWieluPracownikow(@Param("ids") List<Long> ids);


    //zlicza ilosc godzin przepracowanych godzin danego dnia dla wszystkich pracownikow
    @Query(value = "SELECT p.imie, p.nazwisko, p.drugie_imie, c.data_pracy, c.start_pracy, c.stop_pracy, " +
            "COALESCE(c.czas_przerwy, INTERVAL '0 seconds') AS czas_przerwy, " +
            "NULL AS error_message " +
            "FROM pracownik p " +
            "JOIN czas_pracy c ON c.id_pracownik = p.id_pracownika " +
            "WHERE c.data_pracy = :dataPracy", nativeQuery = true)
    List<Object[]> findCzasPracyByDate  (@Param("dataPracy") LocalDate dataPracy);

    @Query(value = "SELECT p.id_pracownika, p.imie, p.nazwisko, p.drugie_imie, c.data_pracy, c.start_pracy, c.stop_pracy, " +
            "COALESCE(c.czas_przerwy, INTERVAL '0 seconds') AS czas_przerwy, " +
            "NULL AS error_message " +
            "FROM pracownik p " +
            "JOIN czas_pracy c ON c.id_pracownik = p.id_pracownika " +
            "WHERE c.data_pracy = :dataPracy", nativeQuery = true)
    List<Object[]> findCzasPracyByDateID  (@Param("dataPracy") LocalDate dataPracy);

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

    @Query(value = "SELECT c.id_pracownik, " +
            "SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy - COALESCE(c.czas_przerwy, INTERVAL '0')))/3600) AS godziny_pracy " +
            "FROM czas_pracy c " +
            "WHERE c.id_pracownik = :id_pracownika " +
            "AND c.data_pracy BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id_pracownik",
            nativeQuery = true)
    List<Map<String, Object>> findGodzinyPracyBetweenDates(@Param("id_pracownika")Long id_pracownik, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    //ilosc godzin przepracowanych przez pracownikow od od do
    @Query(value = "SELECT c.id_pracownik, p.imie, p.nazwisko, p.zdjecie, " +
            "SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy - COALESCE(c.czas_przerwy, INTERVAL '0')))/3600) " +
            "FROM czas_pracy c " +
            "Join pracownik p on p.id_pracownika  = c.id_pracownik "+
            "WHERE c.data_pracy BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id_pracownik, p.imie, p.nazwisko, p.zdjecie",
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

    @Query(value = """
    SELECT 
        c.data_pracy,
        ROUND(SUM(EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy)) / 3600.0), 2) AS suma_godzin,
        c.id_pracownika
    FROM 
        czas_pracy c
    WHERE 
        c.start_pracy IS NOT NULL 
        AND c.stop_pracy IS NOT NULL 
        AND c.data_pracy BETWEEN :startDate AND :endDate
    GROUP BY 
        c.data_pracy, c.id_pracownika
    ORDER BY 
        c.data_pracy, c.id_pracownika
    """, nativeQuery = true)
    List<Object[]> findGodzinyPracyByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

// zwraca liste dni pracy dla pracownika po id i dacie od do
@Query(value = """
    SELECT 
        p.imie, 
        p.nazwisko, 
        p.drugie_imie, 
        c.data_pracy,
        ROUND((
            EXTRACT(EPOCH FROM (c.stop_pracy - c.start_pracy - COALESCE(c.czas_przerwy, INTERVAL '0'))) / 3600
        )::NUMERIC, 2) AS godziny_pracy
    FROM pracownik p
    JOIN czas_pracy c ON c.id_pracownik = p.id_pracownika
    WHERE c.id_pracownik = :idPracownika
      AND c.data_pracy >= :dataOd 
      AND c.data_pracy <= :dataDo
    ORDER BY c.data_pracy
    """, nativeQuery = true)
    List<Object[]> findDniPracyZakres(
        @Param("idPracownika") Long idPracownika,
        @Param("dataOd") LocalDate dataOd,
        @Param("dataDo") LocalDate dataDo);

    @Transactional
    @Modifying
    @Query(value = "UPDATE czas_pracy c SET " +
            "c.start_pracy = COALESCE(:startPracy, c.startPracy), " +
            "c.stop_pracy = COALESCE(:stopPracy, c.stopPracy), " +
            "c.czas_przerwy = COALESCE(:czasPrzerwy, c.czasPrzerwy) " +
            "WHERE c.idPracownika = :idPracownika AND c.dataPracy = :dataPracy",nativeQuery = true)
    void updateCzasPracy(@Param("idPracownika") Long idPracownika,
                         @Param("dataPracy") Date dataPracy,
                         @Param("startPracy") LocalTime startPracy,
                         @Param("stopPracy") LocalTime stopPracy,
                         @Param("czasPrzerwy") Duration czasPrzerwy);

    @Query(value = """
    SELECT p.id_pracownika, p.imie, p.nazwisko,p.zdjecie
    FROM pracownik p
    LEFT JOIN czas_pracy c ON c.id_pracownik = p.id_pracownika
    AND c.data_pracy = :data
    WHERE c.start_pracy IS NULL
    OR c.data_pracy IS NULL
    ORDER BY p.nazwisko, p.imie
    """, nativeQuery = true)
    List<Object[]> findPracownicyNieRozpoczeliPracyDnia(@Param("data") LocalDate data);

}
