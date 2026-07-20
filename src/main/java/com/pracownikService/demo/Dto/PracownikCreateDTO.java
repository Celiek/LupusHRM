package com.pracownikService.demo.Dto;

import com.pracownikService.demo.entity.TypPracownika;
import com.pracownikService.demo.entity.Uprawnienia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PracownikCreateDTO {
    private String nazwa;
    private int wiek;
    private TypPracownika typPracownika;
    private Uprawnienia uprawnienia;
    private LocalDate dataDolaczenia;
    private LocalDate dataRozpoczeciaPracy;
    private MultipartFile zdjecie;
}
