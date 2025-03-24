package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.Pracownik;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//dodac usuwanie po imieniu i nazwisku

public interface PracownikRepository extends CrudRepository<Pracownik, Long> {

    @Query(value="SELECT DISTINCT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik ORDER BY imie",nativeQuery = true)
    List<Object[]> findallUsers();

    //logowanie uzytkownikow
    @Query(value = "select * from pracownik where login = :login",nativeQuery = true)
    Optional<Pracownik> findByLogin(@Param("login") String login);

    //nie pamietam po co to dodalem
//    @Query(value="SELECT id_pracownika, imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik ORDER BY imie",nativeQuery = true)
//    List<Object[]> findUsersWithId();

    @Query(value = "SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik where id_pracownika= :idPracownik",nativeQuery = true)
    List<Object[]> findUserById(@Param("idPracownik")Long idPracownik);

    @Query(value="SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia FROM pracownik WHERE imie = INITCAP(:imie)"
            ,nativeQuery = true)
    List<Object[]> findUserByName(@Param("imie")String imie);

    @Transactional
    @Modifying
    @Query(value="DELETE from pracownik where id_pracownika = :idPracownik",nativeQuery = true)
    void deletePracownikById(@Param("idPracownik")Long idPracownik);

    @Transactional
    @Modifying
    @Query(value = "Insert into pracownik(imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia, login, haslo )" +
            "VALUES(:imie,:dimie, :nazwisko, :typ, :zdjecie,:data,:login,:haslo);",nativeQuery = true)
    void addPracownik(@Param("imie")String imie,
                      @Param("dimie")String dimie,
                      @Param("nazwisko")String nazwisko,
                      @Param("typ")String typ,
                      @Param("zdjecie") byte[] zdjecie,
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
                         @Param("zdjecie") byte[] zdjecie,
                         @Param("data") LocalDate data,
                         @Param("login")String login,
                         @Param("haslo")String haslo);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pracownik where imie = :imie AND nazwisko = :nazwisko"
    ,nativeQuery = true)
    void deletePracownikByNameAndSurname(@Param("imie") String imie,
                                    @Param("nazwisko") String nazwisko);
}
