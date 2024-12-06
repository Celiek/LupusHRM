package com.LUPUS.lupus.repository;

import com.LUPUS.lupus.DTO.WyplataTygodniowaDTO;
import com.LUPUS.lupus.entity.wyplataTygodniowa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

//TODO
//dodawanie wyplat tygodniowych DONE
//usuwanie wyplat tygodniowych
//zliczanie wyplat tygodniowcyh z ostatniego miesiaca
//


public interface TygodniowaRepository extends CrudRepository<wyplataTygodniowa, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT into wyplata_tygodniowa(kwota_tygodniowa,zaliczka_tygodniowa,data_wyplaty_tygodniowej,id_pracownika)\n" +
            "VALUES(:kwota,:zaliczka,:data_wyplaty_tygodniowej,:idPracownika", nativeQuery = true)
    void insertWeelkyPaycheck(@Param("idPracownika")Long idPracownika,
                                         @Param("kwota")Double kwota,
                                         @Param("zaliczka")Double zaliczka,
                                         @Param("data_wyplaty_tygodniowej")Date data_wyplaty);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO wyplata_tygodniowa (kwota_tygodniowa, zaliczka_tygodniowa, data_wyplaty_tygodniowej, id_pracownika) " +
            "SELECT :kwota, :zaliczka, :dataWyplaty, p.id_pracownika FROM pracownik p", nativeQuery = true)
    void insertWeeklyPaychecks(@Param("kwota") Double kwota,
                               @Param("zaliczka") Double zaliczka,
                               @Param("dataWyplaty") Date dataWyplaty);
    //aktualizacja wypłaty dla pojedynczego pracownika
    @Modifying
    @Transactional
    @Query(value= "UPDATE wyplata_tygodniowa w SET w.kwotaTygodniowa = :kwota, w.zaliczkaTygodniowa = :zaliczka, w.dataWyplatyTygodniowej = :dataWyplaty WHERE w.idPracownika = :idPracownika",nativeQuery = true)
    void updateWeeklyPaycheckForEmployee(Long idPracownika, Double kwota, Double zaliczka, Date dataWyplaty);

    //aktualizacja wyplaty dla wszystkich pracowników
    @Modifying
    @Transactional
    @Query(value = "UPDATE wyplata_tygodniowa SET kwota_tygodniowa= :kwota,zaliczka_tygodniowa=:zaliczka,data_wyplaty_tygodniowej = :data",nativeQuery = true)
    void updateWeeklyPaycheckForEmployees(@RequestParam("kwota")Double kwota,
                                          @RequestParam("zaliczka")Double zaliczka,
                                          @RequestParam("data")Date data);

        //do przerobienia idk co mialem w glowie jak to pisalem
    //    @Query(value = "SELECT new com.example.dto.WyplataTygodniowaDTO(wt.idPracownika, p.imie, p.nazwisko, " +
    //            "wt.kwotaTygodniowa, wt.zaliczkaTygodniowa, wt.dataWyplatyTygodniowej) " +
    //            "FROM wyplata_tygodniowa wt " +
    //            "JOIN Pracownik p ON wt.idPracownika = p.idPracownika " +
    //            "WHERE wt.dataWyplatyTygodniowej = :dataWyplaty " +
    //            "ORDER BY p.idPracownika",nativeQuery = true)
    //    List<WyplataTygodniowaDTO> findWeeklyPaymentsWithDetails(@Param("dataWyplaty") Date dataWyplaty);







}