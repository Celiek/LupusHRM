package com.Lupus.demo.services;

import com.Lupus.demo.repository.PayslipInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDate;

@Service
public class PayslipService {

    @Autowired
    private PayslipInterface paymentRepository; // Użycie repozytorium do operacji na płatnościach

    @Transactional
    public void insertWeeklyPayment(Long idPracownika, Double kwota, Double zaliczka) {
        paymentRepository.insertWeeklyPayment(idPracownika, kwota, zaliczka);
    }
    //dodawanie wypłaty tygodniowej dla wszystkich pracowników
    public void insertWeeklyPayments(Double kwota, Double zaliczka){
        paymentRepository.insertWeeklyPayments(kwota,zaliczka);
    }
    // Dodawanie wypłaty miesięcznej dla wszystkich pracowników
    @Transactional
    public void insertMonthlyPayments(Double kwotaMiesieczna) {
        paymentRepository.insertMonthlyPayments(kwotaMiesieczna);
    }

    // Aktualizacja wypłat wszystkich pracowników w danym tygodniu
    @Transactional
    public void updateWeeklyPayments(Double nowaKwota, Double nowaZaliczka) {
        paymentRepository.updateWeeklyPayments(nowaKwota, nowaZaliczka);
    }

    // Aktualizacja wypłat wszystkich pracowników w danym miesiącu
    @Transactional
    public void updateMonthlyPayments(Double nowaKwotaMiesieczna) {
        paymentRepository.updateMonthlyPayments(nowaKwotaMiesieczna);
    }

    // Modyfikacja tygodniowej wypłaty i zaliczki dla jednego pracownika
    @Transactional
    public void updateWeeklyPayment(Double nowaKwota, Double nowaZaliczka, Long idPracownika) {
        paymentRepository.updateWeeklyPayment(nowaKwota, nowaZaliczka, idPracownika);
    }

    // Modyfikacja miesięcznej wypłaty dla jednego pracownika
    @Transactional
    public void updateMonthlyPayment(Double nowaKwotaMiesieczna, Long idPracownika) {
        paymentRepository.updateMonthlyPayment(nowaKwotaMiesieczna, idPracownika);
    }

    // Zwraca wypłatę dla wszystkich pracowników z ostatniego miesiąca
    public List<Object[]> findMonthlyPaymentsWithDetails() {
        return paymentRepository.findMonthlyPaymentsWithDetails();
    }

    // Zwraca wypłatę dla pojedynczego pracownika z ostatniego miesiąca
    public List<Object[]> findMonthlyPaymentsWithDetailsByEmployeeId(Long idPracownika) {
        return paymentRepository.findMonthlyPaymentsWithDetailsByEmployeeId(idPracownika);
    }

    // Zwraca listę wypłat tygodniowych dla jednego pracownika z ostatniego miesiąca
    public List<Object[]> findWeeklyPaymentsByEmployeeId(Long idPracownika) {
        return paymentRepository.findWeeklyPaymentsByEmployeeId(idPracownika);
    }

    // Lista wypłat tygodniowych dla wszystkich pracowników z ostatniego miesiąca
    public List<Object[]> findWeeklyPaymentsForAllEmployees() {
        return paymentRepository.findWeeklyPaymentsForAllEmployees();
    }

    // Zwraca wypłaty tygodniowe dla wszystkich pracowników z określonego tygodnia
    public List<Object[]> findPaymentsByDate(LocalDate dataWyplaty) {
        return paymentRepository.findPaymentsByDate(dataWyplaty);
    }

    // Zwraca wypłaty dla jednego pracownika z określonego okresu
    public List<Object[]> findPaymentsByDateAndEmployeeId(LocalDate dataWypłaty, Long idPracownika) {
        return paymentRepository.findPaymentsByDateAndEmployeeId(dataWypłaty, idPracownika);
    }

    public List<Object[]> findWeeklyPaymentsByEmployeId(Long idPracownika){
        return paymentRepository.findWeeklyPaymentsByEmployeId(idPracownika);
    }
}
