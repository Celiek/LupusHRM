package com.pracownikService.demo.repo;

import com.pracownikService.demo.Dto.PracownikDTO;
import com.pracownikService.demo.entity.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface PracownikRepo extends JpaRepository<Pracownik,Long> {

    void addPracownik(PracownikDTO dto);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE Pracownik p
            Set p.zdjecie = :zdjecie
            WHERE p.id = :id;
            """,nativeQuery = true)
    int updateZdjecie(@Param("id")Long id,
                       @Param("zdjecie")String zdjecie);
}
