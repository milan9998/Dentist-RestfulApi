package com.example.demo.services;

import com.example.demo.Interfaces.IMailService;
import com.example.demo.entities.ConfirmationToken;
import com.example.demo.entities.Dentist;
import com.example.demo.entities.DentistRoles;
import com.example.demo.entities.Token;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.DentistModel;
import com.example.demo.models.LoginDentistModel;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LogoutRequestModel;
import com.example.demo.repositories.IConfirmationRepository;
import com.example.demo.repositories.IDentistRepository;
import com.example.demo.repositories.IRoleRepository;
import com.example.demo.repositories.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//
@Service
@RequiredArgsConstructor
public class AuthenticationService  {
    private final IDentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ITokenRepository tokenRepository;
    private final IRoleRepository roleRepository;
    private final IConfirmationRepository confirmationRepository;
    private final IMailService mailService;


    @Async
    public CompletableFuture<ResponseEntity<String>> signUp(DentistModel dentistModel) {
        var newUser = UserMapper.toEntity(dentistModel, passwordEncoder);

        var userMayExist = dentistRepository.findByEmail(dentistModel.getEmail());
        if (userMayExist.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        var saveUser = dentistRepository.save(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(saveUser);
        confirmationRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = null;
        try {

            mailMessage = new SimpleMailMessage();
            mailMessage.setTo(saveUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
            mailService.sendEmail(mailMessage);


        } catch (Exception e) {
            e.printStackTrace();
        }


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

        return CompletableFuture.completedFuture(ResponseEntity.ok("To confirm your account, please check your e-mail address!"));
    }
    @Async
    public CompletableFuture<LoginResponseModel> authenticate(LoginDentistModel loginDentistModel) throws Throwable {
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

        return CompletableFuture.completedFuture(LoginResponseModel.builder().token(jwtToken).refreshToken(refreshToken).build());
    }

    @Async
    public CompletableFuture<Void> logout(LogoutRequestModel id) {

        int y = id.getId();
        var x = tokenRepository.revokeTokens(y);
        return null;
    }



}

