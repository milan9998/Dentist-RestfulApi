package com.example.demo.services;

import ch.qos.logback.classic.Logger;
import com.example.demo.entities.DentalRepair;
import com.example.demo.entities.SchedulePatient;
import com.example.demo.entities.User;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.IRepairRepository;
import com.example.demo.repositories.IScheduleRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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
    public List<CheckModel> getAllNeeded(Date date, Time time, Integer dentist_id) {
        var x = scheduleRepository.getAllNeeded(date, time, dentist_id);
        //  System.out.println(x.size());
        return RepairMapper.toModelCheckList(x);
    }

    @Override
    public SchedulModel createSchedul(SchedulModel schedul) throws ParseException {
        //   var x = userRepository.findById(schedul.getUser_id());
        Optional<User> x = userRepository.findByEmail(schedul.getEmail());


        Date y = schedul.getDate();
        Date z = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(formatter.format(schedul.getDate()));
        String timeString = schedul.getTime().toString();

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(formatTime.parse(timeString).getTime());


        List<CheckModel> smth = getAllNeeded(dates, time, schedul.getDentist_id());


        for (CheckModel checkModel : smth) {

            if (dates.equals(checkModel.getDate()) &&
                    time.equals(time) &&
                    checkModel.getDentist_id() == schedul.getDentist_id()) {
                throw new IllegalArgumentException("You can not schedule in the same time, please schedule in another time");
            }
        }


        if (y.before(z)) {
            throw new IllegalArgumentException("Your can not schedule in the past");
        }
        System.out.println(smth.size());


        User user = new User();
        //if user does not exist
        if (!x.isPresent()) {


            user.setFirst_name(schedul.getFirst_name());
            user.setLast_name(schedul.getLast_name());
            user.setEmail(schedul.getEmail());
            user.setContact_number(schedul.getContact_number());
            userRepository.save(user);

        } else {
            user = x.get();
        }
        schedul.setUser_id(user.getId());


        //if is emtpy

        SchedulePatient patient = RepairMapper.toEntity(schedul);
        scheduleRepository.save(patient);
        return RepairMapper.toModel(patient);

    }


 /*   @Override
    public List<CheckModel> getAllNeeded(Date date, Time time, Integer dentist_id) {
        var x = scheduleRepository.getAllNeeded();

        return RepairMapper.toModelCheckList(x);
    }*/


}
    /*      for (CheckModel cm : smth) {
            if (cm.getDentist_id() == schedul.getDentist_id() &&
                    cm.getDate().equals(schedul.getDate()) &&
                    cm.getTime().equals(schedul.getTime()))   {

                throw new IllegalArgumentException("That time is already in the schedule");
            }
        } */
