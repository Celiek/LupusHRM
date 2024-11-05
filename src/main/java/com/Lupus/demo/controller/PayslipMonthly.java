package com.Lupus.demo.controller;

import com.Lupus.demo.services.PayslipMonthlyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/payslipMonthly")
@RequiredArgsConstructor
public class PayslipMonthly {
    private String error = "Wystąpił błąd: ";
    @Autowired
    private final PayslipMonthlyService monthlyService;

    @Transactional
    public ResponseEntity<String> addMonthlyPayslipForEmployee(Long idPracownika, Double kwota){
        try{
            monthlyService.addMonthlyPayslipForEmployee(idPracownika,kwota);
            return ResponseEntity.ok().body("Wypłąta dodana");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e);
        }
    }

    public ResponseEntity<String> addMonthlyPayslips(Double kwota){
        try{
            monthlyService.addMonthlyPayslips(kwota);
            return ResponseEntity.ok().body("pomyślnie dodano wypłątę dla użytkoników w wysokości: " + kwota);
        } catch (Exception e){
            return ResponseEntity.ok().body(error + e);
        }
    }

    public ResponseEntity<String> updateMonthlyPayslipForEmployee(Long idPracownika, Double kwota, Date dataWyplaty){
        try{
            monthlyService.updateMonthlyPayslipForEmployee(idPracownika, kwota, dataWyplaty);
            return ResponseEntity.ok().body("Pomyślnie dodano wypłatę dla pracownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e);
        }
    }

    public ResponseEntity<String> updateMonthlyPayslipsForEmployees(Double kwota, Date dataWyplaty){
        try{
            monthlyService.updateMonthlyPayslipsForEmployees(kwota,dataWyplaty);
            return ResponseEntity.ok().body("Poprawnie poprawiono datę");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e);
        }
    }

    public ResponseEntity<String> deleteMonthlyPayslipForEmployeeId(Long idPracownika, Date dataWyplaty){
        try{
            monthlyService.deleteMonthlyPayslipForEmployeeId(idPracownika, dataWyplaty);
            return ResponseEntity.ok().body("Pomyślnie usunięto wypłatę pracownikowi");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e);
        }
    }

    public ResponseEntity<String> deleteMonthlyPayslipForEmployees(Date dataWyplaty){
        try{
            monthlyService.deleteMonthlyPayslipForEmployees(dataWyplaty);
            return ResponseEntity.ok().body("Pomyślnie usunięto wypłatę pracownikom");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e);
        }
    }
}
