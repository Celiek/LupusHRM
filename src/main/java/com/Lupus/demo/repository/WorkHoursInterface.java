package com.Lupus.demo.repository;

import com.Lupus.demo.model.WorkHours;
import org.springframework.data.repository.CrudRepository;

public interface WorkHoursInterface extends CrudRepository<WorkHours, Long> {
}
