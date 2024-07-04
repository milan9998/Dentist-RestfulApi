package com.example.demo.services;

import com.example.demo.entities.DentalRepair;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.RepairModel;
import com.example.demo.repositories.IRepairRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DentistService implements IDentistService {
    private final IRepairRepository repairRepository;

    public List<RepairModel> getAllRepairs() {
        List<DentalRepair> repairs = repairRepository.findAll();

        return RepairMapper.toModelList(repairs);
    }

    @Override
    public List<RepairModel> getByDentist_id(Integer dentist_id) {
        var x = getAllBydentist_id(dentist_id);
        return x;
    }


    public List<RepairModel> getAllBydentist_id(Integer dentist_id) {
        List<RepairModel> repairModels = new ArrayList<>();
        var  x = repairRepository.getAllBydentist_id(dentist_id);

        return RepairMapper.toModelList(x);
    }


}
