package com.Lupus.demo.repository;


import com.Lupus.demo.model.PayslipsWeekly;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface PayslipWeeklyInterface extends CrudRepository<PayslipsWeekly,Long> {
    @Query( value = "INSERT INTO wyplata_tygodniowa (id_pracownika,kwota, data_wyplaty,zaliczka) VALUES(:idPracownika,:kwota,NOW(),:zaliczka);",nativeQuery = true)
    void addWeeklyPayslip(Long idPracownika, Double kwota, Double zaliczka);
    @Query(value = "UPDATE wyplata_tygodniowa SET kwota = :kwota, zaliczka=:zalicza WHERE data = :data AND id_pracownika = :id_pracownika" ,nativeQuery = true)
    void updateWeeklyPayslipForEmployee(Long idPracownika, Double kwota, Date data, Double zaliczka);
    @Query(value = "UPDATE wyplata_tygodniowa SET kwota = :kwota, zaliczka = :zaliczka WHERE data_wyplaty = :data" ,nativeQuery = true)
    void updateWeeklyPayslipForEmployees(Double kwota, Date data);
    //usuwa wypłatę tygodniową w przypadku błędu
    @Query(value = "DELTE FROM wyplata_tygodniowa WHERE data_wypalty = :data",nativeQuery = true)
    void removeWeeklyPayslip(Date data);
    //w przypadku pomyłki dodaje wypłatę dla jednego pracownika
    @Query(value = "INSERT INTO wyplata_tygodniowa (id_pracownika,kwota,data_wyplaty,zaliczka) VALUES :idPracownika,:kwota,:dataWyplaty,:zaliczka;",nativeQuery = true)
    void addWeeklyPayslipForUser(Long id_pracownika, Double kwota, Date dataWyplaty, Double zaliczka);
}
