package com.Lupus.lupus.controler;

import com.Lupus.lupus.service.miesiecznaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/miesieczna")
@RequiredArgsConstructor
public class wyplataMiesiecznaController {
    private final miesiecznaService service;

    @PostMapping("/monthlyPayments")
    public ResponseEntity<String> insertMonthlyPayment(@RequestParam Double kwotaMiesieczna,@RequestParam Double kwotaZaliczek,@RequestParam LocalDate dataWyplaty){
        try {
            service.insertMonthlyPayment(kwotaMiesieczna, kwotaZaliczek, dataWyplaty);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred while inserting the monthly payment: " + e.getMessage());
        }
    }

    @PostMapping("/monthlyPayment")
    public ResponseEntity<String> inserMonthlyPayments(@RequestParam Long idPracownika,
                                                       @RequestParam Double sumaZaliczek,
                                                       @RequestParam LocalDate dataWyplaty,
                                                       @RequestParam Double kwotaMiesieczna){
        try{
            service.inserMonthlyPayments(idPracownika,sumaZaliczek,dataWyplaty,kwotaMiesieczna);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/updateMonthlyPayment")
    public ResponseEntity<String> updateMontlyPayment(@RequestParam Double kwota,
                                                      @RequestParam Long idPracownika,
                                                      @RequestParam Double zaliczka){
        try {
            service.updateMontlyPayment(kwota, idPracownika, zaliczka);
            return ResponseEntity.ok("ok");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/updateMonthlyPayments")
    public ResponseEntity<String> updateMonthlyPayments(@RequestParam Double nowaKwota,
                                                        @RequestParam Double nowaZaliczka,
                                                        @RequestParam LocalDate nowaData){
        try{
            service.updateMonthlyPayments(nowaKwota,nowaZaliczka,nowaData);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/deleteMonthlypaymentById")
    public ResponseEntity<String> deleteMonthlypaymentById(@RequestParam Long idPracownika,
                                                           @RequestParam LocalDate dataWyplaty){
        try{
            service.deleteMonthlypaymentById(idPracownika,dataWyplaty);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/deleteMonthlypaymentsByDate")
    public ResponseEntity<String> deleteMonthlypaymentsByDate(@RequestParam LocalDate dataWyplaty){
        try{
            service.deleteMonthlypaymentsByDate(dataWyplaty);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
