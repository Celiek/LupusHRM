package com.Lupus.lupus.controler;

import com.Lupus.lupus.service.UrlopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urlopy")
public class UrlopyController {

    @Autowired
    private UrlopService service;

    @GetMapping("/wszystkie")
    public ResponseEntity<List<Object[]>> findAllUrlopy(){
        try{
            List<Object[]> results= service.findAllUrlopy();
            return ResponseEntity.ok(results);

        } catch (Exception e){
            System.err.println("Błąd podczas pobierania urlopów: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("error", "Nie udało się pobrać urlopów");
            error.put("details", e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonList(new Object[]{error}));
        }
    }

    @PostMapping("/dodaj")
    public ResponseEntity<Void> findUrlop(@RequestParam Long id_Pracownika,
                                              @RequestParam LocalDate data_Od,
                                              @RequestParam LocalDate data_Do,
                                              @RequestParam String typ_Urlopu,
                                              @RequestParam String powod){
        try{
            service.addUrlopForPracownik(id_Pracownika,data_Od,data_Do,typ_Urlopu,powod);
            return  ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/dodajWszystkim")
    public ResponseEntity<String> dodajUrlopWszystkim(@RequestParam LocalDate dataOd,
                                                      @RequestParam LocalDate dataDo,
                                                      @RequestParam String typ,
                                                      @RequestParam String powod){
        try{
            int inserted = service.dodajUrlopDlaWszystkich(dataOd, dataDo, typ, powod);
            return ResponseEntity.ok("Dodano " + inserted + " urlopów");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd : " +e.getMessage());
        }
    }

    @PostMapping("/zaktualizujUrlop")
    public ResponseEntity<String> zaktualizujUrlop(@RequestParam Long id,
                                                    @RequestParam(required = false) Long idPracownika,
                                                    @RequestParam(required = false) LocalDate dataOd,
                                                    @RequestParam(required = false) LocalDate dataDo,
                                                    @RequestParam(required = false) String typUrlopu,
                                                    @RequestParam(required = false) String powod){
        try{
            service.zaktualizujUrlop(id, idPracownika, dataOd, dataDo, typUrlopu, powod);
            return ResponseEntity.ok("Zaktualizowano urlop");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Błąd " + e.getMessage());
        }

    }

    @PostMapping("/usunUrlop")
    public ResponseEntity<String> usunUrlop(@RequestParam Long id){
        try{
            service.usunUrlop(id);
            return ResponseEntity.ok().body("Usunieto urlop z id " + id);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }

    @GetMapping("/urlopyPracownika")
    public ResponseEntity<List<Object[]>> getUrlopyForPracownik(@RequestParam Long idPracownika) {
        try {
            List<Object[]> urlopy = service.findUrlopyFor(idPracownika);
            return ResponseEntity.ok(urlopy);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
