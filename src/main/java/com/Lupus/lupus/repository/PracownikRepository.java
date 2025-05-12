package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.Pracownik;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//dodac usuwanie po imieniu i nazwisku

public interface PracownikRepository extends CrudRepository<Pracownik, Long> {

    @Query(value="SELECT DISTINCT id_pracownika, imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia, login, haslo, kraj_pochodzenia, nr_whatsapp FROM pracownik ORDER BY imie",nativeQuery = true)
    List<Object[]> findallUsers();

    //logowanie uzytkownikow
    //zmiana na jpql
    @Query("SELECT p FROM Pracownik p WHERE p.login = :login")
    Optional<Pracownik> findByLogin(@Param("login") String login);

    @Query(value = "SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia From pracownik where id_pracownika= :idPracownik",nativeQuery = true)
    List<Object[]> findUserById(@Param("idPracownik")Long idPracownik);

    @Query(value="SELECT imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia FROM pracownik WHERE imie = INITCAP(:imie)"
            ,nativeQuery = true)
    List<Object[]> findUserByName(@Param("imie")String imie);

    @Transactional
    @Modifying
    @Query(value="DELETE from pracownik where id_pracownika = :idPracownik",nativeQuery = true)
    void deletePracownikById(@Param("idPracownik")Long idPracownik);

    @Modifying
    @Query(value = "INSERT INTO pracownik(imie, drugie_imie, nazwisko, typ_pracownika, zdjecie, data_dolaczenia, login, haslo, kraj_pochodzenia, nr_whatsapp) " +
            "VALUES(:imie, :dimie, :nazwisko, :typ, :zdjecie, :data, :login, :haslo, :kraj, :whatsapp)", nativeQuery = true)
    void addPracownik(@Param("imie") String imie,
                      @Param("dimie") String dimie,
                      @Param("nazwisko") String nazwisko,
                      @Param("typ") String typ,
                      @Param("zdjecie") byte[] zdjecie,
                      @Param("data") LocalDate data,
                      @Param("login") String login,
                      @Param("haslo") String haslo,
                      @Param("kraj") String krajPochodzenia,
                      @Param("whatsapp") String nrWhatsapp);

    @Modifying
    @Query(value = "UPDATE pracownik SET " +
            "imie = COALESCE(:imie, imie), " +
            "drugie_imie = COALESCE(:dimie, drugie_imie), " +
            "nazwisko = COALESCE(:nazwisko, nazwisko), " +
            "typ_pracownika = COALESCE(:typ, typ_pracownika), " +
            "zdjecie = COALESCE(:zdjecie, zdjecie), " +
            "data_dolaczenia = COALESCE(:data, data_dolaczenia), " +
            "login = COALESCE(:login, login), " +
            "haslo = COALESCE(:haslo, haslo), " +
            "kraj_pochodzenia = COALESCE(:kraj, kraj_pochodzenia), " +
            "nr_whatsapp = COALESCE(:whatsapp, nr_whatsapp) " +
            "WHERE id_pracownika = :id", nativeQuery = true)
    void updatePracownik(@Param("id") Long id,
                         @Param("imie") String imie,
                         @Param("dimie") String dimie,
                         @Param("nazwisko") String nazwisko,
                         @Param("typ") String typ,
                         @Param("zdjecie") byte[] zdjecie,
                         @Param("data") LocalDate data,
                         @Param("login") String login,
                         @Param("haslo") String haslo,
                         @Param("kraj") String krajPochodzenia,
                         @Param("whatsapp") String nrWhatsapp);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pracownik where imie = :imie AND nazwisko = :nazwisko"
    ,nativeQuery = true)
    void deletePracownikByNameAndSurname(@Param("imie") String imie,
                                    @Param("nazwisko") String nazwisko);

    @Transactional
    @Query(value = "Select login from pracownik where login = :login",nativeQuery = true)
    void getPracownikLogin(@Param("login") String login);

    @Query(value = "SELECT typ_pracownika from pracownik where login = :login",nativeQuery = true)
    String findRoleByLogin(@Param("login")String login);

    //zlicza ile osób rozpoczęło dzisiaj pracę
    @Query(value = """
    SELECT COUNT(DISTINCT c.id_pracownik)
    FROM czas_pracy c
    WHERE c.data_pracy = CURRENT_DATE
""", nativeQuery = true)
    long countTodayNewEmployees();

    //zwraca godzinę startu opracy pracowników
    @Query(value = """
    SELECT TO_CHAR(c.start_pracy, 'HH24:MI')
    FROM czas_pracy c
    WHERE c.data_pracy = CURRENT_DATE
    ORDER BY c.start_pracy ASC
    LIMIT 1
    """, nativeQuery = true)
    String findFirstStartTimeToday();

    //zwraca czas przerw w minutach
    @Query(value = """
    SELECT 
        SUM(EXTRACT(EPOCH FROM COALESCE(c.czas_przerwy, INTERVAL '0')) / 60)
    FROM pracownik p
    LEFT JOIN czas_pracy c 
        ON p.id_pracownika = c.id_pracownik 
        AND c.data_pracy = CURRENT_DATE
    """, nativeQuery = true)
    Double getTotalBreakTimeInMinutesToday();

    //zwraca ilość przepracowanych godzin przez pracowników - czas przerw
    @Query(value = """
    SELECT ROUND(
        EXTRACT(EPOCH FROM (NOW() - (CURRENT_DATE + c.start_pracy) - COALESCE(c.czas_przerwy, INTERVAL '0')))
        / 3600, 2) AS godziny_pracy
    FROM czas_pracy c
    JOIN pracownik p ON p.id_pracownika = c.id_pracownik
    WHERE c.data_pracy = CURRENT_DATE
      AND p.imie = 'Adam'
      AND p.nazwisko = 'Markuszewski'
      AND c.start_pracy IS NOT NULL
    LIMIT 1;
    """, nativeQuery = true)
    Double getCzasPracy();


}
