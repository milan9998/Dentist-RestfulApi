package com.example.demo.Interfaces;

import com.example.demo.entities.Dentist;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IDentistService {
    CompletableFuture<List<RepairModel>> getAllRepairs();



    CompletableFuture<List<DentistImportantModel>> getAllInformationsByDentistid(Integer dentistId);



    ResponseEntity<?> confirmEmail(String confirmationToken);


    

}
