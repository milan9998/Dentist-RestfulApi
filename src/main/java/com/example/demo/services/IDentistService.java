package com.example.demo.services;

import com.example.demo.entities.SchedulePatient;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;

import java.util.List;

public interface IDentistService {
    List<RepairModel> getAllRepairs();

    List<RepairModel> getByDentist_id(Integer dentist_id);

    List<DentistImportantModel> getAllImportant(Integer dentistId);
    // List<RepairModel> getByDentist_id(Integer dentist_id);
    SchedulModel createSchedul(SchedulModel schedul);

    List<CheckModel> getAllNeeded();




    

}
