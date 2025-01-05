package com.Lupus.lupus.service;

import com.Lupus.lupus.DTO.CzasPracyDTO; // Załóżmy, że masz odpowiednią klasę DTO
import com.Lupus.lupus.entity.czas_pracy;
import com.Lupus.lupus.repository.CzasPracyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    //Zliczanie godzin pracy dla pracownika w danym dniu
   public List<Map<String, Object>> sumGodzinyPracyForEmployeeOnDate(Long idPracownika, LocalDate dataPracy){
//        List<Map<String, Object>> result = repo.findGodzinyPracyForEmployeeOnDate(idPracownika, dataPracy);
//        return result;

       List<Object[]> results = repo.findGodzinyPracyForEmployeeOnDate(idPracownika,dataPracy);
       List<Map<String, Object>> resultList = new ArrayList<>();

       for (Object[] row : results){
           Map<String, Object>  resultMap = new HashMap<>();
           resultMap.put("idPracownik", row[0]);    // Przypisanie pierwszego elementu (idPracownika)
           resultMap.put("dataPracy", row[1]);      // Przypisanie drugiego elementu (dataPracy)
           resultMap.put("godzinyPracy", row[2]);   // Przypisanie trzeciego elementu (godzinyPracy)

           // Dodajemy mapę do listy wyników
           resultList.add(resultMap);
       }
        return resultList;
    }

    // Zliczanie godzin pracy dla pracownika w okresie między dwoma datami
    public List<Map<String,Object>> sumGodzinyPracyForEmployeeBetweenDates(Long id_pracownik,LocalDate dataStart, LocalDate dataEnd) {
        //List<CzasPracyDTO> result = repo.findGodzinyPracyBetweenDates(dataStart, dataEnd);
        return repo.findGodzinyPracyBetweenDates(id_pracownik,dataStart, dataEnd );
    }


    // Zliczanie godzin pracy dla wszystkich pracowników w zadanym okresie
    public List<Object[]> sumGodzinyPracy(LocalDate startDate, LocalDate endDate){
        return repo.sumGodzinyPracy(startDate, endDate);
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
                        (LocalTime) result[2],  // start_pracy
                        (LocalTime) result[3],  // stop_pracy
                        (Duration) result[4],  // czas_przerwy
                        (String) result[5]
                ))
                .toList();
    }

    public List<Object[]> findCzasPracyByDate(LocalDate dataPracy){
        return repo.findCzasPracyByDate(dataPracy);
    }
}
