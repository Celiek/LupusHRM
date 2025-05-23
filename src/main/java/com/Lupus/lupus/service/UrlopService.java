package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.UrlopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UrlopService {
    @Autowired
    private UrlopyRepository repo;

    //zwraca listę wszystkich urlopów pracowników
    public List<Object[]> findAllUrlopy(){
        return repo.findAllUrlopy();
    }

    public List<Object[]> findUrlopyFor(Long idPracownika){
        return repo.findUrlopyFor(idPracownika);
    }

    public List<Object[]> addUrlopForPracownik(Long idPracownika,
                                               LocalDate data_Od,
                                               LocalDate data_Do,
                                               String typ_Urlopu,
                                               String powod){
        return repo.addUrlopForPracownik(idPracownika,data_Od,data_Do,typ_Urlopu,powod);
    }

    public int dodajUrlopDlaWszystkich(LocalDate dataOd,
                                       LocalDate dataDo,
                                       String typ,
                                       String powod){
        return repo.addUrlopForEmployees(dataOd,dataDo,typ,powod);
    }

    public void zaktualizujUrlop(Long id,
                                 Long idPracownika,
                                 LocalDate dataOd,
                                 LocalDate dataDo,
                                 String typUrlopu,
                                 String powod){
        repo.updateUrlop(id, idPracownika, dataOd, dataDo, typUrlopu, powod);
    }

    public void usunUrlop(Long id){
        repo.removeUrlop(id);
    }
}
