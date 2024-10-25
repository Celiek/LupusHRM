package com.Lupus.demo.services;

import com.Lupus.demo.dto.UserDTO;
import com.Lupus.demo.model.User;
import com.Lupus.demo.repository.UserInterface;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    private UserInterface userInterface;

    @Transactional
    public void deletUserById(Long userId){
        userInterface.deleteUserById(userId);
    }

    @Transactional
    public void addUser(UserDTO userDTO){
        userInterface.addUser(userDTO);
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

    public User findUserByID(Long userId){
       return userInterface.findUserById(userId);
    }


}
