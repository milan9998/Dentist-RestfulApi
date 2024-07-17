package com.example.demo.Interface;

import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IDentistService {
    List<RepairModel> getAllRepairs();

    List<RepairModel> getByDentist_id(Integer dentist_id);

    List<DentistImportantModel> getAllImportant(Integer dentistId);
    // List<RepairModel> getByDentist_id(Integer dentist_id);
    SchedulModel createSchedul(SchedulModel schedul) throws ParseException;

    List<CheckModel> getAllNeeded(Date date, Time time, Integer dentist_id);




    

}
