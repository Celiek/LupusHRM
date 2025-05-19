package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.urlopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlopyRepository extends CrudRepository<urlopy, Long> {

    @Query(value = "select p.imie ||' ' || p.nazwisko as 'imie i nazwisko', u.data_od, u.data_do,u.typ_urlopu from pracownik p \n" +
            "join urlopy u on u.id_pracownika  = p.id_pracownika", nativeQuery = true)
    List<Object[]> findAllUrlopy();

    // lista urlopów dla osoby po id pracownika
    // TODO dodać sortowanie urlopów od najnowszych do najstarszych
    @Query(value = "select p.imie ||' ' || p.nazwisko as 'imie i nazwisko' from pracownik p \n" +
            "join urlopy u on u.id_pracownika  = p.id_pracownika\n" +
            "where p.id_pracownika = :idPracownika",nativeQuery = true)
    List<Object[]> findUrlopyFor(@Param("idPracownika")Long idPracownika);

    //dodaje urlopy pracownikowi
    @Query(value = "insert into urlopy (id_pracownika, data_od, data_do, typ_urlopu,powod)\n" +
            "values (:id_Pracownika, :data_Od,:data_Do, :typ_Urlopu, :powod);\n",nativeQuery = true)
    List<Object[]> addUrlopForPracownik();
}
