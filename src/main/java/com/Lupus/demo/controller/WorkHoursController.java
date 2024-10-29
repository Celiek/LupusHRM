package com.Lupus.demo.controller;

import com.Lupus.demo.repository.WorkHoursInterface;
import com.Lupus.demo.services.WorkHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity<List<Object[]>> getWorkHoursDaily(Long idPracownika){
        try{

            return ResponseEntity.ok().body(workHoursService.getWorkHoursDaily(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    public ResponseEntity<List<Object[]>> getWorkHoursWeekly(){
        try{
            return ResponseEntity.ok().body(workHoursService.getWorkHoursWeekly());
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    public ResponseEntity<String> insertWorkHoursForAllEmployees(){
        try{
            return ResponseEntity.ok().body("Rozpoczęto dzień pracy");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<List<Object[]>> findWorkHoursAndPaymentsByEmployeeId(Long idPracownika){
        try{
            return ResponseEntity.ok().body(workHoursService.findWorkHoursAndPaymentsByEmployeeId(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    public ResponseEntity<String> startWorkForAllEmployees(){
        try{
             workHoursService.startWorkForAllEmployees();
             return ResponseEntity.ok().body("Rozpoczęto dzień pracy, miłego dnia.");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> breakTimeForAllEmployees(){
        try {
            workHoursService.breakTimeForAllEmployees();
            return ResponseEntity.ok().body("Rozpoczęto przerwę, pamiętaj żeby ją zakończyć!");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    //dodać info o długości przerw
    public ResponseEntity<String> endOfBreakForAllEmplyees(){
        try {
            workHoursService.endOfBreakForAllEmplyees();
            return ResponseEntity.ok().body("Zakończono przerwę");
        } catch(Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> updateStartTimeForEmployee(@RequestParam LocalTime czas,
                                                             @RequestParam Long idPracownika){
        try{
            workHoursService.updateStartTimeForEmployee(czas, idPracownika);
            return ResponseEntity.ok().body("czas pracy dla pracownika zaktualizowano");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> updateStartTimeFOrEmployees(@RequestParam LocalTime time){
        try{
            workHoursService.updateStartTimeFOrEmployees(time);
            return ResponseEntity.ok().body("Godzina rozpoczecia pracy zostałą zaktualizowana!");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<List<Object[]>> findWorkHoursByEmployeeId(@RequestParam Long idPracownika){
        try{
            return ResponseEntity.ok().body( workHoursService.findWorkHoursByEmployeeId(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body( new ArrayList<>());
        }
    }

    public ResponseEntity<List<Object[]>> findWorkHoursByEmployeeIdAndRecent(@RequestParam Long idPracownika){
        try{
            return ResponseEntity.ok().body( workHoursService.findWorkHoursByEmployeeIdAndRecent(idPracownika));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

}
