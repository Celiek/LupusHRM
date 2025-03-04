package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.PracownikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String,Object>> findAllUsers(){
        List<Object[]> results = repo.findallUsers(); // Pobierz dane z repozytorium
        List<Map<String, Object>> mappedResults = new ArrayList<>();

        // Konwertowanie wyników Object[] na Map
        for (Object[] row : results) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("imie", row[0]);
            rowMap.put("drugie_imie", row[1]);
            rowMap.put("nazwisko", row[2]);
            rowMap.put("typ_pracownika", row[3]);
            rowMap.put("zdjecie", row[4]);
            rowMap.put("data_dolaczenia", row[5]);
            mappedResults.add(rowMap);
        }
        return mappedResults;
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

    public void updatePracownik(String imie,String dimie,
                                String nazwisko, String typPracownika,
                                byte[] zdjecie, LocalDate data,
                                String login, String haslo){
        repo.updatePracownik(imie, dimie, nazwisko, typPracownika, zdjecie, data, login, haslo);
    }

    public void deletePracownikByNameAndSurname(String imie,String nazwisko){
        repo.deletePracownikByNameAndSurname(imie, nazwisko);
    }
}
