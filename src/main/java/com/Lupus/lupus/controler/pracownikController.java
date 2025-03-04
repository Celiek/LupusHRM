package com.Lupus.lupus.controler;

import com.Lupus.lupus.service.pracownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                                               @RequestParam MultipartFile zdjecie,
                                               @RequestParam LocalDate data,
                                               @RequestParam String login,
                                               @RequestParam String haslo){
        try{
            byte[] zdj = zdjecie.getBytes();
            service.addPracownik(imie, dimie, nazwisko, typ, zdj, data, login, haslo);
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
            //System.out.println("Parametr imie: " + imie); // Debugowanie
            List<Map<String, Object>> results = service.findUserByName(imie);
            //System.out.println("Wynik zapytania: " + results.size()); // Czy coś zwróciło?
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body((List<Map<String, Object>>) Collections.singletonMap("error", e.getMessage()));
        }
    }
    @PostMapping("/deleteByID")
    public ResponseEntity<String> deletePracownikById(@RequestParam Long pracownikID){
        try {
            service.deletePracownikById(pracownikID);
            return ResponseEntity.ok("Usunieto");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> updatePracownik(@RequestParam String imie,@RequestParam String dimie,
                                                  @RequestParam String nazwisko, @RequestParam String typPracownika,
                                                  @RequestParam MultipartFile zdjecie, @RequestParam LocalDate data,
                                                  @RequestParam String login,@RequestParam String haslo){
        try{
            byte[] zdj = zdjecie.getBytes();
            service.updatePracownik(imie, dimie, nazwisko, typPracownika, zdj, data, login, haslo);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error" +e.getMessage());
        }
    }

    @PostMapping("/deleteByNameAndSurname")
    public ResponseEntity<String> deletePracownikByNameAndSurname(@RequestParam String imie,
                                                                  @RequestParam String nazwisko){
        try{
            service.deletePracownikByNameAndSurname(imie, nazwisko);
            return ResponseEntity.ok("usunieto pracownika" + imie + " " + nazwisko);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }

}

