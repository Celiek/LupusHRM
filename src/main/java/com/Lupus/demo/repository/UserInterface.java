package com.Lupus.demo.repository;


import com.Lupus.demo.model.Role;
import com.Lupus.demo.model.User;
import com.Lupus.demo.model.Worker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserInterface extends CrudRepository<Worker,Long> {

    @Query( value = "INSERT INTO userLogowowanie (login,haslo,rola) VALUES :login, :haslo, :rola", nativeQuery = true)
    User createAccount(String login, String haslo, Role rola);

    @Query(value = "INSERT INTO userLogowowanie(login,password, id_pracownika, rola SELECT CONCAT('pracownik',id_pracownika) :haslo,id_pracownika FROM pracownik" ,nativeQuery = true)
    User createAccountsForEveryone(String haslo);

    @Query(value = "UPDATE userLogowowanie SET password = :haslo WHERE idPracownika = id_pracownika;", nativeQuery = true)
    void updatePasswordByWorkerId(Long idPracownika,String haslo);

    @Query(value = "DELETE FROM userLogowowanie WHERE id_uzytkownika :idPracownika",nativeQuery = true)
    void deleteUserByWorkerId(Long idPracownika);
}
