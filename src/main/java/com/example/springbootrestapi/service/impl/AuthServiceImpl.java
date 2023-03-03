package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.User;
import com.example.springbootrestapi.payload.LoginDTO;
import com.example.springbootrestapi.payload.RegisterDTO;
import com.example.springbootrestapi.repository.RoleRepository;
import com.example.springbootrestapi.repository.UserRepository;
import com.example.springbootrestapi.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged-in successfully";
    }

    @Override
    public RegisterDTO register(RegisterDTO registerDTO) {
        User userFromDTO = mapToEntity(registerDTO);
        User newUser = userRepository.save(userFromDTO);
        return mapToDTO(newUser);
    }

    private User mapToEntity(RegisterDTO registerDTO){
        return modelMapper.map(registerDTO, User.class);
    }

    private RegisterDTO mapToDTO(User user){
        return modelMapper.map(user, RegisterDTO.class);
    }
}
