package com.LUPUS.lupus.repository;

import com.LUPUS.lupus.entity.pracownik;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface PracownikRespository extends CrudRepository<pracownik, Long> {
    // Insert query
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Pracownik (imie, nazwisko, zdjecie, stanowisko, dataZatrudnienia) " +
            "VALUES (:imie, :nazwisko, :zdjecie, :stanowisko, :dataZatrudnienia)",nativeQuery = true)
    void insertPracownik(String imie, String nazwisko, byte[] zdjecie, String stanowisko, Date dataZatrudnienia);

    // Delete query
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Pracownik p WHERE p.idPracownik = :idPracownik",nativeQuery = true)
    void deletePracownikById(Long idPracownik);

    // Update query
    @Modifying
    @Transactional
    @Query(value = "UPDATE Pracownik p SET p.nazwisko = :nazwisko, p.stanowisko = :stanowisko " +
            "WHERE p.idPracownik = :idPracownik", nativeQuery = true)
    void updatePracownik(Long idPracownik, String nazwisko, String stanowisko);
}
