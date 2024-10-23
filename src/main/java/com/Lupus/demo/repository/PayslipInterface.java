package com.Lupus.demo.repository;

import com.Lupus.demo.model.Payslip;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

//TODO
//zliczanie wypłat tygodniowych z potrąceniem zaliczki DONE
//zliczanie wypłat miesięcznych z potrąceniem zalizek tygodniowych DONE
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

    //zwraca wypłatę dla wszystkich pracowników z ostatniego miesiąca
    @Query(value = "SELECT " +
            "wm.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wm.kwota AS wyplata_miesieczna, " +
            "COALESCE(SUM(wt.zaliczka), 0) AS suma_zaliczek, " +
            "wm.kwota - COALESCE(SUM(wt.zaliczka), 0) AS wyplata_po_zaliczkach " +
            "FROM wyplata_miesieczna wm " +
            "JOIN pracownik u ON wm.id_pracownika = u.id_pracownika " +
            "LEFT JOIN wyplata_tygodniowa wt ON wm.id_pracownika = wt.id_pracownika " +
            "AND wt.data_wyplaty >= (CURRENT_DATE - INTERVAL '1 month') " +
            "WHERE wm.data_wyplaty >= (CURRENT_DATE - INTERVAL '1 month') " +
            "GROUP BY wm.id_pracownika, u.id_pracownika, wm.kwota " +
            "ORDER BY u.id_pracownika",
            nativeQuery = true)
    List<Object[]> findMonthlyPaymentsWithDetails();

    //zwraca wypłatę dla pojedyńczego pracownika z ostatniego miesiąca
    @Query(value = "SELECT " +
            "wm.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wm.kwota AS wyplata_miesieczna, " +
            "COALESCE(SUM(wt.zaliczka), 0) AS suma_zaliczek, " +
            "wm.kwota - COALESCE(SUM(wt.zaliczka), 0) AS wyplata_po_zaliczkach " +
            "FROM wyplata_miesieczna wm " +
            "JOIN pracownik u ON wm.id_pracownika = u.id_pracownika " +
            "LEFT JOIN wyplata_tygodniowa wt ON wm.id_pracownika = wt.id_pracownika " +
            "AND wt.data_wyplaty >= (CURRENT_DATE - INTERVAL '1 month') " +
            "WHERE wm.data_wyplaty >= (CURRENT_DATE - INTERVAL '1 month') " +
            "AND wm.id_pracownika = :idPracownika " +
            "GROUP BY wm.id_pracownika, u.id_pracownika, wm.kwota " +
            "ORDER BY u.id_pracownika",
            nativeQuery = true)
    List<Object[]> findMonthlyPaymentsWithDetailsByEmployeeId(@Param("idPracownika") Long idPracownika);

    //zwraca listę wypłat tygodniowych dla jednego pracownika z ostatniego miesiąca
    @Query(value = "SELECT " +
            "wt.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wt.kwota AS wyplata_tygodniowa, " +
            "wt.zaliczka, " +
            "wt.data_wyplaty " +
            "FROM wyplata_tygodniowa wt " +
            "JOIN pracownik u ON wt.id_pracownika = u.id_pracownika " +
            "WHERE wt.data_wyplaty >= (CURRENT_DATE - INTERVAL '1 month') " +
            "AND wt.id_pracownika = :idPracownika " +
            "ORDER BY wt.data_wyplaty DESC",
            nativeQuery = true)
    List<Object[]> findWeeklyPaymentsByEmployeeId(@Param("idPracownika") Long idPracownika);

    //lista wypłat tygodniowych dla wszystkich pracowników z ostatniego miesiąca
    @Query(value = "SELECT " +
            "wt.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wt.kwota AS wyplata_tygodniowa, " +
            "wt.zaliczka, " +
            "wt.data_wyplaty " +
            "FROM wyplata_tygodniowa wt " +
            "JOIN pracownik u ON wt.id_pracownika = u.id_pracownika " +
            "WHERE wt.data_wyplaty >= (CURRENT_DATE - INTERVAL '7 days') " +
            "ORDER BY wt.data_wyplaty DESC, u.nazwisko",
            nativeQuery = true)
    List<Object[]> findWeeklyPaymentsForAllEmployees();

    //lsita wypłat dla jednego pracownika z ostatniego tygodnia
    @Query(value = "SELECT " +
            "wt.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wt.kwota AS wyplata_tygodniowa, " +
            "wt.zaliczka, " +
            "wt.data_wyplaty " +
            "FROM wyplata_tygodniowa wt " +
            "JOIN pracownik u ON wt.id_pracownika = u.id_pracownika " +
            "WHERE wt.data_wyplaty >= (CURRENT_DATE - INTERVAL '7 days') " +
            "AND wt.id_pracownika = :idPracownika " +
            "ORDER BY wt.data_wyplaty DESC",
            nativeQuery = true)
    List<Object[]> findWeeklyPaymentsByEmployeId(@Param("idPracownika") Long idPracownika);

    //zwraca wypłąty tygodniowe dla wszystkich pracowników z określonego tygodnia
    @Query(value = "SELECT " +
            "wt.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wt.kwota AS wyplata_tygodniowa, " +
            "wt.zaliczka, " +
            "wt.data_wyplaty " +
            "FROM wyplata_tygodniowa wt " +
            "JOIN pracownik u ON wt.id_pracownika = u.id_pracownika " +
            "WHERE wt.data_wyplaty = :dataWyplaty " + // Parametr dla daty wypłaty
            "ORDER BY wt.data_wyplaty DESC, u.nazwisko",
            nativeQuery = true)
    List<Object[]> findPaymentsByDate(@Param("dataWyplaty") LocalDate dataWyplaty);

    //zwraca wypłaty dla jednego rpacownika z określonego okresu
    @Query(value = "SELECT " +
            "wt.id_pracownika, " +
            "u.imie, " +
            "u.nazwisko, " +
            "u.zdjecie, " +
            "wt.kwota AS wyplata_tygodniowa, " +
            "wt.zaliczka, " +
            "wt.data_wyplaty " +
            "FROM wyplata_tygodniowa wt " +
            "JOIN pracownik u ON wt.id_pracownika = u.id_pracownika " +
            "WHERE wt.data_wyplaty = :dataWypłaty " + // Parametr dla daty wypłaty
            "AND wt.id_pracownika = :idPracownika " + // Parametr dla identyfikatora pracownika
            "ORDER BY wt.data_wyplaty DESC",
            nativeQuery = true)
    List<Object[]> findPaymentsByDateAndEmployeeId(@Param("dataWypłaty") LocalDate dataWypłaty,
                                                   @Param("idPracownika") Long idPracownika);
}
