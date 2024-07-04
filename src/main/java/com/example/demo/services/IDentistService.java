package com.example.demo.services;

import com.example.demo.models.RepairModel;

import java.util.List;

public interface IDentistService {
    List<RepairModel> getAllRepairs();

    List<RepairModel> getByDentist_id(Integer dentist_id);
    // List<RepairModel> getByDentist_id(Integer dentist_id);

}
