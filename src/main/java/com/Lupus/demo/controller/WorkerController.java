package com.Lupus.demo.controller;

import com.Lupus.demo.dto.UserDTO;
import com.Lupus.demo.repository.WorkerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user")
public class WorkerController {
    @Autowired
    private WorkerInterface workerInterface;
    private String error = "Wystąpił błąd: ";

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUserById(
            @RequestParam Long userId){
        try{
            workerInterface.deleteUserById(userId);
            return ResponseEntity.ok("Usunięto użytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> updateUser(@RequestParam("idPracownika") Long idPracownika,
                                             @RequestParam("imie") String imie,
                                             @RequestParam("nazwisko") String nazwisko,
                                             @RequestParam("typPracownika") String typPracownika,
                                             @RequestParam("zdjecie") byte[] zdjecie,
                                             @RequestParam("dataPrzyjazdu") LocalDate dataPrzyjazdu,
                                             @RequestParam("dataRozpoczeciaPracy") LocalDate dataRozpoczeciaPracy,
                                             @RequestParam("drugieImie") String drugieImie){
        try{
            workerInterface.updateUser(idPracownika, imie, nazwisko, typPracownika, zdjecie, dataPrzyjazdu, dataRozpoczeciaPracy, drugieImie);
            return ResponseEntity.ok("Zaktualizowano uzytkownika " + imie+ " "+ nazwisko);
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> changeEmployeeType(@RequestParam("idPracownika") Long idPracownika,
                                                     @RequestParam("typPracownika") String typPracownika){
        try{
            workerInterface.changeEmployeeType(idPracownika, typPracownika);
            return ResponseEntity.ok().body("Pomyślnie zaktualizowano użytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> findAll(){
        try{
            return (ResponseEntity<String>) workerInterface.findAll();
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> findUserById(@RequestParam Long idPracownika){
        try{
            return (ResponseEntity<String>) workerInterface.findUserById(idPracownika);
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> addUser(@RequestParam UserDTO userDTO){
        try{
            workerInterface.addUser(userDTO);
            return ResponseEntity.ok().body("Pomyślnie dodano nowego użytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
}
