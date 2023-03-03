package com.example.springbootrestapi.service;

import com.example.springbootrestapi.payload.LoginDTO;
import com.example.springbootrestapi.payload.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    RegisterDTO register(RegisterDTO registerDTO);
}
