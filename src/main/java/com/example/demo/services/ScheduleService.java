package com.example.demo.services;

import com.example.demo.Interfaces.IScheduleService;
import com.example.demo.entities.SchedulePatient;
import com.example.demo.entities.User;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.CheckModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.IScheduleRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {
    IScheduleRepository iScheduleRepository;
    IUserRepository userRepository;

    @Override
    public void deleteById(Integer id) {
        iScheduleRepository.deleteById(id);
    }



    @Override
    public List<SchedulModel> getAllSchedulingsByDate(Date date) {

        var all = iScheduleRepository.getAllSchedulingsByDate(date);

        return RepairMapper.toModelListAll(all);
    }

    @Override
    public SchedulModel createSchedul(SchedulModel schedul) throws ParseException {
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
        iScheduleRepository.save(patient);
        return RepairMapper.toModel(patient);

    }


    @Override
    public List<CheckModel> getSchedulesByDateTimeDentistId(Date date, Time time, Integer dentist_id) {
        var schedules = iScheduleRepository.getAllByDateTimeDentistId(date, time, dentist_id);
        return RepairMapper.toModelCheckList(schedules);
    }


}
