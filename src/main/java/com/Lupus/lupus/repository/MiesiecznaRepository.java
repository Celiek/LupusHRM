package com.LUPUS.lupus.repository;

import com.LUPUS.lupus.entity.wyplataMiesieczna;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface MiesiecznaRepository extends CrudRepository<wyplataMiesieczna, Long> {
    //dodaje wyplate miesieczna dla wszystkich pracownikow
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_miesieczna (kwota_miesieczna, suma_zaliczek, data_wyplaty_miesiecznej, id_pracownika) " +
            "SELECT 5000.00 AS kwota_miesieczna, " +
            "1000.00 AS suma_zaliczek, " +
            "CURRENT_DATE AS data_wyplaty_miesiecznej, " +
            "p.id_pracownika " +
            "FROM pracownik p", nativeQuery = true)
    void insertMonthlyPayment();
    //dodawanie wyplat dla pojedynczego pracownika
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO wyplata_miesieczna (kwota_miesieczna, suma_zaliczek, data_wyplaty_miesiecznej, id_pracownika) " +
            "VALUES (5000.00, 1000.00, CURRENT_DATE, :idPracownika)", nativeQuery = true)
    void inserMonthlyPayments(@Param("idPracownika") Long idPracownika);

    //modyfikacja wyplaty dla jednego pracownika
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_miesieczna SET kwota_miesieczna = :kwota, suma_zaliczek = :zaliczka WHERE id_pracownika = :idPracownika", nativeQuery = true)
    void updateMontlyPayment(@Param("kwota") Double kwota, @Param("idPracownika") Long idPracownika, @Param("zaliczka")Double zaliczka);

    //aktualizacja wyplaty dla wszystkich pracownikow
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_miesieczna " +
            "SET kwota_miesieczna = COALESCE(:nowaKwota, kwota_miesieczna), " +
            "suma_zaliczek = COALESCE(:nowaZaliczka, suma_zaliczek), " +
            "data_wyplaty_miesiecznej = COALESCE(:nowaData, data_wyplaty_miesiecznej)", nativeQuery = true)
    void updateMonthlyPayments(@Param("nowaKwota") Double nowaKwota,
                               @Param("nowaZaliczka") Double nowaZaliczka,
                               @Param("nowaData") Date nowaData);
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_miesieczna\n" +
            "SET \n" +
            "    kwota_miesieczna = :nowa_kwota DEFAULT 0," +
            "    suma_zaliczek = :nowa_zaliczka DEFAULT 0,\n" +
            "    data_wyplaty_miesiecznej = :nowa_data DEFAULT CURRENT_DATE\n" +
            "WHERE id_pracownika = :id_pracownika;", nativeQuery = true)
    void updateMonthlyPayment(@Param("nowa_kwota") Double nowaKwota,
                              @Param("nowa_zaliczka") Double nowaZaliczka,
                              @Param("nowa_data") Date nowaData,
                              @Param("id_pracownika") Long idPracownika);
    //usuwanie wyplaty miesiecznej po dacie i id_pracownika
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wyplata_miesieczna WHERE id_pracownika = :idPracownika AND data_wyplaty_miesiecznej = :dataWyplaty", nativeQuery = true)
    void deleteMonthlypaymentById(@Param("idPracownika") Long idPracownika, @Param("dataWyplaty") Date dataWyplaty);


    //usuwanie wyplaty miesiecznej wszystkim pracownikom po dacie
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wyplata_miesieczna WHERE data_wyplaty_miesiecznej = :dataWyplaty", nativeQuery = true)
    void deleteMonthlyPaymentsByDate(@Param("data")Date dataWyplaty);
}
