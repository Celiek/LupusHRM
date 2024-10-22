package com.Lupus.demo.repository;

import com.Lupus.demo.model.Payslip;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO
//zliczanie wypłat tygodniowych z potrąceniem zaliczki
//zliczanie wypłat miesięcznych z potrąceniem zalizek tygodniowych
//dodanie wypłaty dla jednego pracownika DONE
//dodanie wypłaty dla wszystkich pracowników DONE
//potrącenie z wypłaty kosztów za uszkodzenia/cokowliek innego

//ustawianie wypłat tygodniowych DONE
//ustawianie wypłat miesięcznych DONE
//ustawianie zaliczek


public interface PayslipInterface extends CrudRepository<Payslip, Long> {


    //dodawanie wypłaty tyogdniowej dla pojedyńczego pracownika
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_tygodniowa (id_pracownika, kwota, data_wyplaty, zaliczka) " +
            "VALUES (:idPracownika, :kwota, NOW(), :zaliczka)",
            nativeQuery = true)
    void insertWeeklyPayment(@Param("idPracownika") Long idPracownika,
                             @Param("kwota") Double kwota,
                             @Param("zaliczka") Double zaliczka);

    //dodawanie wypłaty miesięcznej dla pojedyńczego pracownika
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_miesieczna (id_pracownika, kwota, data_wyplaty) " +
            "VALUES (:idPracownika, :kwotaMiesieczna, NOW())",
            nativeQuery = true)
    void insertMonthlyPayment(@Param("idPracownika") Long idPracownika,
                              @Param("kwotaMiesieczna") Double kwotaMiesieczna);

    //dodawanie wypłaty tygodniowej dla wszystkich pracowników
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_tygodniowa (id_pracownika, kwota, data_wyplaty, zaliczka) " +
            "SELECT p.id_pracownika, :kwota, NOW(), :zaliczka " +
            "FROM pracownik p",
            nativeQuery = true)
    void insertWeeklyPayments(@Param("kwota") Double kwota, @Param("zaliczka") Double zaliczka);

    //dodawanie wypłaty miesięcznej dla wszystkich pracowników
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_miesieczna (id_pracownika, kwota, data_wyplaty) " +
            "SELECT p.id_pracownika, :kwotaMiesieczna, NOW() " +
            "FROM pracownik p",
            nativeQuery = true)
    void insertMonthlyPayments(@Param("kwotaMiesieczna") Double kwotaMiesieczna);

    //aktualizacja wypłat wszystkich pracowników w danym tygodniu
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_tygodniowa wyplata " +
            "SET kwota = :nowaKwota, " +
            "zaliczka = :nowaZaliczka, " +
            "data_wyplaty = NOW() " +
            "FROM pracownik p " +
            "WHERE wyplata.id_pracownika = p.id_pracownika",
            nativeQuery = true)
    void updateWeeklyPayments(@Param("nowaKwota") Double nowaKwota,
                              @Param("nowaZaliczka") Double nowaZaliczka);

    //aktualizacja wypłat wszystkich pracowników w danym miesiącu
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_miesieczna wyplata " +
            "SET kwota = :nowaKwotaMiesieczna, " +
            "data_wyplaty = NOW() " +
            "FROM pracownik p " +
            "WHERE wyplata.id_pracownika = p.id_pracownika",
            nativeQuery = true)
    void updateMonthlyPayments(@Param("nowaKwotaMiesieczna") Double nowaKwotaMiesieczna);


    //modyfikacja tygodniowej wypłaty i zaliczki dla jednego pracownika
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_tygodniowa " +
            "SET kwota = :nowaKwota, " +
            "zaliczka = :nowaZaliczka " +
            "WHERE id_pracownika = :idPracownika",
            nativeQuery = true)
    void updateWeeklyPayment(@Param("nowaKwota") Double nowaKwota,
                             @Param("nowaZaliczka") Double nowaZaliczka,
                             @Param("idPracownika") Long idPracownika);

    //modyfikacja miesięczej wypłaty i
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_miesieczna " +
            "SET kwota = :nowaKwotaMiesieczna " +
            "WHERE id_pracownika = :idPracownika",
            nativeQuery = true)
    void updateMonthlyPayment(@Param("nowaKwotaMiesieczna") Double nowaKwotaMiesieczna,
                              @Param("idPracownika") Long idPracownika);



}
