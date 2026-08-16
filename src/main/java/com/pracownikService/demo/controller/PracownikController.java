package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.PracownikCreateDTO;
import com.pracownikService.demo.Dto.PracownikDTO;
import com.pracownikService.demo.service.PracownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pracownik")
public class PracownikController {

    @Autowired
    private final PracownikService service;

    @PostMapping("/registerPracownik")
    public ResponseEntity<?> addPracownik(PracownikCreateDTO dto){
        try{
            service.addPracownik(dto);
            return ResponseEntity.ok("Zapisano użytkownika "+dto);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/updateZdjecie")
    public ResponseEntity<?> updateZdjecie(@RequestParam Long id,
                                           @RequestParam String zdjecie){
        try{
            service.updateZdjecie(id,zdjecie);
            return ResponseEntity.ok("Zaktualizowano zdjecie dla " + id);
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<PracownikDTO>> listAllPracownik() {
        return ResponseEntity.ok(service.findAllPracownikDTO());
    }
}
