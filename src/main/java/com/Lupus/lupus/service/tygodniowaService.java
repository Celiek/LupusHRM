package com.Lupus.lupus.service;

import com.Lupus.lupus.DTO.MonthlyProjection;
import com.Lupus.lupus.DTO.WeeklyPayDto;
import com.Lupus.lupus.repository.TygodniowaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Lupus.lupus.DTO.WeeklyPayProjection;

import java.util.Date;
import java.util.List;

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

    @Transactional
    public WeeklyPayProjection getweeklyPaychekEmployee(String imie, String nazwisko, Date data) {
        return repo.getweeklyPaychekEmployee(imie, nazwisko, data);
    }

    @Transactional
    public List<WeeklyPayProjection> getWeeklyPaycheks(Date od, Date ddo) {
        return repo.getWeeklyPaycheks(od, ddo);
    }


    @Transactional
    public void getWeeklyPaychek(String imie, String nazwisko){
        repo.getWeeklyPaychek(imie,nazwisko);
    }

    @Transactional
    public List<MonthlyProjection> getMonthlyPaycheks(Date start, Date stop){
        return repo.getMonthlyPaycheks(start, stop);
    }

    public List<WeeklyPayProjection>getWeeklyPaychecksForEmployeeInDateRange(
            String imie, String nazwisko, Date od, Date ddo)
    {
        return repo.getWeeklyPaychecksForEmployeeInDateRange(imie, nazwisko, od, ddo);
    }

    @Transactional
    public void getMonthlyPaycheck(Date start, Date stop, String imie, String dimie, String nazwisko){
        repo.getMonthlyPaycheck(start, stop, imie, dimie, nazwisko);
    }

}
