package com.example.demo.services;

import com.example.demo.Interfaces.IDentistService;
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
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<DentistImportantModel> getAllInformationsByDentistid(Integer dentist_id) {

        var x = repairRepository.getAllBydentist_id(dentist_id);

        return RepairMapper.toModelImportantList(x);
    }

    @Override
    public List<CheckModel> getSchedulesByDateTimeDentistId(Date date, Time time, Integer dentist_id) {
        var schedules = scheduleRepository.getAllByDateTimeDentistId(date, time, dentist_id);
        return RepairMapper.toModelCheckList(schedules);
    }

    @Override
    public SchedulModel createSchedul(SchedulModel schedul) throws ParseException {
        //   var x = userRepository.findById(schedul.getUser_id());
        Optional<User> userMayExist = userRepository.findByEmail(schedul.getEmail());
        Date saveDateEntry = schedul.getDate();
        Date currentDate = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(formatter.format(schedul.getDate()));
        String timeString = schedul.getTime().toString();

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(formatTime.parse(timeString).getTime());

        List<CheckModel> smth = getSchedulesByDateTimeDentistId(dates, time, schedul.getDentist_id());

        for (CheckModel checkModel : smth) {

            if (dates.equals(checkModel.getDate()) &&
                    time.equals(time) &&
                    checkModel.getDentist_id() == schedul.getDentist_id()) {
                throw new IllegalArgumentException("You can not schedule in the same time, please schedule in another time" + checkModel.getTime() + " at the same time" +
                        checkModel.getDate());
            }
        }
        if (saveDateEntry.before(currentDate)) {
            throw new IllegalArgumentException("Your can not schedule in the past");
        }

        User user = new User();

        if (!userMayExist.isPresent()) {
            user.setFirst_name(schedul.getFirst_name());
            user.setLast_name(schedul.getLast_name());
            user.setEmail(schedul.getEmail());
            user.setContact_number(schedul.getContact_number());
            userRepository.save(user);

        } else {
            user = userMayExist.get();
        }
        schedul.setUser_id(user.getId());
        SchedulePatient patient = RepairMapper.toEntity(schedul);
        scheduleRepository.save(patient);
        return RepairMapper.toModel(patient);

    }
}
