package com.Lupus.demo.repository;

import com.Lupus.demo.model.WorkHours;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

//todo
//pobieranie godzin pracy dla wszystkich użyszkodników DONE
//pobieranie godzin pracy dla jednego użyszkodnika DONE
//pobieranie godzin pracy dla pracownika per dzień DONE
//pobieranie godzin pracy dla pracownika per miesiąc DONE

//ustawianie stopu pracy dla wszystkich pracowników DONE

//ustawianie startu pracy dla wszystkich pracowników DONE
//poprawianie czasu startu pracy dla wszystkich pracowników DONE
//poprawianie czasu pracy dla jednego pracownika DONE



public interface WorkHoursInterface extends CrudRepository<WorkHours, Long> {

    //zwraca ilosc godzin pracownika danego dnia;
    @Transactional
    @Modifying
    @Query(value = "SELECT id_pracownika:id ,(start_pracy - stop_pracy) AS czas_pracy, opis )",nativeQuery = true)
    List<Object[]> getWorkHoursDaily(Long id);

    //zwraca imie, nazwisko zdjecie i estymowaną wypłątę w biezacym tygodniu przez wszystkich pracowników
    @Query(value = "SELECT\n" +
            "    p.imie,\n" +
            "    p.nazwisko, p.zdjecie" +
            "    EXTRACT(YEAR FROM h.data) AS rok,\n" +
            "    EXTRACT(WEEK FROM h.data) AS tydzien,\n" +
            "    SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) /3600 ) AS godziny_przepracowane,\n" +
            "\tSUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) * 28 AS przewidywana_wyplata\n" +
            "FROM\n" +
            "    godziny_pracy h\n" +
            "JOIN\n" +
            "    pracownik p ON h.id_pracownika = p.id_pracownika\n" +
            "GROUP BY\n" +
            "    p.imie, p.nazwisko, EXTRACT(YEAR FROM h.data), EXTRACT(WEEK FROM h.data)\n" +
            "ORDER BY\n" +
            "    p.nazwisko, p.imie, rok, tydzien;" , nativeQuery = true)
    List<Object[]> getWorkHoursWeekly();

    //zwraca ilosc przepracowanych godzin w biezacym miesiacu
    @Query(value="SELECT \n" +
            "    p.imie,\n" +
            "    p.nazwisko,\n" +
            "    EXTRACT(YEAR FROM h.data) AS rok,\n" +
            "    EXTRACT(MONTH FROM h.data) AS miesiac,\n" +
            "    SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) AS godziny_przepracowane,\n" +
            "    SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) * 28 AS przewidywana_wyplata,\n" +
            "    COALESCE(SUM(w.zaliczki), 0) AS suma_zaliczek,\n" +
            "    COALESCE(SUM(w.wyplaty_tygodniowe), 0) AS suma_wyplat_tygodniowych,\n" +
            "    (SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) * 28 \n" +
            "    - COALESCE(SUM(w.zaliczki), 0)) AS wyplata_po_odjeciu\n" +
            "FROM \n" +
            "    godziny_pracy h\n" +
            "JOIN \n" +
            "    pracownik p ON h.id_pracownika = p.id_pracownika\n" +
            "LEFT JOIN \n" +
            "    wyplaty w ON h.id_pracownika = w.id_pracownika \n" +
            "              AND EXTRACT(YEAR FROM h.data) = EXTRACT(YEAR FROM w.data)\n" +
            "              AND EXTRACT(MONTH FROM h.data) = EXTRACT(MONTH FROM w.data)\n" +
            "GROUP BY \n" +
            "    p.imie, p.nazwisko, EXTRACT(YEAR FROM h.data), EXTRACT(MONTH FROM h.data)\n" +
            "ORDER BY \n" +
            "    p.nazwisko, p.imie, rok, miesiac;\n", nativeQuery = true)
    List<Object[]> getWorkHoursMonthly(Long id);

    //ustawianie startu pracy dlawszystkich pracowników
    @Query(value = "INSERT INTO godziny_pracy (id_pracownika, data, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME FROM pracownik",
            nativeQuery = true)
    List<Object[]> insertWorkHoursForAllEmployees();

