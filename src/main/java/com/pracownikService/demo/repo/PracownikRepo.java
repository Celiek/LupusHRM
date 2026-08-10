package com.pracownikService.demo.repo;

import com.Lupus.lupus.DTO.PracownikDto;
import com.pracownikService.demo.entity.Pracownik;
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

    List<PracownikDto> findAllPracownikById();
}
