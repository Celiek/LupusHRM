package com.Lupus.lupus.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Lupus.lupus.service.tygodniowaService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/api/wyplataTyg")
@RequiredArgsConstructor
public class wyplataTygodniowaController {

    private final tygodniowaService service;

    @PostMapping("/weekyPaychek")
    public ResponseEntity<String> insertWeeklyPaycheck(@RequestParam Long idPracownika,
                                                       @RequestParam Double kwota,
                                                       @RequestParam Double zaliczka,
                                                       @RequestParam Date data_wyplaty){
        try {

            service.insertWeeklyPaycheck(idPracownika, kwota, zaliczka, data_wyplaty);
            return  ResponseEntity.ok("dodano wyplate pracownikowi");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error "+ e.getMessage());
        }
    }

    @PostMapping("/weeklyPaycheks")
    public ResponseEntity<String> insertWeeklyPaychecks(@RequestParam Double kwota,
                                                        @RequestParam Double zaliczka,
                                                        @RequestParam Date dataWyplaty){
        try{
            service.insertWeeklyPaychecks(kwota, zaliczka, dataWyplaty);
            return ResponseEntity.ok("dodano wyplaty tygodniowe");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }

    @PostMapping("/updatePaychek")
    public ResponseEntity<String> updateWeeklyPaycheckForEmployee(@RequestParam Long idPracownika,
                                                                  @RequestParam Double kwota,
                                                                  @RequestParam Double zaliczka,
                                                                  @RequestParam Date dataWyplaty){
        try{
            service.updateWeeklyPaycheckForEmployee(idPracownika, kwota, zaliczka, dataWyplaty);
            return ResponseEntity.ok("zaktualizowane wyplate dla pracownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " +e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> updateWeeklyPaycheckForEmployees(@RequestParam Double kwota,
                                                                   @RequestParam Double zaliczka,
                                                                   @RequestParam Date data){
        try{
            service.updateWeeklyPaycheckForEmployees(kwota, zaliczka, data);
            return ResponseEntity.ok("Zaktualizowano wyplaty dla pracownikow");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " +e.getMessage());
        }
    }
}
