package com.LUPUS.lupus.service;

import com.Lupus.lupus.repository.MiesiecznaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class miesiecznaService {
    @Autowired
    private MiesiecznaRepository repo;

    @Transactional
    public void insertMonthlyPayment(){
        repo.insertMonthlyPayment();
    }
    @Transactional
    public void inserMonthlyPayments(Long idPracownika){
        repo.inserMonthlyPayments(idPracownika);
    }
    @Transactional
    public void updateMontlyPayment(Double kwota,Long idPracownika,Double zaliczka){
        repo.updateMontlyPayment(kwota,idPracownika,zaliczka);
    }
    @Transactional
    public void updateMonthlyPayments(Double nowaKwota,
                                      Double nowaZaliczka,
                                      Date nowaData){
        repo.updateMonthlyPayments(nowaKwota, nowaZaliczka, nowaData);
    }
    @Transactional
    public void updateMonthlyPayment(Double nowaKwota,
                                     Double nowaZaliczka,
                                     Date nowaData,
                                     Long idPracownika){
        repo.updateMonthlyPayment(nowaKwota, nowaZaliczka, nowaData, idPracownika);
    }
    @Transactional
    public void deleteMonthlypaymentById(Long idPracownika,Date dataWyplaty){
        repo.deleteMonthlypaymentById(idPracownika, dataWyplaty);
    }
    @Transactional
    public void deleteMonthlyPaymentsByDate(Date dataWyplaty){
        repo.deleteMonthlyPaymentsByDate(dataWyplaty);
    }

}
