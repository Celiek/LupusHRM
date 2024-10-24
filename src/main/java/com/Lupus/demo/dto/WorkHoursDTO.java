package com.Lupus.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class WorkHoursDTO implements Serializable {
    private LocalDate data;
    private LocalDate startPracy;
    private LocalDate stopPracy;
    private String opis;
    private Date czas_przerw;
}
