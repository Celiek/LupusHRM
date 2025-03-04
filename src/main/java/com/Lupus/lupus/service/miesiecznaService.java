package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.MiesiecznaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class miesiecznaService {
    @Autowired
    private MiesiecznaRepository repo;

    @Transactional
    public void insertMonthlyPayment(Double kwotaMiesieczna, Double sumaZaliczek, LocalDate dataWyplaty){
        repo.insertMonthlyPayment(kwotaMiesieczna, sumaZaliczek, dataWyplaty);
    }
    @Transactional
    public void inserMonthlyPayments(Long idPracownika, Double sumaZaliczek,
                                     LocalDate dataWyplaty, Double kwotaMiesieczna){
        repo.inserMonthlyPayments(idPracownika, sumaZaliczek, dataWyplaty, kwotaMiesieczna);
    }
    @Transactional
    public void updateMontlyPayment(Double kwota,Long idPracownika,Double zaliczka){
        repo.updateMontlyPayment(kwota,idPracownika,zaliczka);
    }
    @Transactional
    public void updateMonthlyPayments(Double nowaKwota,
                                      Double nowaZaliczka,
                                      LocalDate nowaData){
        repo.updateMonthlyPayments(nowaKwota, nowaZaliczka,nowaData);
    }
    @Transactional
    public void updateMonthlyPayment(Double nowaKwota,
                                     Double nowaZaliczka,
                                     Date nowaData,
                                     Long idPracownika){
        repo.updateMonthlyPayment(nowaKwota, nowaZaliczka, nowaData, idPracownika);
    }
    @Transactional
    public void deleteMonthlypaymentById(Long idPracownika,LocalDate dataWyplaty){
        repo.deleteMonthlypaymentById(idPracownika, dataWyplaty);
    }
    @Transactional
    public void deleteMonthlypaymentsByDate(LocalDate dataWyplaty){
        repo.deleteMonthlypaymentsByDate(dataWyplaty);
    }

}
