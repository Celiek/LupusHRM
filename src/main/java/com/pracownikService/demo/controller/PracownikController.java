package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.pracownik.PracownikCreateDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikDTO;
import com.pracownikService.demo.Dto.pracownik.PracownikWithIdDTO;
import com.pracownikService.demo.service.PracownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    //TODO
    // poprawić dodawnaie zdjęcia do MInio i usuwanie starego
    @PostMapping("/updateZdjecie")
    public ResponseEntity<?> updateZdjecie(@RequestParam Long id,
                                           @RequestParam MultipartFile zdjecie){
        try{
            service.updateZdjecie(id,zdjecie);
            return ResponseEntity.ok("Zaktualizowano zdjecie dla " + id);
        } catch (Exception e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    //zmienić PracownikDTO na
    @GetMapping("/listAll")
    public ResponseEntity<List<PracownikDTO>> listAllPracownik() {
        return ResponseEntity.ok(service.findAllPracownikDTO());
    }

    //pracownik Details
    @GetMapping("/findProjectionByPracownikId")
    public ResponseEntity<PracownikWithIdDTO> findProjectionByPracownikId(@RequestParam Long idPracownik){
        return ResponseEntity.ok().body(service.findPracownikDetailsById(idPracownik));
    }

    @GetMapping("/countAll")
    public ResponseEntity<Long> countAllPracownicy(){
        return ResponseEntity.ok().body(service.countAllPracownicy());
    }

}
