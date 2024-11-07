package com.Lupus.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userLogowowanie")
public class User {

    @Serial
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne
    @JoinColumn(name = "id_Pracownika")
    private Long idUzytkownika;
    private String login;
    private String haslo;

    @Enumerated(EnumType.STRING)
    private Role role;

}
