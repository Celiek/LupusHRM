package com.Lupus.demo.repository;

import com.Lupus.demo.model.WorkHours;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//todo
//pobieranie godzin pracy dla wszystkich użyszkodników
//pobieranie godzin pracy dla jednego użyszkodnika
//pobieranie godzin pracy dla użytkonwika per dzień,per tydzeiń i  per miesiąc

//ustawianie stopu pracy dla wszystkich pracowników
//pobieranie info wraz ze zdjęciem wszystkich rpacowników



public interface WorkHoursInterface extends CrudRepository<WorkHours, Long> {

    //zwraca ilosc godzin pracownika danego dnia;
    @Transactional
    @Modifying
    @Query(value = "SELECT id_pracownika:id ,(start_pracy - stop_pracy) AS czas_pracy, opis )",nativeQuery = true)
    void getWorkHoursDaily(Long id);

    //zwraca ilosc przepracowanych godzin,imie, nazwisko i estymowanąwypłątę w biezacym tygodniu przez wszystkich pracowników
    @Query(value = "SELECT\n" +
            "    p.imie,\n" +
            "    p.nazwisko,\n" +
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
    void getWorkHoursWeekly();

    //zwraca ilosc przepracowanych godzin w biezacym miesiacu
    void getWorkHoursMonthly(Long id);

    //ustawianie startu pracy dal popjecynczego pracownika
    @Query(value = "INSERT INTO godziny_pracy (id_pracownika, data, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME FROM pracownik",
            nativeQuery = true)
    void insertWorkHoursForAllEmployees();
}
