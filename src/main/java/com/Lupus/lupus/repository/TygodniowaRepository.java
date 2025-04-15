package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.wyplataTygodniowa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

//TODO
//dodawanie wyplat tygodniowych DONE
//usuwanie wyplat tygodniowych Done
//pokazywanie wyplat tygodniowych dla pracownikow DONE
//zliczanie wyplat miesiecznych dla pracownikow DONE
//pokazywanie wyplat miesiecznych dla pracownikow DONE
//pokazywanie wyplat tygodniowych dla pracownika DONE
//dodac ilosc przepracowanych godzin dzisiajc


public interface TygodniowaRepository extends CrudRepository<wyplataTygodniowa, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT into wyplata_tygodniowa(kwota_tygodniowa,zaliczka_tygodniowa,data_wyplaty_tygodniowej,id_pracownika)\n" +
            "VALUES(:kwota,:zaliczka,:data_wyplaty_tygodniowej,:idPracownika)", nativeQuery = true)
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

    //wyswietlanie wyplaty tygodniowej dla pracownika po imieniu i nazwisku
    //TODO
    //dodac przedzial dat DONE
    @Transactional
    @Modifying
    @Query(value = "select p.imie,p.nazwisko, w.kwota_tygodniowa,w.zaliczka_tygodniowa,w.data_wyplaty_tygodniowej\n" +
            "from wyplata_tygodniowa w \n" +
            "join pracownik p on p.id_pracownika = w.id_pracownika " +
            "WHERE DATE_PART('week',w.data_wyplaty_tygodniowej) = DATE_PART('week', :data)" +
            "AND (p.imie = :imie AND p.nazwisko = :nazwisko)"
            ,nativeQuery = true)
    void getweeklyPaychekEmployee(@RequestParam("imie") String imie,
                                  @RequestParam("nazwisko") String nazwisko,
                                  @RequestParam("data") Date data);

    //wyswietlanie wyplaty tygodniowej dla wszystkich pracownikow
    @Query(value = "select p.imie,p.nazwisko, w.kwota_tygodniowa,w.zaliczka_tygodniowa,w.data_wyplaty_tygodniowej\n" +
            "from wyplata_tygodniowa w \n" +
            "join pracownik p on p.id_pracownika = w.id_pracownika ",
    nativeQuery = true)
    void getWeeklyPaycheks();

    @Query(value = "select p.imie,p.nazwisko, w.kwota_tygodniowa,w.zaliczka_tygodniowa,w.data_wyplaty_tygodniowej\n" +
            "from wyplata_tygodniowa w \n" +
            "join pracownik p on p.id_pracownika = w.id_pracownika WHERE p.imie = :imie AND p.nazwisko = :nazwisko",
    nativeQuery = true)
    void getWeeklyPaychek(@RequestParam("imie") String imie,
                          @RequestParam("nazwisko")String nazwisko);

    //zliczana miesieczna wyplata dla pracownikow
    @Query(value="SELECT \n" +
            "    p.imie,\n" +
            "\tp.drugie_imie,\n" +
            "    p.nazwisko,\n" +
            "    SUM(w.kwota_tygodniowa) AS suma_wyplat_miesiecznych,\n" +
            "\tSUM(w.kwota_tygodniowa - w.zaliczka_tygodniowa) AS wyplata_z_potraceniem\n" +
            "FROM \n" +
            "    pracownik p\n" +
            "JOIN \n" +
            "    wyplata_tygodniowa w ON p.id_pracownika = w.id_pracownika\n" +
            "where \n" +
            "\tw.data_wyplaty_tygodniowej BETWEEN :start AND :stop \n" +
            "GROUP BY \n" +
            "    p.imie, \n" +
            "    p.nazwisko,\n" +
            "\tp.drugie_imie;",
    nativeQuery = true)
    void getMonthlyPaycheks(@RequestParam("start") Date start,
                            @RequestParam("stop") Date stop);

    //miesieczna wyplata dla pracownika
    @Query(value = "SELECT p.imie, p.drugie_imie, p.nazwisko,\n" +
            "SUM(w.kwota_tygodniowa) AS suma_wyplat_miesiecznych,\n" +
            "SUM(w.kwota_tygodniowa - w.zaliczka_tygodniowa) AS wyplata_z_potraceniem\n" +
            "FROM pracownik p JOIN \n" +
            "wyplata_tygodniowa w ON p.id_pracownika = w.id_pracownika\n" +
            "where w.data_wyplaty_tygodniowej BETWEEN :start AND :stop \n" +
            "AND p.imie = :imie AND p.drugie_imie = :dimie AND p.nazwisko = :nazwisko\n" +
            "GROUP BY \n" +
            "p.imie, \n" +
            "p.nazwisko,\n" +
            "p.drugie_imie;",nativeQuery = true)
    void getMonthlyPaycheck(@RequestParam("start") Date start,
                            @RequestParam("stop") Date stop,
                            @RequestParam("imie") String imie,
                            @RequestParam("dimie")String  dimie,
                            @RequestParam("nazwisko") String nazwisko);
}