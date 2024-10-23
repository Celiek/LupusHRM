package com.Lupus.demo.repository;

import com.Lupus.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserInterface extends CrudRepository<User, Long> {
    // Tworzenie nowego użytkownika
    User save(User user);

    // Usuwanie użytkownika
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.idPracownika = :idPracownika")
    void deleteUserById(@Param("idPracownika") Long idPracownika);

    // Modyfikacja użytkownika
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.imie = :imie, u.nazwisko = :nazwisko, u.typPracownika = :typPracownika, u.zdjecie = :zdjecie, u.dataPrzyjazdu = :dataPrzyjazdu, u.dataRozpoczeciaPracy = :dataRozpoczeciaPracy, u.drugieImie = :drugieImie WHERE u.idPracownika = :idPracownika")
    void updateUser(@Param("idPracownika") Long idPracownika,
                    @Param("imie") String imie,
                    @Param("nazwisko") String nazwisko,
                    @Param("typPracownika") String typPracownika,
                    @Param("zdjecie") byte[] zdjecie,
                    @Param("dataPrzyjazdu") LocalDate dataPrzyjazdu,
                    @Param("dataRozpoczeciaPracy") LocalDate dataRozpoczeciaPracy,
                    @Param("drugieImie") String drugieImie);

    // Zmiana typu pracownika
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.typPracownika = :typPracownika WHERE u.idPracownika = :idPracownika")
    void changeEmployeeType(@Param("idPracownika") Long idPracownika,
                            @Param("typPracownika") String typPracownika);

    // Znajdź wszystkich użytkowników
    List<User> findAll();

}
