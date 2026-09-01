package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.ZaliczkaDTO;
import com.pracownikService.demo.service.ZaliczkiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/zaliczki")
@RequiredArgsConstructor
public class ZaliczkiController {

    private final ZaliczkiService zaliczkiService;

    @PostMapping
    public ResponseEntity<Void> createZaliczka(
            @RequestBody ZaliczkaDTO dto) {

        zaliczkiService.createZaliczka(dto);

        return ResponseEntity.ok().build();
    }


    // ==========================================
    // POBIERANIE
    // ==========================================

    // wszystkie zaliczki pracownika
    @GetMapping("/pracownik/{idPracownik}")
    public ResponseEntity<List<ZaliczkaDTO>> findAllForPracownik(
            @PathVariable Long idPracownik) {

        return ResponseEntity.ok(
                zaliczkiService.findAllForPracownik(idPracownik)
        );
    }


    // wszystkie zaliczki z konkretnego dnia
    @GetMapping("/date")
    public ResponseEntity<List<ZaliczkaDTO>> findAllByDate(
            @RequestParam LocalDate dataZaliczki) {

        return ResponseEntity.ok(
                zaliczkiService.findAllByDate(dataZaliczki)
        );
    }


    // zaliczki pracownika z konkretnego dnia
    @GetMapping("/pracownik/{idPracownik}/date")
    public ResponseEntity<List<ZaliczkaDTO>> findForPracownikAndDate(
            @PathVariable Long idPracownik,
            @RequestParam LocalDate dataZaliczki) {

        return ResponseEntity.ok(
                zaliczkiService.findForPracownikAndDate(
                        idPracownik,
                        dataZaliczki
                )
        );
    }


    // konkretna zaliczka pracownika
    @GetMapping("/{idZaliczki}/pracownik/{idPracownik}")
    public ResponseEntity<ZaliczkaDTO> findZaliczka(
            @PathVariable Long idZaliczki,
            @PathVariable Long idPracownik) {

        return ResponseEntity.ok(
                zaliczkiService.findZaliczka(
                        idZaliczki,
                        idPracownik
                )
        );
    }


    // ==========================================
    // AKTUALIZACJA
    // ==========================================

    // aktualizacja jednej zaliczki
    @PutMapping("/{idZaliczki}/pracownik/{idPracownik}")
    public ResponseEntity<Void> updateZaliczka(
            @PathVariable Long idZaliczki,
            @PathVariable Long idPracownik,
            @RequestParam(required = false) BigDecimal kwota,
            @RequestParam(required = false) LocalDate dataZaliczki) {

        zaliczkiService.updateZaliczka(
                idZaliczki,
                idPracownik,
                kwota,
                dataZaliczki
        );

        return ResponseEntity.noContent().build();
    }


    // aktualizacja zaliczek wszystkich pracowników
    // z konkretnego dnia
    @PutMapping("/pracownicy/date")
    public ResponseEntity<Void> updateZaliczkiForPracownicyByDate(
            @RequestParam LocalDate dataZaliczki,
            @RequestParam BigDecimal kwotaZaliczki) {

        zaliczkiService.updateZaliczkiForPracownicyByDate(
                dataZaliczki,
                kwotaZaliczki
        );

        return ResponseEntity.noContent().build();
    }
}
