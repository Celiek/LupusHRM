package com.Lupus.demo.services;

import com.Lupus.demo.repository.PayslipMonthlyInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PayslipMonthlyService {
    @Autowired
    private PayslipMonthlyInterface payslipMonthlyInterface;
    @Transactional
    public void addMonthlyPayslipForEmployee(Long idPracownika, Double kwota){
        payslipMonthlyInterface.addMonthlyPayslipForEmployee(idPracownika, kwota);
    }
    @Transactional
    public void addMonthlyPayslips(Double kwota){
        payslipMonthlyInterface.addMonthlyPayslips(kwota);
    }
    @Transactional
    public void updateMonthlyPayslipForEmployee(Long idPracownika, Double kwota, Date dataWyplaty){
        payslipMonthlyInterface.updateMonthlyPayslipForEmployee(idPracownika, kwota, dataWyplaty);
    }
    @Transactional
    public void updateMonthlyPayslipsForEmployees(Double kwota, Date dataWyplaty){
        payslipMonthlyInterface.updateMonthlyPayslipsForEmployees(kwota, dataWyplaty);
    }
    @Transactional
    public void deleteMonthlyPayslipForEmployeeId(Long idPracownika, Date dataWyplaty){
        payslipMonthlyInterface.deleteMonthlyPayslipForEmployeeId(idPracownika,dataWyplaty);
    }
    @Transactional
    public void deleteMonthlyPayslipForEmployees(Date dataWyplaty){
        payslipMonthlyInterface.deleteMonthlyPayslipForEmployees(dataWyplaty);
    }
}
