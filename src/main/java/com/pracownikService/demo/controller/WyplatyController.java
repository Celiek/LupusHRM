package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.CreateWyplataDTO;
import com.pracownikService.demo.Dto.WyplatyDTO;
import com.pracownikService.demo.entity.Wyplaty;
import com.pracownikService.demo.service.WyplatyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/wyplaty")
@RequiredArgsConstructor
public class WyplatyController {

    private final WyplatyService wyplatyService;


    // ==========================================
    // DODAWANIE WYPŁAT
    // ==========================================

    // dodanie wypłaty jednego pracownika
    @PostMapping
    public ResponseEntity<Void> createWyplata(
            @RequestBody CreateWyplataDTO dto) {

        wyplatyService.createWyplata(dto);

        return ResponseEntity.ok().build();
    }


    // dodanie wypłat wielu pracowników
    @PostMapping("/many")
    public ResponseEntity<Void> createWyplaty(
            @RequestBody List<CreateWyplataDTO> dtos) {

        wyplatyService.createWyplaty(dtos);

        return ResponseEntity.ok().build();
    }


    // ==========================================
    // POBIERANIE WYPŁAT
    // ==========================================

    // wszystkie wypłaty pracownika
    @GetMapping("/pracownik/{idPracownik}")
    public ResponseEntity<List<WyplatyDTO>> findAllWyplatyForPracownik(
            @PathVariable Long idPracownik) {

        return ResponseEntity.ok(
                wyplatyService.findAllWyplatyForPracownik(idPracownik)
        );
    }


    // wypłaty wszystkich pracowników pomiędzy datami
    @GetMapping("/between")
    public ResponseEntity<List<WyplatyDTO>> findAllWyplatyBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate stop) {

        return ResponseEntity.ok(
                wyplatyService.findAllWyplatyBetweenDates(start, stop)
        );
    }


    // konkretna wypłata pracownika
    @GetMapping("/pracownik/{idPracownik}/wyplata/{idWyplaty}")
    public ResponseEntity<WyplatyDTO> findWyplataForPracownik(
            @PathVariable Long idPracownik,
            @PathVariable Long idWyplaty) {

        return ResponseEntity.ok(
                wyplatyService.findWyplataForPracownik(
                        idPracownik,
                        idWyplaty
                )
        );
    }


    // wypłaty wszystkich pracowników z konkretnego dnia
    @GetMapping("/date")
    public ResponseEntity<List<WyplatyDTO>> findWyplatyForDate(
            @RequestParam LocalDate dataWyplaty) {

        return ResponseEntity.ok(
                wyplatyService.findWyplatyForDate(dataWyplaty)
        );
    }


    // wypłaty konkretnego pracownika z konkretnego dnia
    @GetMapping("/pracownik/{idPracownik}/date")
    public ResponseEntity<List<WyplatyDTO>> findWyplatyForPracownikAndDate(
            @PathVariable Long idPracownik,
            @RequestParam LocalDate dataWyplaty) {

        return ResponseEntity.ok(
                wyplatyService.findWyplatyForPracownikAndDate(
                        idPracownik,
                        dataWyplaty
                )
        );
    }


    // konkretna encja wypłaty pracownika
    @GetMapping("/{idWyplaty}/pracownik/{idPracownik}")
    public ResponseEntity<Wyplaty> findWyplata(
            @PathVariable Long idWyplaty,
            @PathVariable Long idPracownik) {

        return ResponseEntity.ok(
                wyplatyService.findWyplata(
                        idWyplaty,
                        idPracownik
                )
        );
    }


    // konkretna wypłata dla wielu pracowników
    @GetMapping("/{idWyplaty}/pracownicy")
    public ResponseEntity<List<Wyplaty>> findWyplatyForPracownicy(
            @PathVariable Long idWyplaty,
            @RequestParam List<Long> idPracownikow) {

        return ResponseEntity.ok(
                wyplatyService.findWyplatyForPracownicy(
                        idWyplaty,
                        idPracownikow
                )
        );
    }


    // ==========================================
    // AKTUALIZACJA
    // ==========================================

    // aktualizacja kwoty wypłaty jednego pracownika
    @PutMapping("/pracownik/{idPracownik}/kwota")
    public ResponseEntity<Void> updateKwotaWyplatyForPracownik(
            @PathVariable Long idPracownik,
            @RequestParam LocalDate dataWyplaty,
            @RequestParam BigDecimal kwotaWyplaty) {

        wyplatyService.updateKwotaWyplatyForPracownik(
                idPracownik,
                dataWyplaty,
                kwotaWyplaty
        );

        return ResponseEntity.noContent().build();
    }


    // aktualizacja kwoty wypłat wielu pracowników
    @PutMapping("/pracownicy/kwota")
    public ResponseEntity<Void> updateKwotaWyplatyForPracownicy(
            @RequestParam List<Long> idPracownikow,
            @RequestParam LocalDate dataWyplaty,
            @RequestParam BigDecimal kwotaWyplaty) {

        wyplatyService.updateKwotaWyplatyForPracownicy(
                idPracownikow,
                dataWyplaty,
                kwotaWyplaty
        );

        return ResponseEntity.noContent().build();
    }
}