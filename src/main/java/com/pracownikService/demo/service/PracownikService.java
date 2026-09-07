package com.pracownikService.demo.service;

import com.pracownikService.demo.Dto.pracownik.PracownikDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikCreateDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikUpdateDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikWithIdDTO;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.entity.TypPracownika;
import com.pracownikService.demo.repo.PracownikRepo;
import com.pracownikService.exception.PracownikError;
import com.pracownikService.exception.PracownikException;
import com.pracownikService.exception.PracownikNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
            pracownik.setTypPracownika(dto.getTypPracownika());

            pracownik.setZdjecie(linkDoZdjecia);

            repo.save(pracownik);
    }

    public void updateZdjecie(Long id, MultipartFile zdjecie){
        if(id <= 0 || id == null){
            throw new PracownikException(
                    PracownikError.PROVIDED_WRONG_ID,"ID pracownika nie może być mniejsze od zera !"
            );
        }

        if(zdjecie == null || zdjecie.isEmpty()){
            throw new RuntimeException("Nie przesłano zdjęcia ");
        }

        String sciezka = uploadService.uploadZdjecie(id,zdjecie);

        int updated = repo.updateZdjecie(id,sciezka);

        if(updated == 0){
            throw new PracownikException(
                        PracownikError.PRACOWNIK_NOT_FOUND,
                        "Nie znaleziono pracownika z podanym id!");
        }
    }

    public List<PracownikDTO> findAllPracownikDTO(){
        return repo.findAllPracownikDto();
    }

    public PracownikWithIdDTO findPracownikDetailsById(Long idPracownika){
        if(idPracownika <= 0){
            throw  new RuntimeException("Id pracownika nie może być mniejsze od 1");
        }

        return repo.findByIdPracownik(idPracownika)
                .orElseThrow( () -> new PracownikNotFoundException("Pracownik o podanym id nie istnieje"));
    }

    public Long countAllPracownicy(){
        Long pracownicy = repo.countPracownicy();

        if(pracownicy == 0){
            return 0L;
        }

        return pracownicy;
    }

    @Transactional
    public void updatePracownik(
            Long idPracownik,
            PracownikUpdateDTO dto
        ) {
            Pracownik pracownik = repo.findById(idPracownik)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Nie znaleziono pracownika o id: " + idPracownik
                            )
                    );

            if (dto.getNazwa() != null) {
                pracownik.setNazwa(dto.getNazwa());
            }

            if (dto.getWiek() != null) {
                pracownik.setWiek(dto.getWiek());
            }

            if (dto.getTypPracownika() != null) {
                pracownik.setTypPracownika(dto.getTypPracownika());
            }

            if (dto.getRole() != null) {
                pracownik.setRole(dto.getRole());
            }

            if (dto.getZdjecie() != null) {
                pracownik.setZdjecie(dto.getZdjecie());
            }

            if (dto.getDataDolaczenia() != null) {
                pracownik.setDataDolaczenia(dto.getDataDolaczenia());
            }

            if (dto.getDataRozpoczeciaPracy() != null) {
                pracownik.setDataRozpoczeciaPracy(
                        dto.getDataRozpoczeciaPracy()
                );
            }
    }

}
