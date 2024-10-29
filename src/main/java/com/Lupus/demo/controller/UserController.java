package com.Lupus.demo.controller;

import com.Lupus.demo.dto.UserDTO;
import com.Lupus.demo.repository.UserInterface;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserInterface userInterface;
    private String error = "Wystąpił błąd: ";

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUserById(
            @RequestParam Long userId){
        try{
            userInterface.deleteUserById(userId);
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
            userInterface.updateUser(idPracownika, imie, nazwisko, typPracownika, zdjecie, dataPrzyjazdu, dataRozpoczeciaPracy, drugieImie);
            return ResponseEntity.ok("Zaktualizowano uzytkownika " + imie+ " "+ nazwisko);
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> changeEmployeeType(@RequestParam("idPracownika") Long idPracownika,
                                                     @RequestParam("typPracownika") String typPracownika){
        try{
            userInterface.changeEmployeeType(idPracownika, typPracownika);
            return ResponseEntity.ok().body("Pomyślnie zaktualizowano użytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> findAll(){
        try{
            return (ResponseEntity<String>) userInterface.findAll();
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> findUserById(@RequestParam Long idPracownika){
        try{
            return (ResponseEntity<String>) userInterface.findUserById(idPracownika);
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    public ResponseEntity<String> addUser(@RequestParam UserDTO userDTO){
        try{
            userInterface.addUser(userDTO);
            return ResponseEntity.ok().body("Pomyślnie dodano nowego użytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
}
