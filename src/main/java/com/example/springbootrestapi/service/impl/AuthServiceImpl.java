package com.example.springbootrestapi.service.impl;

import com.example.springbootrestapi.entity.Role;
import com.example.springbootrestapi.entity.User;
import com.example.springbootrestapi.exception.EmailTakenException;
import com.example.springbootrestapi.exception.UsernameTakenException;
import com.example.springbootrestapi.payload.LoginDTO;
import com.example.springbootrestapi.payload.RegisterDTO;
import com.example.springbootrestapi.repository.RoleRepository;
import com.example.springbootrestapi.repository.UserRepository;
import com.example.springbootrestapi.security.JwtTokenProvider;
import com.example.springbootrestapi.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public RegisterDTO register(RegisterDTO registerDTO) {
        // check user exists by username
        if (userRepository.existsByUsername(registerDTO.getUsername())){
            throw new UsernameTakenException("User", "username", registerDTO.getUsername());
        }
        // check user exists by email
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EmailTakenException("User", "email", registerDTO.getEmail());
        }
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        User userFromDTO = mapToEntity(registerDTO);
        Set<Role> userRoles = new HashSet<>();
        Role role = roleRepository.findByRole("ROLE_USER").get();
        userRoles.add(role);
        userFromDTO.setRoles(userRoles);

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
