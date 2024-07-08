package com.example.demo.services;

import com.example.demo.entities.DentalRepair;
import com.example.demo.entities.SchedulePatient;
import com.example.demo.entities.User;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.IRepairRepository;
import com.example.demo.repositories.IScheduleRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DentistService implements IDentistService {

    private final IRepairRepository repairRepository;
    private final IScheduleRepository scheduleRepository;
    private final IUserRepository userRepository;

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

        var x = repairRepository.getAllBydentist_id(dentist_id);

        return RepairMapper.toModelList(x);
    }

    @Override
    public List<DentistImportantModel> getAllImportant(Integer dentist_id) {

        var x = repairRepository.getAllBydentist_id(dentist_id);

        return RepairMapper.toModelImportantList(x);
    }

    @Override
    public SchedulModel createSchedul(SchedulModel schedul) {
        var x = userRepository.findById(schedul.getUser_id());

        if (!x.isPresent()) {
            User user = new User();
            user.setId(schedul.getUser_id());
            userRepository.save(user);
        }


        SchedulePatient patient = RepairMapper.toEntity(schedul);
        scheduleRepository.save(patient);
        return RepairMapper.toModel(patient);
    }





}
