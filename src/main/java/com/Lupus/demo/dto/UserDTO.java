package com.Lupus.demo.dto;

import com.Lupus.demo.model.WorkHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private String Imie;
    private String DrugieImie;
    private String Nazwisko;
    private String Typ_pracownika;
    private Date Data_Dolaczenia;
    private Date data_rozpoczecia_pracy;
    private byte[] zdjecie;
    private List<WorkHours> workHoursUser;
}
