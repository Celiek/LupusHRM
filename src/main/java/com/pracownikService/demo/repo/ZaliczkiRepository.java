package com.pracownikService.demo.repo;

import com.pracownikService.demo.Dto.ZaliczkaDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
}
