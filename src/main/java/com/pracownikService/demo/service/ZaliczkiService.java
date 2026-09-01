package com.pracownikService.demo.service;

import com.pracownikService.demo.Dto.ZaliczkaDTO;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.entity.Zaliczki;
import com.pracownikService.demo.repo.PracownikRepo;
import com.pracownikService.demo.repo.ZaliczkiRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ZaliczkiService {
    @Autowired
    private final ZaliczkiRepository zaliczkiRepo;

    @Autowired
    private final PracownikRepo pracownikRepo;

    @Transactional
    public void createZaliczki(List<ZaliczkaDTO> dtos){
        List<Zaliczki> zaliczki = new ArrayList<>();
        for(ZaliczkaDTO dto : dtos){
            Pracownik pracowik = pracownikRepo.findById(dto.getIdPracownik())
                    .orElseThrow( ()->
                            new RuntimeException("nie znaleziono pracownika o podanym id :"
                                    + dto.getIdPracownik()));

            if(dto.getKwota().compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("Kwota zaliczki nie może być ujemna ani równa zeru");
            }

            Zaliczki zaliczka = new Zaliczki();
            zaliczka.setKwota(dto.getKwota());
            zaliczka.setDataZaliczki(dto.getDataZaliczki());
            zaliczka.setPracownik(pracowik);
            zaliczki.add(zaliczka);
        }
        zaliczkiRepo.saveAll(zaliczki);
    }

    @Transactional
    public void createZaliczka(ZaliczkaDTO dto) {

        Pracownik pracownik = pracownikRepo
                .findById(dto.getIdPracownik())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nie znaleziono pracownika o id: "
                                        + dto.getIdPracownik()
                        )
                );

        Zaliczki zaliczka = new Zaliczki();

        zaliczka.setKwota(dto.getKwota());
        zaliczka.setDataZaliczki(dto.getDataZaliczki());
        zaliczka.setPracownik(pracownik);

        zaliczkiRepo.save(zaliczka);
    }

    public List<ZaliczkaDTO> findAllForPracownik(Long idPracownik) {

        if (!pracownikRepo.existsById(idPracownik)) {
            throw new RuntimeException(
                    "Nie znaleziono pracownika o id: " + idPracownik
            );
        }

        return zaliczkiRepo.findAllByPracownik_IdPracownik(idPracownik);
    }


    // wszystkie zaliczki z konkretnego dnia
    public List<ZaliczkaDTO> findAllByDate(LocalDate dataZaliczki) {

        return zaliczkiRepo.findAllByDataZaliczki(dataZaliczki);
    }


    // zaliczka konkretnego pracownika z konkretnego dnia
    public List<ZaliczkaDTO> findForPracownikAndDate(
            Long idPracownik,
            LocalDate dataZaliczki) {

        return zaliczkiRepo
                .findByPracownik_IdPracownikAndDataZaliczki(
                        idPracownik,
                        dataZaliczki
                );
    }


    // konkretna zaliczka
    public ZaliczkaDTO findZaliczka(
            Long idZaliczki,
            Long idPracownik) {

        return zaliczkiRepo
                .findByIdZaliczkiAndPracownik_IdPracownik(
                        idZaliczki,
                        idPracownik
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nie znaleziono zaliczki o id: "
                                        + idZaliczki
                                        + " dla pracownika o id: "
                                        + idPracownik
                        )
                );
    }


    // ==========================================
    // AKTUALIZACJA
    // ==========================================

    // aktualizacja konkretnej zaliczki
    @Transactional
    public void updateZaliczka(
            Long idZaliczki,
            Long idPracownik,
            BigDecimal kwota,
            LocalDate dataZaliczki) {

        int updated = zaliczkiRepo.updateZaliczka(
                idZaliczki,
                idPracownik,
                kwota,
                dataZaliczki
        );

        if (updated == 0) {
            throw new RuntimeException(
                    "Nie znaleziono zaliczki o id: "
                            + idZaliczki
                            + " dla pracownika o id: "
                            + idPracownik
            );
        }
    }


    // aktualizacja zaliczek wszystkich pracowników
    // z konkretnego dnia
    @Transactional
    public void updateZaliczkiForPracownicyByDate(
            LocalDate dataZaliczki,
            BigDecimal kwotaZaliczki) {

        int updated =
                zaliczkiRepo.updateZaliczkiForPracownicyByDate(
                        dataZaliczki,
                        kwotaZaliczki
                );

        if (updated == 0) {
            throw new RuntimeException(
                    "Nie znaleziono zaliczek z dnia: "
                            + dataZaliczki
            );
        }
    }
}
