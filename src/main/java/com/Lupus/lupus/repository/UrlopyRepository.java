package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.urlopy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UrlopyRepository extends CrudRepository<urlopy, Long> {

    @Query(value = "select p.imie ||' ' || p.nazwisko as imie_i_nazwisko, u.data_od, u.data_do,u.typ_urlopu, u.powod, u.id, p.id_pracownika from pracownik p \n" +
            "join urlopy u on u.id_pracownika  = p.id_pracownika", nativeQuery = true)
    List<Object[]> findAllUrlopy();

    // lista urlopów dla osoby po id pracownika
    // TODO dodać sortowanie urlopów od najnowszych do najstarszych
    @Query(value = "select p.imie ||' ' || p.nazwisko as imie_i_nazwisko, u.data_od, u.data_do from pracownik p \n" +
            "join urlopy u on u.id_pracownika  = p.id_pracownika\n" +
            "where p.id_pracownika = :idPracownika",nativeQuery = true)
    List<Object[]> findUrlopyFor(@Param("idPracownika")Long idPracownika);


    @Query(value ="SELECT u,data",nativeQuery = true )
    List<Object[]> findListaUrlopow(@Param("idPracownika")Long idPracownika);

    //dodaje urlopy pracownikowi
    @Modifying
    @Transactional
    @Query(value = "insert into urlopy (id_pracownika, data_od, data_do, typ_urlopu,powod)\n" +
            "values (:id_Pracownika, :data_Od,:data_Do, :typ_Urlopu, :powod);\n",nativeQuery = true)
    List<Object[]> addUrlopForPracownik(@Param("id_Pracownika")Long id_Pracownika,
                                        @Param("data_Od")LocalDate data_Od,
                                        @Param("data_Do")LocalDate data_do,
                                        @Param("typ_Urlopu")String typ_Urlopu,
                                        @Param("powod")String powod);

    // awaryjne dodanie urlopu pracownikom
    @Modifying
    @Transactional
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
    @Modifying
    @Transactional
    @Query(value = """
    DELETE FROM urlopy
    WHERE id = :id
    """, nativeQuery = true)
    void removeUrlop(@Param("id") Long id);

    // aktualizuje dane urlopu(nie wszystkie muszą być podane)
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE urlopy\s
                SET id_pracownika = COALESCE(:idPracownika, id_pracownika),
                    data_od = COALESCE(:dataOd, data_od), \s
                    data_do = COALESCE(:dataDo, data_do),
                    typ_urlopu = COALESCE(:typUrlopu, typ_urlopu),
                    powod = COALESCE(:powod, powod)
                WHERE id = :id
    """, nativeQuery = true)
    void updateUrlop(@Param("id") Long id,
                     @Param("idPracownika") Long idPracownika,
                     @Param("dataOd") LocalDate dataOd,
                     @Param("dataDo") LocalDate dataDo,
                     @Param("typUrlopu") String typUrlopu,
                     @Param("powod") String powod);

    @Query(value = "SELECT u.dataOd, u.dataDo FROM Urlop u WHERE u.pracownik.id = :idPracownika",nativeQuery = true)
    List<Object[]> findZakresyUrlopow(@Param("idPracownika") Long idPracownika);

    @Query(value = """
    SELECT DISTINCT c.data_pracy
    FROM czas_pracy c
    WHERE c.id_pracownik = :idPracownika
      AND c.data_pracy BETWEEN :dataOd AND :dataDo
    ORDER BY c.data_pracy
""", nativeQuery = true)
    List<LocalDate> findDniPracy(
            @Param("idPracownika") Long idPracownika,
            @Param("dataOd") LocalDate dataOd,
            @Param("dataDo") LocalDate dataDo);

}
