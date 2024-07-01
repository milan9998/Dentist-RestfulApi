package com.example.demo.services;

import com.example.demo.entities.Dentist;
import com.example.demo.entities.Token;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.DentistModel;
import com.example.demo.models.LoginDentistModel;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.repositories.IDentistRepository;
import com.example.demo.repositories.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IDentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ITokenRepository tokenRepository;

    public DentistModel signUp(DentistModel dentistModel) {
        var newUser = UserMapper.toEntity(dentistModel,passwordEncoder);

        var userMayExist = dentistRepository.findByEmail(dentistModel.getEmail());
        if(userMayExist.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        var saveUser = dentistRepository.save(newUser);

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
        Token token = new Token();
        token.setToken(jwtToken);
        token.setRefreshToken(refreshToken);
        token.setDentist((Dentist) authenticatedUser); // Assuming Token entity has a relationship with User/Dentist entity
        tokenRepository.save(token);



        return LoginResponseModel.builder().token(jwtToken).refreshToken(refreshToken).build();




    }

}