    //pobieranie ilosci przepracwonaych godzin w danym miesiacu dla pracownika
    @Query(value = "SELECT " +
            "p.imie, " +
            "p.nazwisko, " +
            "EXTRACT(YEAR FROM h.data) AS rok, " +
            "EXTRACT(MONTH FROM h.data) AS miesiac, " +
            "SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) AS godziny_przepracowane, " +
            "SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) * 28 AS przewidywana_wyplata, " +
            "COALESCE(SUM(w.zaliczki), 0) AS suma_zaliczek, " +
            "COALESCE(SUM(w.wyplaty_tygodniowe), 0) AS suma_wyplat_tygodniowych, " +
            "(SUM(EXTRACT(EPOCH FROM (h.stop_pracy - h.start_pracy)) / 3600) * 28 " +
            "- COALESCE(SUM(w.zaliczki), 0)) AS wyplata_po_odjeciu " +
            "FROM godziny_pracy h " +
            "JOIN pracownik p ON h.id_pracownika = p.id_pracownika " +
            "LEFT JOIN wyplaty w ON h.id_pracownika = w.id_pracownika " +
            "AND EXTRACT(YEAR FROM h.data) = EXTRACT(YEAR FROM w.data) " +
            "AND EXTRACT(MONTH FROM h.data) = EXTRACT(MONTH FROM w.data) " +
            "WHERE p.id_pracownika = :idPracownika " +
            "GROUP BY p.imie, p.nazwisko, EXTRACT(YEAR FROM h.data), EXTRACT(MONTH FROM h.data) " +
            "ORDER BY p.nazwisko, p.imie, rok, miesiac",
            nativeQuery = true)
    List<Object[]> findWorkHoursAndPaymentsByEmployeeId(@Param("idPracownika") Long idPracownika);

    //start pracy dla wszystkich pracownikow
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO godziny_pracy (id_pracownika, data, start_pracy, stop_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME, CURRENT_TIME + INTERVAL '8 hours' " +
            "FROM pracownik",
            nativeQuery = true)
    void startWorkForAllEmployees();

    //zatrzymuje naliczanie czasu pracy dla wszystkich pracowników / przerwa w pracy
    @Query(value = "UPDATE godziny_pracy\n" +
            "SET czas_przerw = COALESCE(czas_przerw, '00:00:00'::TIME) + (NOW() - start_pracy)\n" +
            "WHERE data = CURRENT_DATE;",nativeQuery = true)
    @Modifying
    @Transactional
    void breakTimeForAllEmployees();

    //koniec pracy, dodaje wartość do kolumny stop_pracy godzine zakońćzenia pracy
    @Query( value = "UPDATE godziny_pracy\n" +
            "SET stop_pracy = NOW();",nativeQuery = true)
    @Modifying
    @Transactional
    void endOfBreakForAllEmplyees();


    //aktualizuje czas startu pracy dla użytkownika
    @Query(value = "UPDATE godziny_pracy SET start_pracy = :czas WHERE data = CURRENT_DATE AND id_pracownika = :id_pracownika;" , nativeQuery = true)
    void updateStartTimeForEmployee(@Param("czas") LocalTime czas,
                                    @Param("id_pracownika")Long id_pracownika);

    //aktualizuje czas startu pracy dla wszystkich pracowników
    @Query(value="UPDATE godziny_pracy \n" +
            "SET start_pracy = :czas \n" +
            "WHERE data = CURRENT_DATE;")
    void updateStartTimeFOrEmployees(@Param("czas")LocalTime time);

    //pobiera czas pracy przepracowany przez danego przacownika w dniu today
    @Query(value = "SELECT " +
            "gp.data, " +
            "gp.start_pracy, " +
            "gp.stop_pracy, " +
            "gp.czas_przerw, " +
            "p.imie, " +
            "p.nazwisko, " +
            "p.zdjecie " +
            "FROM godziny_pracy gp " +
            "JOIN pracownik p ON gp.id_pracownika = p.id_pracownika " +
            "WHERE gp.data = CURRENT_DATE AND gp.id_pracownika = :idPracownika",
            nativeQuery = true)
    List<Object[]> findWorkHoursByEmployeeId(@Param("idPracownika") Long idPracownika);

    //pobierane dane pracownika z ostatnich 30 dni kalendarzowych
    @Query(value = "SELECT " +
            "gp.data, " +
            "gp.start_pracy, " +
            "gp.stop_pracy, " +
            "gp.czas_przerw, " +
            "p.imie, " +
            "p.nazwisko " +
            "FROM godziny_pracy gp " +
            "JOIN pracownik p ON gp.id_pracownika = p.id_pracownika " +
            "WHERE gp.id_pracownika = :idPracownika " +
            "AND gp.data >= CURRENT_DATE - INTERVAL '30 days' " +
            "ORDER BY gp.data DESC",
            nativeQuery = true)
    List<Object[]> findWorkHoursByEmployeeIdAndRecent(@Param("idPracownika") Long idPracownika);
}
