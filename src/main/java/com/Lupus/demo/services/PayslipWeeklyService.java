package com.Lupus.demo.services;

import com.Lupus.demo.repository.PayslipWeeklyInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PayslipWeeklyService {
    @Autowired
    private PayslipWeeklyInterface payslipWeeklyInterface;

    @Transactional
    public void addWeeklyPayslip(Long idPracownika,Double kwota,Double zaliczka){
        payslipWeeklyInterface.addWeeklyPayslip(idPracownika, kwota, zaliczka);
    }

    @Transactional
    public void updateWeeklyPayslipForEmployee(Long idPracownika, Double kwota, Date data, Double zaliczka){
        payslipWeeklyInterface.updateWeeklyPayslipForEmployee(idPracownika, kwota, data, zaliczka);
    }

    @Transactional
    public void updateWeeklyPayslipForEmployees(Double kwota,Date data){
        payslipWeeklyInterface.updateWeeklyPayslipForEmployees(kwota,data);;
    }

    @Transactional
    public void removeWeeklyPayslip(Date data){
        payslipWeeklyInterface.removeWeeklyPayslip(data);
    }
    @Transactional
    public void addWeeklyPayslipForUser(Long idPracownika, Double kwota, Date dataWyplaty, Double zaliczka){
        payslipWeeklyInterface.addWeeklyPayslipForUser(idPracownika,kwota,dataWyplaty,zaliczka);
    }


}
