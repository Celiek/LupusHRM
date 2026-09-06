package com.pracownikService.demo.service;

import com.pracownikService.demo.Dto.CreateWyplataDTO;
import com.pracownikService.demo.Dto.WyplatyDTO;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.entity.Wyplaty;
import com.pracownikService.demo.repo.PracownikRepo;
import com.pracownikService.demo.repo.WyplatyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WyplatyService {

    @Autowired
    private final WyplatyRepository wyplatyRepo;
    @Autowired
    private final PracownikRepo pracownikRepo;

    @Transactional
    public void createWyplaty(List<CreateWyplataDTO> dtos){
        List<Wyplaty> wyplaty = new ArrayList<>();
        for (CreateWyplataDTO dto : dtos) {

            Pracownik pracownik = pracownikRepo
                    .findById(dto.getIdPracownik())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Nie znaleziono pracownika o id: "
                                            + dto.getIdPracownik()
                            )
                    );

            if (dto.getDataOd().isAfter(dto.getDataDo())) {
                throw new RuntimeException(
                        "Data od nie może być późniejsza niż data do"
                );
            }

           Wyplaty wyplata = new Wyplaty();

            wyplata.setPracownik(pracownik);
            wyplata.setDataWyplaty(dto.getDataWyplaty());
            wyplata.setDataOd(dto.getDataOd());
            wyplata.setDataDo(dto.getDataDo());
            wyplata.setKwotaWyplaty(dto.getKwotaWyplaty());

            wyplaty.add(wyplata);
        }
        wyplatyRepo.saveAll(wyplaty);
    }

    //zapisuje wypłatę pracownika do DB
    @Transactional
    public void createWyplata(CreateWyplataDTO dto){
        Pracownik pracownik = pracownikRepo
                .findById(dto.getIdPracownik())
                .orElseThrow( () ->
                        new RuntimeException(
                                "Nie znaleziono pracownika o podnaym id: "
                                        + dto.getIdPracownik()));

        Wyplaty wyplata = new Wyplaty();

        wyplata.setPracownik(pracownik);
        wyplata.setKwotaWyplaty(dto.getKwotaWyplaty());
        wyplata.setDataWyplaty(dto.getDataWyplaty());
        wyplata.setDataOd(dto.getDataOd());
        wyplata.setDataDo(dto.getDataDo());
        wyplata.setDataWyplaty(dto.getDataWyplaty());

        wyplatyRepo.save(wyplata);
    }

    //zwraca listę wypłąt dla pracownika
    public List<WyplatyDTO> findAllWyplatyForPracownik(Long idPracownik){
        Pracownik pracownik = pracownikRepo
                .findById(idPracownik)
                .orElseThrow( () -> new RuntimeException("Nie znaleziono pracownika o podnaym id: "
                        + idPracownik));

        return wyplatyRepo.findAllWyplatyForPracownik(idPracownik);
    }

    public List<WyplatyDTO> findAllWyplatyBetweenDates(LocalDate start,
                                                       LocalDate stop){
        return wyplatyRepo.findAllWyplatyBetweenDates(start, stop);
    }

    public WyplatyDTO findWyplataForPracownik(
            Long idPracownik,
            Long idWyplaty) {

        return wyplatyRepo
                .findByPracownik_IdPracownikAndIdWyplaty(
                        idPracownik,
                        idWyplaty
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nie znaleziono wypłaty o id: "
                                        + idWyplaty
                                        + " dla pracownika o id: "
                                        + idPracownik
                        )
                );
    }


    // zwraca wypłaty wszystkich pracowników z konkretnego dnia
    public List<WyplatyDTO> findWyplatyForDate(LocalDate dataWyplaty) {
        return wyplatyRepo.findAllByDataWyplaty(dataWyplaty);
    }


    // Wypłaty konkretnego pracownika z konkretnego dnia
    public List<WyplatyDTO> findWyplatyForPracownikAndDate(
            Long idPracownik,
            LocalDate dataWyplaty) {

        return wyplatyRepo
                .findByPracownik_IdPracownikAndDataWyplaty(
                        idPracownik,
                        dataWyplaty
                );
    }

    // zwraca encje konkretnej wypłaty pracownika
    public Wyplaty findWyplata(
            Long idWyplaty,
            Long idPracownik) {

        return wyplatyRepo
                .findByIdWyplatyAndPracownikIdPracownik(
                        idWyplaty,
                        idPracownik
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nie znaleziono wypłaty o id: "
                                        + idWyplaty
                                        + " dla pracownika o id: "
                                        + idPracownik
                        )
                );
    }

    // Konkretna wypłata dla wielu pracowników
    public List<Wyplaty> findWyplatyForPracownicy(
            Long idWyplaty,
            List<Long> idPracownikow) {

        return wyplatyRepo
                .findAllByIdWyplatyAndPracownik_IdPracownikIn(
                        idWyplaty,
                        idPracownikow
                );
    }


    //aktuazlizuje wypłatę dla jednego pracownika
    @Transactional
    public void updateKwotaWyplatyForPracownik(
            Long idPracownik,
            LocalDate dataWyplaty,
            BigDecimal kwotaWyplaty) {


        List<WyplatyDTO> wyplaty =
                wyplatyRepo.findByPracownik_IdPracownikAndDataWyplaty(
                        idPracownik,
                        dataWyplaty
                );

        if (wyplaty.isEmpty()) {
            throw new RuntimeException(
                    "Nie znaleziono wypłaty dla pracownika o id: "
                            + idPracownik
                            + " z dnia: "
                            + dataWyplaty
            );
        }

        wyplaty.forEach(wyplata ->
                wyplata.setKwotaWyplaty(kwotaWyplaty)
        );
    }

    @Transactional
    public void updateKwotaWyplatyForPracownicy(
            List<Long> idPracownikow,
            LocalDate dataWyplaty,
            BigDecimal kwotaWyplaty) {

        List<Wyplaty> wyplaty =
                wyplatyRepo
                        .findAllByPracownik_IdPracownikInAndDataWyplaty(
                                idPracownikow,
                                dataWyplaty
                        );

        if (wyplaty.isEmpty()) {
            throw new RuntimeException(
                    "Nie znaleziono wypłat dla podanych pracowników " +
                            "z dnia: " + dataWyplaty
            );
        }

        wyplaty.forEach(wyplata ->
                wyplata.setKwotaWyplaty(kwotaWyplaty)
        );
    }
}
