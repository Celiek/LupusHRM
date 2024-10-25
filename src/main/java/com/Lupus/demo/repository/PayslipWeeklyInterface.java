package com.Lupus.demo.repository;


import com.Lupus.demo.model.PayslipsWeekly;
import org.springframework.data.repository.CrudRepository;

public interface PayslipWeeklyInterface extends CrudRepository<PayslipsWeekly,Long> {
    void addWeeklyPayslip();
    void updateWeeklyPayslip();
    void removeWeeklyPayslip();
    void addWeeklyPayslipForUser();
}
