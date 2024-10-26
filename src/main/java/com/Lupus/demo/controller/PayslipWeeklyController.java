package com.Lupus.demo.controller;

import com.Lupus.demo.services.PayslipWeeklyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/payslipWeekly")
@RequiredArgsConstructor
public class PayslipWeeklyController {
    private String error = "Wystapił błąd: ";

    private final PayslipWeeklyService payslipWeeklyService;

    @PostMapping("/payslip")
    public ResponseEntity<String> addWeeklyPayslip(
            @RequestParam Long idPraconwika,
            @RequestParam Double kwota,
            @RequestParam Double zaliczka){
    try {
        payslipWeeklyService.addWeeklyPayslip(idPraconwika, kwota, zaliczka);
        return ResponseEntity.ok("Wypłata tygodniowa dla pracowników dodana");
    } catch (Exception e){
        return ResponseEntity.status(500).body(error + e.getMessage());
    }
    }
    @PostMapping("/payslipForEmployee")
    public ResponseEntity<String> updateWeeklyPayslipForEmployee(
            @RequestParam Long idPracownika,
            @RequestParam Double kwota,
            @RequestParam Date data,
            @RequestParam Double zaliczka){
        try{
            payslipWeeklyService.updateWeeklyPayslipForEmployee(idPracownika, kwota, data, zaliczka);
            return ResponseEntity.ok("Wypłąta tygodniowa dla pracowników zaktualizowana");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/payslip/forEmployees")
    public ResponseEntity<String> updateWeeklyPayslipForEmployees(
            @RequestParam Double kwota,
            @RequestParam Date data){
        try{
            payslipWeeklyService.updateWeeklyPayslipForEmployees(kwota, data);
            return ResponseEntity.ok("Wypłata tygodniowa dla pracowników zaktualizowana");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Błąd: "+e.getMessage());
        }
    }
    @PostMapping("/payslip/remove")
    public ResponseEntity<String> removeWeeklyPayslip(
            @RequestParam Date data){
        try{
            payslipWeeklyService.removeWeeklyPayslip(data);
            return ResponseEntity.ok("Wypłata tygodniowa dla pracownika usunięta");
        }catch(Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/payslip/forUser")
    public ResponseEntity<String> addWeeklyPayslipForUser(
            @RequestParam Long idPracownika,
            @RequestParam Double kwota,
            @RequestParam Date dataWyplaty,
            @RequestParam Double zaliczka
    ){
        try{
            payslipWeeklyService.addWeeklyPayslipForUser(idPracownika, kwota, dataWyplaty, zaliczka);
            return ResponseEntity.ok("Dodano wypłatę weekendową dla pracownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
}
