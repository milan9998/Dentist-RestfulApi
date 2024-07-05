package com.example.demo.services;

import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;

import java.util.List;

public interface IDentistService {
    List<RepairModel> getAllRepairs();

    List<RepairModel> getByDentist_id(Integer dentist_id);

    List<DentistImportantModel> getAllImportant(Integer dentistId);
    // List<RepairModel> getByDentist_id(Integer dentist_id);
    

}
