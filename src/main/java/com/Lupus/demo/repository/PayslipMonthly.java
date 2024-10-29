package com.Lupus.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface PayslipMonthly extends CrudRepository<PayslipMonthly,Long> {

    //dodawanie mieisecznej wypłąty dla pracownika
    @Query(value="INSERT INTO wyplaty_miesieczne (id_pracownika, kwota, data_wyplaty)\n" +
            "VALUES (:idPracownika, :kwota, NOW());",nativeQuery = true)
    void addMonthlyPayslipForEmployee(Long idPracownika, Double kwota);
    //dodawanie miesięcznej wypłaty dla pracowników
    @Query(value = "INSERT INTO wyplata_miesieczna (id_pracownika, kwota, data_wyplaty)\n" +
            "SELECT id_pracownika, :kwota, CURRENT_DATE  -- Ustaw kwotę lub zastąp 5000.00 odpowiednią wartością\n" +
            "FROM pracownik;",nativeQuery = true)
    void addMonthlyPayslips(Double kwota);
    //aktualizacja miesięcznej wypłaty dla pracownika
    @Query(value = "UPDATE wyplaty_miesieczne\n" +
            "SET kwota = :kwota, \n" +
            "    data_wyplaty = :dataWyplaty,)\n" +
            "WHERE id_pracownika = :idPracownika;\n",nativeQuery = true)
    void updateMonthlyPayslipForEmployee(Long idPracownika, Double kwota, Date dataWyplaty);
    //aktualizacja miesięcznej wypłąty dla pracownika
    @Query(value = "UPDATE wyplaty_miesieczne\n" +
            "SET kwota = :kwota,\n" +
            "    data_wyplaty = :dataWyplaty;\n",nativeQuery = true)
    void updateMonthlyPayslipsForEmployees(Double kwota, Date dataWyplaty);
    //usuwanie miesięcznej wypłaty dla pracownika
    @Query(value = "DELETE FROM wyplaty_miesieczne\n" +
            "WHERE id_pracownika = :idPracownika\n" +
            "    AND data_wyplaty = :dataWyplaty\n",nativeQuery = true)
    void deleteMonthlyPayslipForEmployeeId(Long idPracownika, Date dataWyplaty);
    //usuwanie miesięcznej wypłaty dla pracowników
    @Query(value = "DELETE FROM wyplaty_miesieczne\n" +
            "WHERE data_wyplaty = :dataWyplaty;",nativeQuery = true)
    void deleteMonthlyPayslipForEmployees(Date dataWyplaty);
}
