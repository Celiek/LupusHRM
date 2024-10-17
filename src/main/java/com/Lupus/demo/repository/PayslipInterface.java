package com.Lupus.demo.repository;

import com.Lupus.demo.model.Payslip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//TODO
//zliczanie wypłat tygodniowych z potrąceniem zaliczki
//zliczanie wypłat miesięcznych z potrąceniem zalizek tygodniowych
//dodanie wypłaty dla jednego pracownika
//dodanie wypłaty dla wszystkich pracowników
//potrącenie z wypłaty kosztów za uszkodzenia/cokowliek innego


public interface PayslipInterface extends CrudRepository<Payslip, Long> {


    @Query(value = "SELECT * from ", nativeQuery = true)
    List<Object> getAllPayslipsWeekly();
}
