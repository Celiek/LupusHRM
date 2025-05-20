package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.urlopy;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
    List<Object[]> addUrlopForPracownik(@Param("id_Pracownika")Long id_Pracownika,
                                        @Param("data_Od")LocalDate data_Od,
                                        @Param("data_Do")LocalDate data_do,
                                        @Param("typ_Urlopu")String typ_Urlopu,
                                        @Param("powdo")String powod);

    // awaryjne dodanie urlopu pracownikom
    @Query(value = """
            INSERT INTO urlopy (id_pracownika, data_od, data_do, typ_urlopu, powod)
                SELECT p.id_pracownika, :dataOd, :dataDo, :typUrlopu, :powod
                FROM pracownik p
            """,nativeQuery = true)
    int addUrlopForEmployees(@Param("dataOd") LocalDate dataOd,
                                        @Param("dataDo") LocalDate dataDo,
                                        @Param("typUrlopu") String typUrlopu,
                                        @Param("powod") String powod);

    // ususwa urlop po dacie
    @Query(value = """
            Delete from urlop
            Where data_od = :data_od AND data_do = :data_do;
            """, nativeQuery = true)
     void removeUrlop(@Param("data_od")LocalDate data_od,
                      @Param("data_do")LocalDate data_do);

    // aktualizuje dane urlopu(nie wszystkie muszą być podane)
    @Modifying
    @Transactional
    @Query(value = """
            Update urlopy 
            id_pracownika = Coalesce(:idPracownika, id_pracownika),
            data_od = Coalesce(:dataOd,data_od),  
            data_do = Coalesce(:dataDo, data_do),
            typ_urlop = Coalesce(:typUrlopu, typ_urlopu),
            powod = Coalesce(:powod, powod)
            WHERE id = :id
            """, nativeQuery = true)
    void updateUrlop(@Param("id") Long id,
                     @Param("idPracownika") Long idPracownika,
                     @Param("dataOd")LocalDate dataOd,
                     @Param("dataDo")LocalDate dataDo,
                     @Param("typUrlopu")String typUrlopu,
                     @Param("powod")String powod);
}
