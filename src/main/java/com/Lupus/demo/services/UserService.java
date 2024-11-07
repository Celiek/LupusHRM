package com.Lupus.demo.services;

import com.Lupus.demo.model.Role;
import com.Lupus.demo.model.User;
import com.Lupus.demo.repository.UserInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserInterface user;
    @Transactional
    public void createAccount(String login, String haslo, Role rola){
        user.createAccount(login, haslo, rola);
    }
    @Transactional
    public void updatePasswordByWorkerId(Long idPracownika,String haslo){
        user.updatePasswordByWorkerId(idPracownika, haslo);
    }
    @Transactional
    public void deleteUserByWorkerId(Long idPracownika){
        user.deleteUserByWorkerId(idPracownika);
    }
    @Transactional
    public User createAccountsForEveryone(String haslo){
        return user.createAccountsForEveryone(haslo);
    }
}
