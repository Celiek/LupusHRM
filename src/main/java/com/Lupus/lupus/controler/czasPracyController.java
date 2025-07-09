package com.Lupus.lupus.controler;

import com.Lupus.lupus.DTO.CzasPracyDTO;
import com.Lupus.lupus.service.CzasPracyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/czasPracy")
@RequiredArgsConstructor
public class czasPracyController {
    public String error = "Wystapil blad: ";
    private final CzasPracyService service;


    @PostMapping("/updateCzasPracy")
    public ResponseEntity<String> updateStartPracy(@RequestParam Long idPracownika){
        try{
            service.updateStartPracy(idPracownika);
            return ResponseEntity.ok("Zaktualizowano start pracy");
        } catch (Exception e){
            return  ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    // start pracy dla wszystkich pracowników
    // nie uruchomi się nowy start pracy jeśli poprzedni nie został zakończony
    //
    @PostMapping("/startPracy")
    public ResponseEntity<String> insertStartDayForEmployees() {
        try {
            int inserted = service.insertStartDayForEmployees();
            if (inserted == 0) {
                return ResponseEntity.status(409).body("Nie zaczęto OGARNIJ SIĘ LUDZIE JUŻ ZAPIERDALajĄ!1!!!.");
            }
            return ResponseEntity.ok("Dodano nowe wpisy dla " + inserted + " pracowników.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd: " + e.getMessage());
        }
    }

    @PostMapping("/stopPracy")
    public ResponseEntity<String> stopPracyDlaPracownika(@RequestParam Long id){
        try
        {
            service.stopPracyDlaPracownika(id);
            return ResponseEntity.ok("Zapisano zakonczenie pracy dal pracownika od ID " + id);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Błąd zapisu stopu pracy " + e.getMessage());
        }
    }

    // start nalizcania pracy dl pojedynczego pracownika
    @PostMapping("/startPraca")
    public ResponseEntity<String> startPracyDlaPracownika(@RequestParam Long id,
                                                          @RequestParam LocalDate data,
                                                          @RequestParam LocalTime czas) {
        try {
            int rows = service.setStartPracyForEmployee(id, data, czas);
            if (rows == 0) {
                return ResponseEntity.status(409).body("️Pracownik " + id + " już zapierdala prosze jechać prosto.");
            }
            return ResponseEntity.ok("✅ Rozpoczęto czas pracy dla pracownika: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Błąd: " + e.getMessage());
        }
    }

    @GetMapping("/sumGodzinyPracyByIdForEmployee")
    public ResponseEntity<List<Map<String, Object>>> sumGodzinyPracyForEmployeeOnDate(@RequestParam Long idPracownika,
                                                                               @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataStart) {
        try {
            List<Map<String, Object>> result = service.sumGodzinyPracyForEmployeeOnDate(idPracownika, dataStart);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            CzasPracyDTO errorDTO = new CzasPracyDTO();
            Map<String, Object> errorMap =Map.of("error", "Wystąpił błąd: " + e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonList(errorMap));
        }
    }


    @GetMapping("/sumGodzinyPracyById")
    public ResponseEntity<List<Map<String, Object>>> sumGodzinyPracyForEmployeeBetweenDates(@RequestParam Long id_pracownik,
                                                                         @RequestParam LocalDate dataStart,
                                                                         @RequestParam LocalDate dataEnd){
        try{
                List<Map<String, Object>> result = service.sumGodzinyPracyForEmployeeBetweenDates(id_pracownik, dataStart, dataEnd);
                return ResponseEntity.ok(result);
        } catch (Exception e){
            CzasPracyDTO errorDTO = new CzasPracyDTO();
            Map<String, Object> errorMap =Map.of("error", "Wystąpił błąd: " + e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonList(errorMap));
        }
    }

    @GetMapping("/sumGodzinyPracy")
    public ResponseEntity<List<Object[]>> sumGodzinyPracy(@RequestParam LocalDate startDate,
                                                  @RequestParam LocalDate endDate){
        try {
            // Wywołanie usługi i pobranie wyników
            List<Object[]> results = service.sumGodzinyPracy(startDate, endDate);
            // Zwrócenie wyników
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            // Obsługa konkretnego wyjątku
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(new Object[]{"Błąd argumentów: " + e.getMessage()}));
        } catch (Exception e) {
            // Obsługa innych ogólnych wyjątków
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new Object[]{"Wystąpił błąd: " + e.getMessage()}));
        }
    }

    @PostMapping("/updateStartPracyEmploee")
    public ResponseEntity<String> updateStartPracy(@RequestParam Long idPracownika,
                                                   @RequestParam LocalDate dataPracy){
        try{
            service.updateStartPracy(idPracownika,dataPracy);
            return ResponseEntity.ok().body("Zaktualizowano ");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    @PostMapping("/startPracyPojedynczy")
    public ResponseEntity<String> startPracyPojedynczy(@RequestParam Long idPracownika) {
        try {
            service.updateStartPracy(idPracownika, LocalDate.now());
            return ResponseEntity.ok("Start pracy ustawiony dla ID: " + idPracownika);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd: " + e.getMessage());
        }
    }


    @GetMapping("/findCzasPracyByDate")
    public ResponseEntity<List<Object[]>> findCzasPracyByDate(@RequestParam LocalDate dataPracy){
        try{
            List<Object[]> result = service.findCzasPracyByDate(dataPracy);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            // Obsługa konkretnego wyjątku
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(new Object[]{"Błąd argumentów: " + e.getMessage()}));
        } catch (Exception e) {
            // Obsługa innych ogólnych wyjątków
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new Object[]{"Wystąpił błąd: " + e.getMessage()}));
        }
    }

    @GetMapping("/findCzasPracyByDateId")
    public ResponseEntity<List<Object[]>> findCzasPracyByDateId(@RequestParam LocalDate dataPracy){
        try{
            List<Object[]> result = service.findCzasPracyByDateId(dataPracy);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            // Obsługa konkretnego wyjątku
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(new Object[]{"Błąd argumentów: " + e.getMessage()}));
        } catch (Exception e) {
            // Obsługa innych ogólnych wyjątków
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(new Object[]{"Wystąpił błąd: " + e.getMessage()}));
        }
    }

    @PostMapping("/stopPracaWielu")
    public ResponseEntity<String> stopPracaDlaWielu(@RequestBody List<Long> ids) {
        try {
            service.stopPracaDlaPracownikow(ids);
            return ResponseEntity.ok("Zatrzymano pracę dla: " + ids.size() + " pracowników");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd zatrzymania pracy: " + e.getMessage());
        }
    }

    @GetMapping("/listDni")
    public ResponseEntity<List<Object[]>> findGodzinyPracyForPracownik(@RequestParam LocalDate dataOd,
                                                                       @RequestParam LocalDate dataDo){
        try{
            List<Object[]> result =  service.findGodzinyPracyDziennie(dataOd,dataDo);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/dniPracyZakres")
    public ResponseEntity<?> getDniPracyZakres(
            @RequestParam("idPracownika") Long idPracownika,
            @RequestParam("dataOd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataOd,
            @RequestParam("dataDo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDo) {
        try {
            System.out.printf("RECEIVED → id=%s, dataOd=%s, dataDo=%s%n", idPracownika, dataOd, dataDo);
            List<Object[]> result = service.getDniPracyZakres(idPracownika, dataOd, dataDo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Wystąpił błąd: " + e.getMessage());
        }
    }

    @PostMapping("/przerwa")
    public ResponseEntity<String> insertPrzerwa(@RequestParam String przerwa,
                                                @RequestParam LocalDate data){
        try{
            System.out.println("Czas przerwy : " +przerwa);
            service.insertPrzerwa(przerwa,data);
            return ResponseEntity.ok().body("Przerwa wstawiona");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error "+e.getMessage());
        }
    }

    @PutMapping("/updateCzasPracy")
    public ResponseEntity<Map<String, String>> updateCzasPracy(
            @RequestParam Long idPracownika,
            @RequestParam String dataPracy,
            @RequestParam(required = false) String startPracy,
            @RequestParam(required = false) String stopPracy,
            @RequestParam(required = false) String czasPrzerwy) {

        try {
            service.updateCzasPracy(idPracownika, dataPracy, startPracy, stopPracy, czasPrzerwy);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Czas pracy został zaktualizowany.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Wystąpił błąd podczas aktualizacji.");
            return ResponseEntity.status(500).body(response);
        }
    }

    //zwraca liste pracwonikwo kotrzy nie ropzoczeli rpacy
    @GetMapping("/listaNieRozpoczeliPracy")
    public ResponseEntity<List<Object[]>> getPracownicyNieRozpoczeliPracy(
            @RequestParam(name ="data")LocalDate data){
        try{
            List<Object[]> pracownicy = service.getPracownicyNieRozpoczeliPracyDnia(data);
            return ResponseEntity.ok(pracownicy);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
