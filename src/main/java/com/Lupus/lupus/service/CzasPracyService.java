package com.LUPUS.lupus.service;

import com.LUPUS.lupus.repository.CzasPracyRepository;
import com.LUPUS.lupus.DTO.CzasPracyDTO; // Załóżmy, że masz odpowiednią klasę DTO
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CzasPracyService {

    @Autowired
    private CzasPracyRepository repo;

    // Zaktualizowanie godziny rozpoczęcia pracy dla pracownika
    @Transactional
    public void updateStartPracy(Long idPracownika){
        repo.updateStartPracy(idPracownika);
    }

    // Zainicjowanie rozpoczęcia pracy dla wszystkich pracowników
    @Transactional
    public void insertStartDayForEmployees(){
        repo.insertStartDayForEmployees();
    }

    // Zliczanie godzin pracy dla pracownika w danym dniu
    public List<CzasPracyDTO> sumGodzinyPracyForEmployeeOnDate(Long idPracownika, LocalDate dataPracy){
        List<CzasPracyDTO> result = repo.findGodzinyPracyForEmployeeOnDate(idPracownika, dataPracy);
        return result;
    }

    // Zliczanie godzin pracy dla pracownika w okresie między dwoma datami
    public List<CzasPracyDTO> sumGodzinyPracyForEmployeeBetweenDates( LocalDate dataStart, LocalDate dataEnd){
        List<CzasPracyDTO> result = repo.findGodzinyPracyBetweenDates( dataStart, dataEnd);
        return result;
    }

    // Zliczanie godzin pracy dla wszystkich pracowników w zadanym okresie
    public List<CzasPracyDTO> sumGodzinyPracy(LocalDate startDate, LocalDate endDate){
        List<CzasPracyDTO> result = repo.sumGodzinyPracy(startDate, endDate);
        return result;
    }

    // Zaktualizowanie godziny rozpoczęcia pracy dla pracownika na konkretną datę
    public void updateStartPracy(Long idPracownika, LocalDate dataPracy){
        repo.updateStartPracy(idPracownika, dataPracy);
    }

    // Zaktualizowanie godziny rozpoczęcia pracy dla wszystkich pracowników na dany dzień
    public void updateStartPracyByDataPracy(LocalDate dataPracy){
        repo.updateStartPracyByDataPracy(dataPracy);
    }

    // Metoda mapująca wyniki zapytań z repozytorium do DTO
    private List<CzasPracyDTO> mapToCzasPracyDTO(List<Object[]> results) {
        // Przekształcanie Object[] na CzasPracyDTO (przykład mapowania)
        return results.stream()
                .map(result -> new CzasPracyDTO(
                        (Long) result[0],   // id_pracownika
                        (LocalDate) result[1],  // data_pracy
                        (LocalDateTime) result[2],  // start_pracy
                        (LocalDateTime) result[3],  // stop_pracy
                        (Duration) result[4],  // czas_przerwy
                        (String) result[5]
                ))
                .toList();
    }
}
