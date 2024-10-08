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


public interface WorkHoursInterface extends CrudRepository<WorkHours, Long> {
    //zwraca ilosc godzin pracownika danego dnia;
    @Transactional
    @Modifying
    @Query(value = "SELECT id_pracownika:id ,(start_pracy - stop_pracy) AS czas_pracy opis )",nativeQuery = true)
    void getWorkHoursDaily(Long id);

    //zwraca ilosc przepracowanych godzin w biezacym tygodniu
    void getWorkHoursWeekly(Long id);

    //zwraca ilosc przepracowanych godzin w biezacym miesiacu
    void getWorkHoursMonthly(Long id);

    @Query(value = "INSERT INTO godziny_pracy (id_pracownika, data, start_pracy) " +
            "SELECT id_pracownika, CURRENT_DATE, CURRENT_TIME FROM pracownik",
            nativeQuery = true)
    void insertWorkHoursForAllEmployees();
}
