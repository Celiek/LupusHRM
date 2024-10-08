package com.Lupus.demo.dto;

import com.Lupus.demo.model.WorkHours;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private String Imie;
    private String Nazwisko;
    private String Typ_pracownika;
    private byte[] zdjecie;
    private List<WorkHours> workHoursUser;
}
