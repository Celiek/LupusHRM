package com.Lupus.demo.controller;

import com.Lupus.demo.model.Role;
import com.Lupus.demo.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private String error = "Wystapił bład: ";

    @RequestMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestParam String login, @RequestParam String haslo, Role rola){
        try{
            userService.createAccount(login, haslo, rola);
            return ResponseEntity.ok().body("Pomyslnie utworzono uzytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @Transactional
    @RequestMapping("/updatePassword")
    public ResponseEntity<String> updatePasswordByWorkerId(@RequestParam Long idPracownika,@RequestParam String haslo){
        try{
            userService.updatePasswordByWorkerId(idPracownika, haslo);
            return ResponseEntity.ok().body("Pomyslnie zaktualizowano haslo");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }

    @RequestMapping("/deleteUser")
    public ResponseEntity<String> deleteUserByWorkerId(@RequestParam Long idPracownika){
        try{
            userService.deleteUserByWorkerId( idPracownika);
            return ResponseEntity.ok().body("Usunieto Uzytkownika");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
    @RequestMapping("/createAccounts")
    public ResponseEntity<String>createAccountsForEveryone(@RequestParam String haslo){
        try{
            userService.createAccountsForEveryone(haslo);
            return ResponseEntity.ok().body("Pomyslnie wygenerowano dane logowania dla wszystkich uzytkownikow");
        } catch (Exception e){
            return ResponseEntity.status(500).body(error + e.getMessage());
        }
    }
}
