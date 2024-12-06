package com.LUPUS.lupus.service;

import com.LUPUS.lupus.repository.PracownikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class pracownikService {
    @Autowired
    private PracownikRepository repo;


    @Transactional
    public void addPracownik(String imie,
                             String dimie,
                             String nazwisko,
                             String typ,
                             Byte[] zdjecie,
                             LocalDate data,
                             String login,
                             String haslo){
        repo.addPracownik(imie,dimie,nazwisko,typ,zdjecie,data,login,haslo);
    }

    public List<Object[]> findAllUsers(){
        return repo.findallUsers();
    }

    public List<Object[]> findUserById(Long idPracownik){
        return repo.findUserById(idPracownik);
    }

    public List<Object[]> findUserByName(String imie){
        return repo.findUserByName(imie);
    }

    public void deletePracownikById(Long idPracownik){
        repo.deletePracownikById(idPracownik);
    }
}
