package com.pracownikService.demo.repo;

import com.pracownikService.demo.Dto.ZaliczkaDTO;
import com.pracownikService.demo.entity.Zaliczki;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ZaliczkiRepository {

    @Modifying
    @Query("""
            UPDATE Zaliczki z
            SET z.kwota = :kwotaZaliczki,
                z.dataZaliczki = :dataZaliczki
            where z.pracownik.idPracownik = :idPracownik
            """)
    int updateZaliczkaForPracownik(@Param("idPracownik")Long idPracownik,
                                   @Param("kwotaZaliczki") BigDecimal kwotaZaliczki,
                                   @Param("dataZaliczki") LocalDate dataZaliczki);

    @Modifying
    @Query("""
            Update Zaliczki z 
            SET z.kwota =: kwotaZaliczki
            WHERE z.dataZaliczki =: dataZaliczki
            """)
    int updateZaliczkiForPracownicyByDate(@Param("dataZaliczki") LocalDate dataZaliczki,
                                          @Param("kwotaZaliczki") BigDecimal kwotaZaliczki);

    void saveAll(List<Zaliczki> zaliczki);

    void save(Zaliczki zaliczka);

    Optional<Zaliczki> findByIdZaliczkiAndPracownik_IdPracownik(
            Long idZaliczki,
            Long idPracownik
    );

    List<ZaliczkaDTO> findByPracownik_IdPracownikAndDataZaliczki(
            Long idPracownik,
            LocalDate dataZaliczki
    );

    List<ZaliczkaDTO> findAllByDataZaliczki(
            LocalDate dataZaliczki
    );

    List<ZaliczkaDTO> findAllByPracownik_IdPracownik(
            Long idPracownik
    );

    @Modifying
    @Query("""
        UPDATE Zaliczki z
        SET z.kwota = :kwota,
            z.dataZaliczki = :dataZaliczki
        WHERE z.idZaliczki = :idZaliczki
          AND z.pracownik.idPracownik = :idPracownik
    """)
    int updateZaliczka(
            @Param("idZaliczki") Long idZaliczki,
            @Param("idPracownik") Long idPracownik,
            @Param("kwota") BigDecimal kwota,
            @Param("dataZaliczki") LocalDate dataZaliczki
    );
}
