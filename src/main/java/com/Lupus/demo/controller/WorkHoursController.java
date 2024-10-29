package com.Lupus.demo.controller;

import com.Lupus.demo.services.WorkHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/workHours")
@RequiredArgsConstructor
public class WorkHoursController {
    private String error = "Wystąpił błąd: ";
    @Autowired
    private final WorkHoursService workHoursService;

    @GetMapping("/dailyByPracownik")
    public ResponseEntity<List<Object[]>> getWorkHoursDaily(Long idPracownika){
        try{

            return ResponseEntity.ok().body(workHoursService.getWorkHoursDaily(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
    @GetMapping("/weekly")
    public ResponseEntity<List<Object[]>> getWorkHoursWeekly(){
        try{
            return ResponseEntity.ok().body(workHoursService.getWorkHoursWeekly());
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
    @PostMapping("/startDay/update")
    public ResponseEntity<String> insertWorkHoursForAllEmployees(){
        try{
            return ResponseEntity.ok().body("Rozpoczęto dzień pracy");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    @GetMapping("/workhours/byEmployee")
    public ResponseEntity<List<Object[]>> findWorkHoursAndPaymentsByEmployeeId(Long idPracownika){
        try{
            return ResponseEntity.ok().body(workHoursService.findWorkHoursAndPaymentsByEmployeeId(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }
    @PostMapping("/startDay")
    public ResponseEntity<String> startWorkForAllEmployees(){
        try{
             workHoursService.startWorkForAllEmployees();
             return ResponseEntity.ok().body("Rozpoczęto dzień pracy, miłego dnia.");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/breakStart")
    public ResponseEntity<String> breakTimeForAllEmployees(){
        try {
            workHoursService.breakTimeForAllEmployees();
            return ResponseEntity.ok().body("Rozpoczęto przerwę, pamiętaj żeby ją zakończyć!");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    //dodać info o długości przerw
    @PostMapping("/breakStop")
    public ResponseEntity<String> endOfBreakForAllEmplyees(){
        try {
            workHoursService.endOfBreakForAllEmplyees();
            return ResponseEntity.ok().body("Zakończono przerwę");
        } catch(Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/startDay")
    public ResponseEntity<String> updateStartTimeForEmployee(@RequestParam LocalTime czas,
                                                             @RequestParam Long idPracownika){
        try{
            workHoursService.updateStartTimeForEmployee(czas, idPracownika);
            return ResponseEntity.ok().body("czas pracy dla pracownika zaktualizowano");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @PostMapping("/startDay/update")
    public ResponseEntity<String> updateStartTimeFOrEmployees(@RequestParam LocalTime time){
        try{
            workHoursService.updateStartTimeFOrEmployees(time);
            return ResponseEntity.ok().body("Godzina rozpoczecia pracy zostałą zaktualizowana!");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @GetMapping("/worHours/byEmployee")
    public ResponseEntity<List<Object[]>> findWorkHoursByEmployeeId(@RequestParam Long idPracownika){
        try{
            return ResponseEntity.ok().body( workHoursService.findWorkHoursByEmployeeId(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body( new ArrayList<>());
        }
    }
    @GetMapping("/workHours/byEmployee/recent")
    public ResponseEntity<List<Object[]>> findWorkHoursByEmployeeIdAndRecent(@RequestParam Long idPracownika){
        try{
            return ResponseEntity.ok().body( workHoursService.findWorkHoursByEmployeeIdAndRecent(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

}
