package com.pracownikService.demo.repo;


import com.pracownikService.demo.Dto.pracownik.PracownikDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikWithIdDTO;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.entity.TypPracownika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PracownikRepo extends JpaRepository<Pracownik,Long> {
    

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Pracownik p
            Set p.zdjecie = :zdjecie
            WHERE p.id = :id;
            """,nativeQuery = true)
    int updateZdjecie(@Param("id")Long id,
                       @Param("zdjecie")String zdjecie);


    @Modifying
    @Query("""
    UPDATE Pracownik p
        SET p.nazwa = :nazwa,
            p.wiek = :wiek,
            p.typPracownika = :typPracownika
        WHERE p.idPracownik = :idPracownik
    """)
    int updatePracownik(
            @Param("idPracownik") Long idPracownik,
            @Param("nazwa") String nazwa,
            @Param("wiek") Integer wiek,
            @Param("typPracownika") TypPracownika typPracownika
    );

    @Query(value = """
            SELECT nazwa, wiek, typ_pracownika,role,zdjecie,data_dolaczenia,data_rozpoczecia_pracy from pracownik
            """,nativeQuery = true)
    List<PracownikDTO> findAllPracownikDto();

    Optional<PracownikWithIdDTO> findByIdPracownik(Long idPracownik);

    @Query("""
            SELECT COUNT(p)
            FROM Pracownik p
            """)
    Long countPracownicy();

}
