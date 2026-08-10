package com.pracownikService.demo.controller;

import com.pracownikService.demo.Dto.UserDto;
import com.pracownikService.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUSer(@RequestBody UserDto dto){
            userService.createUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
