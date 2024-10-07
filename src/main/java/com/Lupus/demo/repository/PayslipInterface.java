package com.Lupus.demo.repository;

import com.Lupus.demo.model.Payslip;
import org.springframework.data.repository.CrudRepository;

public interface PayslipInterface extends CrudRepository<Payslip, Long> {
}
