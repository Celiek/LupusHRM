package com.Lupus.lupus.service;

import com.Lupus.lupus.DTO.PracownikDto;
import com.Lupus.lupus.repository.PracownikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class pracownikService {
    @Autowired
    private PracownikRepository repo;


    @Transactional
    public void addPracownik(String imie,
                             String dimie,
                             String nazwisko,
                             String typ,
                             byte[] zdjecie,
                             LocalDate data,
                             String login,
                             String haslo){
        repo.addPracownik(imie,dimie,nazwisko,typ,zdjecie,data,login,haslo);
    }


    public List<PracownikDto> findAllUsers() {
        List<Object[]> results = repo.findallUsers();
        List<PracownikDto> users = new ArrayList<>();

        for (Object[] row : results) {
            PracownikDto dto = new PracownikDto();
            dto.setIdPracownika(((Number) row[0]).longValue()); // id_pracownika
            dto.setImie((String) row[1]); // imie
            dto.setDrugieImie((String) row[2]); // drugie_imie
            dto.setNazwisko((String) row[3]); // nazwisko
            dto.setTypPracownika((String) row[4]); // typ_pracownika
            // Konwersja zdjęcia na base64 (jeśli zdjęcie nie jest puste)
            dto.setZdjecie(row[5] != null ? Base64.getEncoder().encodeToString((byte[]) row[5]) : null);
            // Konwersja daty (jeśli istnieje)
            dto.setDataDolaczenia((row[6] != null) ? LocalDate.parse(row[6].toString()) : null);
            dto.setLogin((String) row[7]); // login
            dto.setHaslo((String) row[8]); // haslo

            users.add(dto);
        }

        return users;
    }


    public List<Map<String,Object>> findUserById(Long idPracownik){
            List<Object[]> results = repo.findUserById(idPracownik);
            List<Map<String,Object>> mappedResults = new ArrayList<>();

            for(Object[] row : results){
                Map<String,Object> rowMap = new HashMap<>();
                rowMap.put("imie",row[0]);
                rowMap.put("drugie_imie",row[1]);
                rowMap.put("nazwisko",row[2]);
                rowMap.put("typ_pracownika",row[3]);
                rowMap.put("zdjecie",row[4]);
                rowMap.put("data_dolaczenia",row[5]);
                mappedResults.add(rowMap);
            }

            return mappedResults;
    }

    public List<Map<String,Object>> findUserByName(String imie){
        List<Object[]> results = repo.findUserByName(imie);
        List<Map<String,Object>> mappedResults = new ArrayList<>();

        for (Object[] row: results){
            Map<String,Object> rowMap = new HashMap<>();
            rowMap.put("imie",row[0]);
            rowMap.put("drugie_imie",row[1]);
            rowMap.put("nazwisko",row[2]);
            rowMap.put("typ_pracownika",row[3]);
            rowMap.put("zdjecie",row[4]);
            rowMap.put("data_dolaczenia",row[5]);
            mappedResults.add(rowMap);
        }
        return mappedResults;
    }

    public void deletePracownikById(Long idPracownik){
        repo.deletePracownikById(idPracownik);
    }

    public void updatePracownik(Long id_Pracownika,String imie, String dimie, String nazwisko, String typPracownika,
                                byte[] zdjecie, LocalDate data, String login, String haslo) {
        repo.updatePracownik(id_Pracownika,imie,dimie,nazwisko,typPracownika,zdjecie,data,login,haslo);
    }

    public void deletePracownikByNameAndSurname(String imie,String nazwisko){
        repo.deletePracownikByNameAndSurname(imie, nazwisko);
    }

    public long countEmployeesStartedToday() {
        return repo.countTodayNewEmployees();
    }

    public String getFirstStartTimeToday() {
        return repo.findFirstStartTimeToday();
    }

    public double getTotalBreakTimeTodayInHours() {
        Double minutes = repo.getTotalBreakTimeInMinutesToday();
        return minutes != null ? minutes / 60.0 : 0.0;
    }

    public Double getCzasPracy() {
        return repo.getCzasPracy();
    }

}
