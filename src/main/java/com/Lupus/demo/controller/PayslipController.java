package com.Lupus.demo.controller;

import com.Lupus.demo.services.PayslipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {
    public String blad = "Wystapił błąd: ";

    private final PayslipService payslipService;

    @PostMapping("/weekly")
    public ResponseEntity<String> insertWeeklyPayment(
            @RequestParam Long idPracownika,
            @RequestParam Double kwota,
            @RequestParam Double zaliczka) {
        try {
            payslipService.insertWeeklyPayment(idPracownika, kwota, zaliczka);
            return ResponseEntity.ok("Wypłata tygodniowa została dodana.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd: " + e.getMessage());
        }
    }
    @PostMapping("/monthly")
    public ResponseEntity<String> insertWeeklyPayments(Double kwota, Double zaliczka){
        try{
            payslipService.insertWeeklyPayments(kwota,zaliczka);
            return ResponseEntity.ok("Wypłąty tygodniowe zostały dodane.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd: " + e.getMessage());
        }
    }
    @PostMapping("/weekly/updates")
    public ResponseEntity<String> insertMonthlyPayments(Double kwotaMiesieczna){
        try {
            payslipService.insertMonthlyPayments(kwotaMiesieczna);
            return ResponseEntity.ok("Wypłaty miesięczne dla wszystkich pracowników zostały dodane.");
        } catch (Exception e){
            return  ResponseEntity.status(500).body("Wystąpił błąd: " + e.getMessage());
        }
    }
    @PostMapping("/weekly/update")
    public ResponseEntity<String> updateWeeklyPayments(Double nowaKwota, Double nowaZaliczka){
        try{
            payslipService.updateWeeklyPayments(nowaKwota, nowaZaliczka);
            return ResponseEntity.ok("Wypłaty tygodniowe pracowników zostały zaktualizowane.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd: " + e.getMessage());
        }
    }
    @PostMapping("/monthly/updates")
    public ResponseEntity<String> updateMonthlyPayments(Double nowaKwotaMiesięczna){
        try{
            payslipService.updateMonthlyPayments(nowaKwotaMiesięczna);
            return ResponseEntity.ok("Wypłaty miesięczne pracowników zostały zaktualizowane.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Wystąpił błąd: "+e.getMessage());
        }
    }
    @PostMapping("/weekly/{idPracownika}/}")
    public ResponseEntity<String> updateWeeklyPayment(Double nowaKwota,Double nowaZaliczka, Long idPracownika){
        try {
            payslipService.updateWeeklyPayment(nowaKwota, nowaZaliczka, idPracownika);
            return ResponseEntity.ok("Wypłata tygodniowa dla pracownika została zaktualizowana.");
        } catch (Exception e){
            return ResponseEntity.status(500).body(blad+ e.getMessage());
        }
    }
    @PostMapping("/monthly/{idPracownika/update}")
    public ResponseEntity<String> updateMonthlyPayment(Double nowaKwotaMiesiecza, Long idPracownika){
        try{
            payslipService.updateMonthlyPayment(nowaKwotaMiesiecza,idPracownika);
            return ResponseEntity.ok("Wypłata tygodniowa dla pracownika zostałą zaktualizowana.");
        } catch (Exception e){
            return ResponseEntity.status(500).body(blad +e.getMessage());
        }
    }
    @PostMapping("/monthly")
    public ResponseEntity<List<Object[]>> findMonthlyPaymentsWithDetails(){
        try {
            List<Object[]> payments = payslipService.findMonthlyPaymentsWithDetails();
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/monthly/{idPracownika}")
    public ResponseEntity<List<Object[]>> findMonthlyPaymentsWithDetailsByEmployeeId(Long idPracownika){
        try{
            List<Object[]> payments = payslipService.findMonthlyPaymentsWithDetailsByEmployeeId(idPracownika);
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/weekly/{idPracownika}")
    public ResponseEntity<List<Object[]>> findWeeklyPaymentsByEmployeeId(Long idPracownik){
        try{
            List<Object[]> payments = payslipService.findWeeklyPaymentsByEmployeeId(idPracownik);
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/weekly")
    public ResponseEntity<List<Object[]>> findWeeklyPaymentsForAllEmployees(){
        try {
            List<Object[]> payments = payslipService.findWeeklyPaymentsForAllEmployees();
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/weekly/date")
    public ResponseEntity<List<Object[]>> findPaymentsByDate(LocalDate dataWyplaty){
        try{
            List<Object[]> payments = payslipService.findPaymentsByDate(dataWyplaty);
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/payments")
    public ResponseEntity<List<Object[]>> findPaymentsByDateAndEmployeeId(LocalDate dataWyplaty, Long idPracownika){
        try{
            List<Object[]> payments = payslipService.findPaymentsByDateAndEmployeeId(dataWyplaty, idPracownika);
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/payments")
    public ResponseEntity<List<Object[]>> findWeeklyPaymentsByEmployeId(Long idPracownika){
        try{
            List<Object[]> payments = payslipService.findWeeklyPaymentsByEmployeId(idPracownika);
            return ResponseEntity.ok(payments);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

}
