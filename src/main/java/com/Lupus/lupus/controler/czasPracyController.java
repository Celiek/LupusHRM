package com.Lupus.lupus.controler;

import com.Lupus.lupus.DTO.CzasPracyDTO;
import com.Lupus.lupus.service.CzasPracyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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


    @PostMapping("/update")
    public ResponseEntity<String> updateStartPracy(@RequestParam Long idPracownika){
        try{
            service.updateStartPracy(idPracownika);
            return ResponseEntity.ok("Zaktualizowano start pracy");
        } catch (Exception e){
            return  ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/startPracy")
    public ResponseEntity<String> insertStartDayForEmployees(){
        try{
            service.insertStartDayForEmployees();
            return ResponseEntity.ok("Rozpoczeto czas pracy!");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
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
    public ResponseEntity<String> sumGodzinyPracyForEmployeeBetweenDates(@RequestParam Long id_pracownik,
                                                                         @RequestParam LocalDate dataStart,
                                                                         @RequestParam LocalDate dataEnd){
        try{
                service.sumGodzinyPracyForEmployeeBetweenDates(id_pracownik,dataStart, dataEnd);
                return ResponseEntity.ok("Zsumowane godziny pracy dla pracownika:");
        } catch (Exception e){
                return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    @GetMapping("/sumGodzinyPracy")
    public ResponseEntity<String> sumGodzinyPracy(@RequestParam LocalDate startDate,
                                                  @RequestParam LocalDate endDate){
        try {
            service.sumGodzinyPracy(startDate, endDate);
            return ResponseEntity.ok("Zsumowanie godziny pracy dla pracownikow");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
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
}
