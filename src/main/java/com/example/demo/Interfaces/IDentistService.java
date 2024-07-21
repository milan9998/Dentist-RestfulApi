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

public interface IDentistService {
    List<RepairModel> getAllRepairs();

    List<RepairModel> getByDentist_id(Integer dentist_id);

    List<DentistImportantModel> getAllInformationsByDentistid(Integer dentistId);
    // List<RepairModel> getByDentist_id(Integer dentist_id);
    SchedulModel createSchedul(SchedulModel schedul) throws ParseException;

    List<CheckModel> getSchedulesByDateTimeDentistId(Date date, Time time, Integer dentist_id);

    ResponseEntity<?> confirmEmail(String confirmationToken);


    

}
