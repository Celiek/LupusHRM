package com.Lupus.lupus.controler;

import com.Lupus.lupus.service.pracownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/api/pracownik")
@RequiredArgsConstructor
public class pracownikController {
    private final pracownikService service;

    @PostMapping("/addPracownik")
    public ResponseEntity<String> addPracownik(@RequestParam String imie,
                                               @RequestParam String dimie,
                                               @RequestParam String nazwisko,
                                               @RequestParam String typ,
                                               @RequestParam Byte[] zdjecie,
                                               @RequestParam LocalDate data,
                                               @RequestParam String login,
                                               @RequestParam String haslo){
        try{
            service.addPracownik(imie, dimie, nazwisko, typ, zdjecie, data, login, haslo);
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " +e.getMessage());
        }
    }

    @GetMapping("/listall")
    public ResponseEntity<List<Map<String, Object>>> findAllUsers() {
        try {
            List<Map<String, Object>> results = service.findAllUsers();  // Pobierz dane z serwisu
            return ResponseEntity.ok(results);  // Zwróć odpowiedź z danymi;
        } catch (Exception e){
            return ResponseEntity.status(500).body((List<Map<String, Object>>) Collections.singletonMap("error", e.getMessage()));        }
    }

    @GetMapping("/userById")
    public ResponseEntity<List<Map<String,Object>>> findUserById(@RequestParam Long idPracownik){
        try{
            List<Map<String,Object>> results = service.findUserById(idPracownik);
            return ResponseEntity.ok(results);
        } catch(Exception e){
            return ResponseEntity.status(500).body((List<Map<String, Object>>) Collections.singletonMap("error", e.getMessage()));        }
        }
    @GetMapping("/userByName")
    public ResponseEntity<List<Map<String,Object>>> findUserByName(@RequestParam String imie) {
        try {
            List<Map<String, Object>> results = service.findUserByName(imie);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body((List<Map<String, Object>>) Collections.singletonMap("error", e.getMessage()));
        }
    }
    @PostMapping("/deleteByID")
    public ResponseEntity<String> deletePracownikById(@RequestParam Long pracownikID){
        try {
            service.deletePracownikById(pracownikID);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> updatePracownik(@RequestParam String imie,@RequestParam String dimie,
                                                  @RequestParam String nazwisko, @RequestParam String typPracownika,
                                                  @RequestParam Byte[] zdjecie, @RequestParam LocalDate data,
                                                  @RequestParam String login,@RequestParam String haslo){
        try{
            service.updatePracownik(imie, dimie, nazwisko, typPracownika, zdjecie, data, login, haslo);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error" +e.getMessage());
        }
    }

}

