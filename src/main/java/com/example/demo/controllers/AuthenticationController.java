package com.example.demo.controllers;

import com.example.demo.models.DentistModel;
import com.example.demo.models.LoginDentistModel;
import com.example.demo.models.LogoutRequestModel;
import com.example.demo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody DentistModel dentistModel) {
        return ResponseEntity.ok(authenticationService.signUp(dentistModel));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDentistModel dentistModel) throws Throwable {
        return ResponseEntity.ok(authenticationService.authenticate(dentistModel));
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestModel x) {
       authenticationService.logout(x);
        return ResponseEntity.ok("Logout successful");
    }
}
