package com.Lupus.lupus.repository;

import com.Lupus.lupus.entity.uwagiPracownikow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface uwagiPracownikowRepository extends CrudRepository<uwagiPracownikow,Long> {
    @Query(value = "SELECT * FROM uwagi_pracownikow WHERE id_pracownika = :id", nativeQuery = true)
    List<Object[]> findAllByPracownikId(@Param("id") Long id);

    @Query(value="SELECT * FROM uwagi_pracownikow OREDR BY DESC",nativeQuery = true)
    List<Object[]> findAllUwagi();

    @Query(value = "SELECT * FROM uwagi_pracownikow WHERE LOWER(tresc_uwagi) LIKE LOWER(CONCAT('%', :szukanyTekst, '%'))", nativeQuery = true)
    List<Object[]> searchByTresc(@Param("szukanyTekst") String szukanyTekst);

    @Query(value = "SELECT * FROM uwagi_pracownikow WHERE data_dodania > :data", nativeQuery = true)
    List<Object[]> findByDataPo(@Param("data") LocalDate data);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM uwagi_pracownikow WHERE id_pracownika = :id", nativeQuery = true)
    void deleteByPracownikId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE uwagi_pracownikow
    SET
        id_pracownika = COALESCE(:idPracownika, id_pracownika),
        data_dodania = COALESCE(:dataDodania, data_dodania),
        tresc_uwagi = COALESCE(:trescUwagi, tresc_uwagi),
        autor_uwagi = COALESCE(:autorUwagi, autor_uwagi)
    WHERE id_uwagi = :idUwagi
    """, nativeQuery = true)
    void updateUwagaOpcjonalnie(
            @Param("idUwagi") Long idUwagi,
            @Param("idPracownika") Long idPracownika,
            @Param("dataDodania") LocalDate dataDodania,
            @Param("trescUwagi") String trescUwagi,
            @Param("autorUwagi") String autorUwagi
    );

}
