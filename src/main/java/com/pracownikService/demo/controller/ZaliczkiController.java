package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.ZaliczkaDTO;
import com.pracownikService.demo.service.ZaliczkiService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createZaliczka(
            @RequestBody ZaliczkaDTO dto) {
        try{

            zaliczkiService.createZaliczka(dto);
            return ResponseEntity.ok().build();

        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createZaliczki(@Valid List<ZaliczkaDTO> zaliczki){
        try{
            zaliczkiService.createZaliczki(zaliczki);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }

    }


    // wszystkie zaliczki pracownika
    @GetMapping("/pracownik/{idPracownik}")
    public ResponseEntity<?> findAllForPracownik(
            @PathVariable Long idPracownik) {

        try{
            return ResponseEntity.ok(
                    zaliczkiService.findAllForPracownik(idPracownik)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }

    }


    // wszystkie zaliczki z konkretnego dnia
    @GetMapping("/date")
    public ResponseEntity<?> findAllByDate(
            @RequestParam LocalDate dataZaliczki) {
        try{
            return ResponseEntity.ok(zaliczkiService.findAllByDate(dataZaliczki));
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }


    // zaliczki pracownika z konkretnego dnia
    @GetMapping("/pracownik/{idPracownik}/date")
    public ResponseEntity<?> findForPracownikAndDate(
            @PathVariable Long idPracownik,
            @RequestParam LocalDate dataZaliczki) {

        try{
            return ResponseEntity.ok(
                    zaliczkiService.findForPracownikAndDate(
                            idPracownik,
                            dataZaliczki
                    )
            );
        } catch (Exception e ){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }


    }


    // konkretna zaliczka pracownika
    @GetMapping("/{idZaliczki}/pracownik/{idPracownik}")
    public ResponseEntity<?> findZaliczka(
            @PathVariable Long idZaliczki,
            @PathVariable Long idPracownik) {
        try {
            return ResponseEntity.ok(
                    zaliczkiService.findZaliczka(
                            idZaliczki,
                            idPracownik
                    )
            );
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }


    // aktualizacja jednej zaliczki
    @PutMapping("/{idZaliczki}/pracownik/{idPracownik}")
    public ResponseEntity<?> updateZaliczka(
            @PathVariable Long idZaliczki,
            @PathVariable Long idPracownik,
            @RequestParam(required = false) BigDecimal kwota,
            @RequestParam(required = false) LocalDate dataZaliczki) {

        try {
            zaliczkiService.updateZaliczka(
                    idZaliczki,
                    idPracownik,
                    kwota,
                    dataZaliczki
            );
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }

    }


    // aktualizacja zaliczek wszystkich pracowników
    // z konkretnego dnia
    @PutMapping("/pracownicy/date")
    public ResponseEntity<?> updateZaliczkiForPracownicyByDate(
            @RequestParam LocalDate dataZaliczki,
            @RequestParam BigDecimal kwotaZaliczki) {
        try {
            zaliczkiService.updateZaliczkiForPracownicyByDate(
                    dataZaliczki,
                    kwotaZaliczki
            );

            return ResponseEntity.noContent().build();
            }
        catch (Exception e){
                return ResponseEntity
                        .internalServerError()
                        .body(e.getMessage());
            }
        }
}
