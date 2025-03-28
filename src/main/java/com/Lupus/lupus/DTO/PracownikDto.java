package com.Lupus.lupus.DTO;

import java.time.LocalDate;

public class PracownikDto {
    private Long idPracownika;
    private String imie;
    private String drugieImie;
    private String nazwisko;
    private String typPracownika;
    private String zdjecie; // Base64 zdjęcia lub null
    private LocalDate dataDolaczenia;
    private String login;
    private String haslo;

    // Gettery i settery
    public Long getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(Long idPracownika) {
        this.idPracownika = idPracownika;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getDrugieImie() {
        return drugieImie;
    }

    public void setDrugieImie(String drugieImie) {
        this.drugieImie = drugieImie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTypPracownika() {
        return typPracownika;
    }

    public void setTypPracownika(String typPracownika) {
        this.typPracownika = typPracownika;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    public LocalDate getDataDolaczenia() {
        return dataDolaczenia;
    }

    public void setDataDolaczenia(LocalDate dataDolaczenia) {
        this.dataDolaczenia = dataDolaczenia;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
}
