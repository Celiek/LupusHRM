package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.CzasPracyPerPersonDTO;
import com.pracownikService.demo.service.CzasPracyService;
import com.pracownikService.exception.PracownikException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/czasPracy")
@RequiredArgsConstructor
public class CzasPracyController {
    @Autowired
    private final CzasPracyService czasPracyService;

    @PostMapping("/startPracyForPracownik")
    public ResponseEntity<String> startCzasPracyForPracownik(Long idPracownik){

        try{
            czasPracyService.startCzasPracyForPracownik(idPracownik);
            return ResponseEntity.ok("Rozpoczęto czas Pracy dla pracownika " + idPracownik);

        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystapil blad" + e.getMessage());
        }
    }

    @PostMapping("/startPracyForPracownicy")
    public ResponseEntity<String> startCzasPracyForPracownicy(@RequestParam List<Long> ids){
        try{
            czasPracyService.startCzasPracyForPracownicy(ids);
            return ResponseEntity.ok("Rozpoczęto czas pracy dla pracowników");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd" + e.getMessage());
        }
    }

    @PostMapping("/stopPracyForPracownik")
    public ResponseEntity<String> stopPracyDlaPracownika(Long idPracownika){
        try{
            czasPracyService.stopPracyDlaPracownika(idPracownika);
            return ResponseEntity.ok("Rozpoczęto czas pracy dla pracownika + ");
        } catch (Exception e){
            return  ResponseEntity.status(500).body("Wystąpił błąd "+e.getMessage());
        }
    }

    @PostMapping("/stopPracyForPracownicy")
    public ResponseEntity<String> setStopPracyForPracownicy(@RequestParam List<Long> idPracownikow){
        try{
            czasPracyService.setStopPracyForPracownicy(idPracownikow);
            return ResponseEntity.ok("Zakonczono czas pracy dla pracownikow");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd " +e.getMessage());
        }
    }

    @PostMapping("/updateStartPracyForPracownik")
    public ResponseEntity<String> updateStartPracyForPracownik(@RequestParam Long id,
                                                               @RequestParam LocalDate dataPracy,
                                                               @RequestParam LocalTime startPracy){
        try{
            czasPracyService.updateStartPracyForPracownik(id,dataPracy,startPracy);
            return ResponseEntity.ok("Zaktualizowano start pracy dla pracownika "+ id +" w dzien: "+ dataPracy + " o godzinie " + startPracy);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd "+e.getMessage());
        }
    }

    @PostMapping("/updateStartPracyForPracownicy")
    public ResponseEntity<String> updateStartPracyForPracownicy(@RequestParam List<Long> id,
                                                                @RequestParam LocalDate dataPracy,
                                                                @RequestParam LocalTime startPracy){
        try{
            czasPracyService.updateStartPracyForPracownicy(id,dataPracy,startPracy);
            return ResponseEntity.ok("Zaktualizowano start pracy dla pracownikow");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd "+e.getMessage());
        }
    }

    @PostMapping("/updateStopPracyForPracownik")
    public ResponseEntity<String> updateStopPracyForPracownik(@RequestParam Long idPracownika,
                                                              @RequestParam LocalDate dataPracy,
                                                              @RequestParam LocalTime stop){
        try {
            czasPracyService.updateStopPracyForPracownik(idPracownika,dataPracy,stop);
            return ResponseEntity.ok("Zaktualizowano stop pracy dla pracownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd :" + e.getMessage());
        }
    }

    @PostMapping("/updateStopPracyForPracownicy")
    public ResponseEntity<String> updateStopPracyForPracownicy(@RequestParam List<Long> idsPracownikow,
                                                               @RequestParam LocalDate dataPracy,
                                                               @RequestParam LocalTime stop){
        try {
            czasPracyService.updateStopPracyForPracownicy(idsPracownikow, dataPracy, stop);
            return ResponseEntity.ok().body("Zaktualizowano stop pracy dla pracowników");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd "+e.getMessage());
        }
    }

    @GetMapping("/sumGodzinyPracyForPracownik")
    public ResponseEntity<?> sumGodzinyPracyForPracownik(Long idPracownika,
                                                              LocalDate start,
                                                              LocalDate stop){
        try{
            Double wynik = czasPracyService.sumGodzinyPracyForPracownik(idPracownika,start,stop);
            return ResponseEntity.ok(wynik);
        } catch (PracownikException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CzasPracyPerPersonDTO>> getCzasPracyBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate stop){

        return ResponseEntity.ok(czasPracyService.getCzasPracyDTOBetweenDates(start,stop));

    }
}
