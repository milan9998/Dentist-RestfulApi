package com.example.demo.services;

import com.example.demo.entities.Dentist;
import com.example.demo.entities.DentistRoles;
import com.example.demo.entities.Token;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.DentistModel;
import com.example.demo.models.LoginDentistModel;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LogoutRequestModel;
import com.example.demo.repositories.IDentistRepository;
import com.example.demo.repositories.IRoleRepository;
import com.example.demo.repositories.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IDentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ITokenRepository tokenRepository;
    private final IRoleRepository roleRepository;


    public DentistModel signUp(DentistModel dentistModel) {
        var newUser = UserMapper.toEntity(dentistModel, passwordEncoder);

        var userMayExist = dentistRepository.findByEmail(dentistModel.getEmail());
        if (userMayExist.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        //new mapper may be needed for insert
        var saveUser = dentistRepository.save(newUser);

        Dentist dentist = new Dentist();

        List<Dentist> allDentist = new ArrayList<>();
        allDentist = dentistRepository.findAll();
        //setting the dentist role for every new dentist signed up
        DentistRoles dentistRoles = new DentistRoles();

        for (Dentist d : allDentist) {
            dentistRoles.setDentist_id(d.getId());
            dentistRoles.setRole_id(1);
            roleRepository.save(dentistRoles);
        }


        return UserMapper.toModel(saveUser);
    }

    public LoginResponseModel authenticate(LoginDentistModel loginDentistModel) throws Throwable {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginDentistModel.getEmail(),
                        loginDentistModel.getPassword()
                ));

        var authenticatedUser = dentistRepository.findByEmail(loginDentistModel.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + loginDentistModel.getEmail() + " not found!"));

        String jwtToken = jwtService.generateToken((Dentist) authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken((Dentist) authenticatedUser);

        //saving token in db
        Token token = new Token();
        token.setToken(jwtToken);
        token.setRefreshToken(refreshToken);
        token.setDentist((Dentist) authenticatedUser);
        tokenRepository.save(token);

        return LoginResponseModel.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public void Logout(LogoutRequestModel id) {

        int y = id.getId();
        var x = tokenRepository.revokeTokens(y);
    }



}

