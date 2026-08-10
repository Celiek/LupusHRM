package com.pracownikService.demo.service;

import com.pracownikService.demo.Dto.UserDto;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.entity.User;
import com.pracownikService.demo.repo.PracownikRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final com.pracownikService.demo.repo.UserRepository userRepo;
    private final PracownikRepo pracownikRepo;
    private final PasswordEncoder encoder;

    //dodać testy
    @Transactional
    public void createUser(UserDto dto){
        Pracownik pracownik = pracownikRepo
                .findById(dto.getPracownikId())
                .orElseThrow( () ->
                        new RuntimeException("Nie znaleziono pracownika od id: "
                                + dto.getPracownikId())
                );

        if(userRepo.existsByPracownik_IdPracownik(
                dto.getPracownikId())){
            throw new RuntimeException("Ten pracownik juz posiada konto użytkownika");
        }

        User user = new User();
        user.setLogin( dto.getLogin());
        user.setPassword( encoder.encode(dto.getPassword()));
        user.setRole( dto.getRole());
        user.setEnabled( true);
        user.setPracownik( pracownik);

        userRepo.save(user);
    }
}
