package com.pracownikService.demo.service;

import com.pracownikService.demo.Dto.PracownikCreateDTO;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.repo.PracownikRepo;
import com.pracownikService.exception.PracownikError;
import com.pracownikService.exception.PracownikException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PracownikService {

    @Autowired
    private final PracownikRepo repo;
    @Autowired
    private final PracownikZdjecieService uploadService;

    public void addPracownik(PracownikCreateDTO dto)  {

            if (dto.getNazwa() == null || dto.getNazwa().trim().isEmpty()) {
                throw new PracownikException(
                        PracownikError.EMPTY_NAME,
                        "Nazwa nie może być pusta"
                );

            }
            if(dto.getWiek() < 18){
                throw new PracownikException(
                        PracownikError.TOO_YOUNG,"Pracownik nie może mieć mniej niż 18 lat"
                );
            }

            String linkDoZdjecia = null;
            if (dto.getZdjecie() != null && !dto.getZdjecie().isEmpty()) {
                linkDoZdjecia = uploadService.uploadZdjecie(
                        null,
                        dto.getZdjecie()
                );
            }

            Pracownik pracownik = new Pracownik();

            pracownik.setNazwa(dto.getNazwa());
            pracownik.setWiek(dto.getWiek());
            pracownik.setTyp_pracownika(dto.getTypPracownika());

            pracownik.setZdjecie(linkDoZdjecia);

            repo.save(pracownik);
    }

    public void updateZdjecie(Long id, String zdjecie){
        if(id <= 0 || id == null){
            throw new PracownikException(
                    PracownikError.PROVIDED_WRONG_ID,"ID pracownika nie może być mniejsze od zera !"
            );
        }

        int updated = repo.updateZdjecie(id,zdjecie);

        if(updated == 0){
            throw new PracownikException(
                        PracownikError.PRACOWNIK_NOT_FOUND,
                        "Nie znaleziono pracownika z podanym id!");
        }
    }


}
