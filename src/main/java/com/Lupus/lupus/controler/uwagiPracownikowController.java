package com.Lupus.lupus.controler;

import com.Lupus.lupus.service.uwagiPracownikowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/uwagi")
public class uwagiPracownikowController {

    @Autowired
    private uwagiPracownikowService service;

    @GetMapping("/byId")
    public ResponseEntity<List<Object[]>> findAllByPracownikID(@RequestParam Long id){
        try{
            List<Object[]> results = service.getUwagiByPracownikId(id);
            return ResponseEntity.ok(results);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Object[]>> findAllUwagi(){
        try{
            List<Object[]> results = service.getAllUwagi();
            return ResponseEntity.ok(results);
        } catch (Exception e){
            return  ResponseEntity.status(500).build();
        }
    }


    // Szukanie po treści uwagi
    @GetMapping("/szukaj")
    public ResponseEntity<List<Object[]>> searchByTresc(@RequestParam String tekst) {
        try {
            List<Object[]> wynik = service.searchByTresc(tekst);
            return ResponseEntity.ok(wynik);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Znajdź wszystkie uwagi po dacie dodania
    @GetMapping("/poDacie")
    public ResponseEntity<List<Object[]>> findByDataPo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Object[]> wynik = service.findByDataPo(data);
            return ResponseEntity.ok(wynik);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Usunięcie wszystkich uwag danego pracownika
    @DeleteMapping("/usunWszystkieDlaPracownika/{id}")
    public ResponseEntity<String> deleteByPracownikId(@PathVariable Long id) {
        try {
            service.deleteByPracownikId(id);
            return ResponseEntity.ok("Usunięto wszystkie uwagi pracownika o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd: " + e.getMessage());
        }
    }

    // Aktualizacja uwagi (opcjonalne pola)
    @PostMapping("/edytujOpcjonalnie")
    public ResponseEntity<String> updateOpcjonalnie(
            @RequestParam Long idUwagi,
            @RequestParam(required = false) Long idPracownika,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDodania,
            @RequestParam(required = false) String trescUwagi,
            @RequestParam(required = false) String autorUwagi) {
        try {
            service.updateUwagaOPcjonalne(idUwagi, idPracownika, dataDodania, trescUwagi, autorUwagi);
            return ResponseEntity.ok("Zaktualizowano uwagę o ID: " + idUwagi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd: " + e.getMessage());
        }
    }
}
