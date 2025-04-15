package com.Lupus.lupus.controler;

import com.Lupus.lupus.DTO.WyplataRequest;
import com.Lupus.lupus.DTO.WyplataTygodniowaDTO;
import com.Lupus.lupus.service.tygodniowaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api/wyplataTyg")
@RequiredArgsConstructor
public class wyplataTygodniowaController {

    private final tygodniowaService service;

    @PostMapping("/insertWeeklyPaychek")
    public ResponseEntity<String> insertWeeklyPaycheck(@RequestBody WyplataRequest request) {
        try {
            service.insertWeeklyPaycheck(
                    request.getIdPracownika(),
                    request.getKwota(),
                    request.getZaliczka(),
                    request.getData_wyplaty()
            );
            return ResponseEntity.ok("dodano wyplate pracownikowi");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }


    @PostMapping("/insertweeklyPaychecks")
    public ResponseEntity<String> insertWyplatyDlaWszystkich(@RequestBody WyplataTygodniowaDTO dto) {
        try {
            service.insertWeeklyPaychecks(dto.getKwota(), dto.getZaliczka(), dto.getData_wyplaty_tygodniowej());
            return ResponseEntity.ok("Wypłaty tygodniowe zostały dodane dla wszystkich pracowników.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Błąd podczas dodawania wypłat: " + e.getMessage());
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

    @PostMapping("/updateWeeklyPaycheks")
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

    @GetMapping("/getWeeklyPaychek")
    public ResponseEntity<String> getweeklyPaychekEmployee(@RequestParam String imie,
                                                           @RequestParam String nazwisko,
                                                           @RequestParam Date data){
        try{
            service.getweeklyPaychekEmployee(imie,nazwisko,data);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return  ResponseEntity.status(500).body("Error " +e.getMessage());
        }
    }

    @GetMapping("/getWeeklyPaycheks")
    public ResponseEntity<String> getWeeklyPaycheks(){

        try{
            service.getWeeklyPaycheks();
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error " +e.getMessage());
        }
    }

//    @GetMapping("/getWeeklyPaychek")
//    public ResponseEntity<String> getWeeklyPaychek(@RequestParam String imie,
//                                                   @RequestParam String nazwisko){
//        try{
//            service.getWeeklyPaychek(imie, nazwisko);
//            return ResponseEntity.ok("ok");
//        } catch (Exception e){
//            return ResponseEntity.status(500).body("Error " + e.getMessage());
//        }
//    }

    @GetMapping("/getMonthlyPaycheks")
    public ResponseEntity<String> getMonthlyPaycheks(@RequestParam Date start,
                                                     @RequestParam Date stop){
        try{
            service.getMonthlyPaycheks(start,stop);
            return ResponseEntity.ok("ok");
        } catch(Exception e){
            return  ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }

    @GetMapping("/getMonthlyPaycheck")
    public ResponseEntity<String> getMonthlyPaycheck(@RequestParam Date start,
                                                     @RequestParam Date stop,
                                                     @RequestParam String imie,
                                                     @RequestParam String dimie,
                                                     @RequestParam String nazwisko){
        try{
            service.getMonthlyPaycheck(start, stop, imie, dimie, nazwisko);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error " +e.getMessage());
        }
    }

}
