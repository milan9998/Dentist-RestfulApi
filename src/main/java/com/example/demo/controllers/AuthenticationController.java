package com.example.demo.controllers;

import com.example.demo.entities.Dentist;
import com.example.demo.models.DentistModel;
import com.example.demo.models.LoginDentistModel;
import com.example.demo.models.LoginResponseModel;
import com.example.demo.models.LogoutRequestModel;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.DentistService;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.text.ParseException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final DentistService dentistService;


    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        return dentistService.confirmEmail(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        dentistService.confirmRessetPassword(email);
        return ResponseEntity.ok("Check your email");
    }
    @GetMapping("/show-reset-password")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("reset-password");
        return modelAndView;
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        dentistService.confirmNewPassword(password, email);
        return ResponseEntity.ok("Password reset successful");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody DentistModel dentistModel) {
        try{
            CompletableFuture<ResponseEntity<String>> future = authenticationService.signUp(dentistModel);
            ResponseEntity<String> dentistModel1 = future.join();
            return ResponseEntity.status(HttpStatus.CREATED).body(dentistModel1);
        }catch (CompletionException e) {
            // Handle the exception
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(cause.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cause.getMessage());

        }
        //  return ResponseEntity.ok(authenticationService.signUp(dentistModel));


    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDentistModel dentistModel) throws Throwable {

        try{
            CompletableFuture<LoginResponseModel> future = authenticationService.authenticate(dentistModel);
            LoginResponseModel loginResponseModel = future.join();
            return ResponseEntity.ok(loginResponseModel);
        }catch (CompletionException e) {
            // Handle the exception
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(cause.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cause.getMessage());

        }catch (ParseException e){
            throw new RuntimeException(e);
        }





    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestModel x) {
        var z = x.getId();
       authenticationService.logout(x);
        return ResponseEntity.ok("Logout successful");
    }



}
