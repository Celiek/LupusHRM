package com.Lupus.demo.services;

import com.Lupus.demo.dto.UserDTO;
import com.Lupus.demo.model.User;
import com.Lupus.demo.repository.UserInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private UserInterface userInterface;

    @Transactional
    public void deletUserById(Long userId){
        userInterface.deleteUserById(userId);
    }

    @Transactional
    public void addUser(UserDTO userDTO) {
        User user = new User();
        user.setImie(userDTO.getImie());
        user.setNazwisko(userDTO.getNazwisko());
        user.setTyp_pracownika(userDTO.getTyp_pracownika());
        user.setZdjecie(userDTO.getZdjecie());
        user.setData_przyjazdu(userDTO.getData_Dolaczenia());
        user.setData_rozpoczecia_pracy(userDTO.getData_rozpoczecia_pracy());
        user.setDrugieImie(userDTO.getDrugieImie());

        userInterface.save(user);
    }
    @Transactional
    public void updateUser(Long userID,
                           String imie,
                           String nazwisko,
                           String typPracownika,
                           byte[] zdjecie,
                           LocalDate dataPrzyjazdu,
                           LocalDate dataRozpoczeciaPracy,
                           String drugieImie){
        userInterface.updateUser(userID,imie,nazwisko,typPracownika,zdjecie,dataPrzyjazdu,dataRozpoczeciaPracy,drugieImie);
    }

    public void changeEmployeeType(Long idPracownika, String typPracownika){
        userInterface.changeEmployeeType(idPracownika,typPracownika);
    }

    public List<User> findUserByID(Long userId){
       return userInterface.findUserById(userId);
    }


}
