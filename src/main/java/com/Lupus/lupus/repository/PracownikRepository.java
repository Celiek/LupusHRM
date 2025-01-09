package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.pracownik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PracownikRepository extends CrudRepository<pracownik, Long> {

    @Query(value="SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik ORDER BY imie",nativeQuery = true)
    List<Object[]> findallUsers();

    //nie pamietam po co to dodalem
//    @Query(value="SELECT id_pracownika, imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik ORDER BY imie",nativeQuery = true)
//    List<Object[]> findUsersWithId();

    @Query(value = "SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik where id_pracownika= :idPracownik",nativeQuery = true)
    List<Object[]> findUserById(@Param("idPracownik")Long idPracownik);

    @Query(value="SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia FROM pracownik where imie = :imie",nativeQuery = true)
    List<Object[]> findUserByName(@Param("imie")String imie);

    @Query(value="DELETE from pracownik where id_pracownika = :idPracownik",nativeQuery = true)
    void deletePracownikById(@Param("idPracownik")Long idPracownik);

    @Query(value = "Insert into pracownik(imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia, login, haslo " +
            "VALUES(:imie,:dimie, :nazwisko, :typ, :zdjecie,:data,:login,:haslo);",nativeQuery = true)
    void addPracownik(@Param("imie")String imie,
                      @Param("dimie")String dimie,
                      @Param("nazwisko")String nazwisko,
                      @Param("typ")String typ,
                      @Param("zdjecie")Byte[] zdjecie,
                      @Param("data") LocalDate data,
                      @Param("login")String login,
                      @Param("haslo")String haslo);

    @Query(value="UPDATE pracownik\n" +
            "SET \n" +
            "    imie = COALESCE(:imie, imie),\n" +
            "    drugie_imie = COALESCE(:dimie, drugie_imie),\n" +
            "    nazwisko = COALESCE(:nazwisko, nazwisko),\n" +
            "    typ_pracownika = COALESCE(:typ_pracownika, typ_pracownika),\n" +
            "    zdjecie = COALESCE(:zdjecie, zdjecie),\n" +
            "    data_dolaczenia = COALESCE(:data_dolaczenia, data_dolaczenia),\n" +
            "    login = COALESCE(:login, login),\n" +
            "    haslo = COALESCE(:haslo, haslo)\n" +
            "WHERE id_pracownika = :id_pracownika;",nativeQuery = true)
    void updatePracownik(@Param("imie")String imie,
                         @Param("dimie")String dimie,
                         @Param("nazwisko")String nazwisko,
                         @Param("typ")String typ,
                         @Param("zdjecie")Byte[] zdjecie,
                         @Param("data") LocalDate data,
                         @Param("login")String login,
                         @Param("haslo")String haslo);
}
