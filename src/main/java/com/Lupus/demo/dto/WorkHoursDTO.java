package com.Lupus.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class WorkHoursDTO implements Serializable {
    private LocalDate data;
    private LocalDate startPracy;
    private LocalDate stopPracy;
    private String opis;
}
