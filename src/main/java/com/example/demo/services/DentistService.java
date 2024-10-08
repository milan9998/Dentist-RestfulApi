package com.example.demo.services;

import com.example.demo.Interfaces.IDentistService;
import com.example.demo.Interfaces.IMailService;
import com.example.demo.entities.*;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class DentistService implements IDentistService {

    private final IRepairRepository repairRepository;
    private final IScheduleRepository scheduleRepository;
    private final IUserRepository userRepository;
    private final IConfirmationRepository confirmationRepository;
    private final IDentistRepository dentistRepository;
    private final IMailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationRepository.findByConfirmationToken(confirmationToken);
        if (token != null && !token.getDentist().isEnabled()) {
            Dentist dentist = token.getDentist();
            dentist.setEnabled(true);
            dentistRepository.save(dentist);
            return ResponseEntity.ok("Email verified successfully");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    @Override
    public ResponseEntity<?> confirmNewPassword(String password,String email) {

         dentistRepository.resetPassword(email,passwordEncoder.encode(password));

        return ResponseEntity.ok("Password verified successfully");
    }


    @Override
    public ResponseEntity<?> confirmRessetPassword(String email) {
        var userMayExist = dentistRepository.findByEmail(email);
        if (!userMayExist.isPresent()) {
            throw new IllegalArgumentException("Email not found");
        }
        SimpleMailMessage mailMessage = null;

        try{
            mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Reset Password");
            mailMessage.setText("To confirm your account, please click here: "
                    + "http://localhost:8080/auth/show-reset-password?email=" + email);
            mailService.sendEmail(mailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok("Reset password has sent to your email");
    }



    @Override
    @Async
    @Transactional
    public CompletableFuture<List<RepairModel>> getAllRepairs() {
        List<DentalRepair> repairs = repairRepository.findAll();

        return CompletableFuture.completedFuture(RepairMapper.toModelList(repairs));
    }

    @Override
    @Async
    public CompletableFuture<List<DentistImportantModel>> getAllInformationsByDentistid(Integer dentist_id) {

        var x = repairRepository.getAllBydentist_id(dentist_id);

        return CompletableFuture.completedFuture(RepairMapper.toModelImportantList(x));
    }




}
