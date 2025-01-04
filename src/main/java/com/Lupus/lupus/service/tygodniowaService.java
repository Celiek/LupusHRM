package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.TygodniowaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class tygodniowaService {
    @Autowired
    private TygodniowaRepository repo;

    @Transactional
    public void insertWeeklyPaycheck(Long idPracownika,
                                     Double kwota,
                                     Double zaliczka,
                                     Date data_wyplaty){
        repo.insertWeelkyPaycheck(idPracownika, kwota, zaliczka, data_wyplaty);
    }
    @Transactional
    public void insertWeeklyPaychecks(Double kwota,
                                      Double zaliczka,
                                      Date dataWyplaty){
        repo.insertWeeklyPaychecks(kwota, zaliczka, dataWyplaty);
    }
    @Transactional
    public void updateWeeklyPaycheckForEmployee(Long idPracownika,
                                                Double kwota,
                                                Double zaliczka,
                                                Date dataWyplaty){
        repo.updateWeeklyPaycheckForEmployee(idPracownika, kwota, zaliczka, dataWyplaty);
    }
    @Transactional
    public void updateWeeklyPaycheckForEmployees(Double kwota,
                                                 Double zaliczka,
                                                 Date data){
        repo.updateWeeklyPaycheckForEmployees(kwota, zaliczka, data);
    }
}
