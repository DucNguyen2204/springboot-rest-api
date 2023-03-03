package com.example.springbootrestapi.controller;

import com.example.springbootrestapi.payload.LoginDTO;
import com.example.springbootrestapi.payload.RegisterDTO;
import com.example.springbootrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("register")
    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO){
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED) ;
    }

}
