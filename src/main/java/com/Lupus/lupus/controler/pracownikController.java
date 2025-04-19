package com.Lupus.lupus.controler;

import com.Lupus.lupus.DTO.PracownikDto;
import com.Lupus.lupus.service.pracownikService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/pracownik")
@RequiredArgsConstructor
public class pracownikController {
    private final pracownikService service;

    @PostMapping("/addPracownik")
    public ResponseEntity<?> addPracownik(
            @RequestParam String imie,
            @RequestParam(required = false, defaultValue = "") String dimie,
            @RequestParam String nazwisko,
            @RequestParam String typ,
            @RequestParam MultipartFile zdjecie,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam String login,
            @RequestParam String haslo) {
        try {
            // Walidacja pliku (zdjęcia)
            if (zdjecie.isEmpty()) {
                return ResponseEntity.badRequest().body("Zdjęcie jest wymagane.");
            }

            byte[] zdj = zdjecie.getBytes();
            // Wywołanie serwisu
            service.addPracownik(imie, dimie, nazwisko, typ, zdj, data, login, haslo);

            return ResponseEntity.ok("Pracownik został dodany pomyślnie.");
        } catch (Exception e) {
            // Logowanie błędu (przykładowo)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Wystąpił błąd podczas dodawania pracownika: " + e.getMessage());
        }
    }


    @GetMapping("/listall")
    public ResponseEntity<?> findAllUsers() {
        try {
            List<PracownikDto> results = service.findAllUsers();  // Pobierz dane z serwisu
            return ResponseEntity.ok(results);  // Zwróć odpowiedź z danymi;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("details", "Wystąpił błąd podczas przetwarzania żądania");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/userById")
    public ResponseEntity<List<Map<String, Object>>> findUserById(@RequestParam Long idPracownik) {
        try {
            List<Map<String, Object>> results = service.findUserById(idPracownik);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body((List<Map<String, Object>>) Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/userByName")
    public ResponseEntity<List<Map<String, Object>>> findUserByName(@RequestParam String imie) {
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
    public ResponseEntity<String> deletePracownikById(@RequestParam Long pracownikID) {
        try {
            service.deletePracownikById(pracownikID);
            return ResponseEntity.ok("Usunieto");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> updatePracownik(@RequestParam String imie, @RequestParam String dimie,
                                                  @RequestParam String nazwisko, @RequestParam String typPracownika,
                                                  @RequestParam MultipartFile zdjecie, @RequestParam LocalDate data,
                                                  @RequestParam String login, @RequestParam String haslo,
                                                  @RequestParam Long idPracownika) {
        try {
            byte[] zdj = zdjecie.getBytes();
            service.updatePracownik(idPracownika, imie, dimie, nazwisko, typPracownika, zdj, data, login, haslo);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error" + e.getMessage());
        }
    }

    @PostMapping("/deleteByNameAndSurname")
    public ResponseEntity<String> deletePracownikByNameAndSurname(@RequestParam String imie,
                                                                  @RequestParam String nazwisko) {
        try {
            service.deletePracownikByNameAndSurname(imie, nazwisko);
            return ResponseEntity.ok("usunieto pracownika" + imie + " " + nazwisko);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }

    @PostMapping("/updatePracownik")
    public ResponseEntity<String> updatePracownik(@RequestParam Long idPracownika,
                                                  @RequestParam String imie,
                                                  @RequestParam String dimie,
                                                  @RequestParam String nazwisko,
                                                  @RequestParam String typPracownika,
                                                  @RequestParam byte[] zdjecie,
                                                  @RequestParam LocalDate data,
                                                  @RequestParam String login,
                                                  @RequestParam String haslo) {
        try {
            service.updatePracownik(idPracownika, imie, dimie, nazwisko, typPracownika, zdjecie, data, login, haslo);
            return ResponseEntity.ok("Pracownik został zaktualizowany pomyślnie.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas aktualizacji: " + e.getMessage());
        }
    }

    //wyswietla ilosc pracowników którzy rozpoczęli pracę dzisiaj
    @GetMapping("/nowiDzis")
    public ResponseEntity<?> getEmployeesStartedToday() {
        try {
            long count = service.countEmployeesStartedToday();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            // Logujemy błąd (opcjonalnie)
            e.printStackTrace();

            // Zwracamy status 500 + wiadomość
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Wystąpił błąd podczas zliczania nowych pracowników: " + e.getMessage());
        }
    }

    //zwraca godzinę startu pracy
    @GetMapping("/pierwszyStartDzisiaj")
    public ResponseEntity<?> getFirstStartTimeToday() {
        try {
            String godzina = service.getFirstStartTimeToday();
            if (godzina == null) {
                return ResponseEntity.ok("Brak pracowników, którzy dziś rozpoczęli pracę.");
            }
            return ResponseEntity.ok(godzina);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd: " + e.getMessage());
        }
    }

    //zwraca czas przerw w minutach
    @GetMapping("/sumaPrzerwDzisiaj")
    public ResponseEntity<Double> getTodayBreakTimeOnlyNumber() {
        try {
            double hours = service.getTotalBreakTimeTodayInHours();
            return ResponseEntity.ok(hours);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //zwraca ilość godzin przecowanych dzisiaj
    @GetMapping("/czasPracyDzisiaj")
    public ResponseEntity<Double> getCzasPracyDlaAdama() {
        try {
            Double czas = service.getCzasPracy();
            return czas != null ? ResponseEntity.ok(czas) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
