package com.example.demo.services;

import com.example.demo.Interfaces.IScheduleService;
import com.example.demo.entities.SchedulePatient;
import com.example.demo.entities.User;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.AppointmentModel;
import com.example.demo.models.CheckModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.IScheduleRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {
    IScheduleRepository iScheduleRepository;
    IUserRepository userRepository;

   private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Override
    public void deleteById(Integer id) {
        iScheduleRepository.deleteById(id);
    }


    @Override
    public CompletableFuture<List<SchedulModel>> getAllSchedulingsByDate(Date date) {

        var all = iScheduleRepository.getAllSchedulingsByDate(date);

        return CompletableFuture.completedFuture(RepairMapper.toModelListAll(all));
    }

    @Override
    @Async
    public CompletableFuture<SchedulModel> createSchedul(SchedulModel schedule) throws ParseException {

        Optional<User> userMayExist = userRepository.findByEmail(schedule.getEmail());
        Date saveDateEntry = schedule.getDate();
        Date currentDate = new Date();


        //  1
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(formatter.format(schedule.getDate()));
        String timeString = schedule.getTime().toString();

        // 2

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(formatTime.parse(timeString).getTime());

      //  var allUsedAppointments = getAllAppointmentTime();
        CompletableFuture<List<AppointmentModel>> future = getAllAppointmentTime();
        List<AppointmentModel> allUsedAppointments = future.join();

        int minutes = time.getMinutes();


        if (minutes % 30 != 0) {
            throw new ParseException("You must enter the right time here is used appointments " + allUsedAppointments , 0);
        }



        //  1   2
        List<CheckModel> smth = getSchedulesByDateTimeDentistId(dates, time, schedule.getDentist_id());




        for (CheckModel checkModel : smth) {

            if (dates.equals(checkModel.getDate()) &&
                    time.equals(checkModel.getTime()) &&
                    checkModel.getDentist_id() == schedule.getDentist_id()
            ) {
                throw new IllegalArgumentException("You can not schedule in the same time, please schedule in another time" + "check here all used appointments "+allUsedAppointments + " or in another date " +
                        checkModel.getDate());
            }
        }




        if (saveDateEntry.before(currentDate)) {
            throw new IllegalArgumentException("Your can not schedule in the past");
        }
        User user = new User();
        if (!userMayExist.isPresent()) {
            user.setFirst_name(schedule.getFirst_name());
            user.setLast_name(schedule.getLast_name());
            user.setEmail(schedule.getEmail());
            user.setContact_number(schedule.getContact_number());
            userRepository.save(user);
            logger.info("Created new user " + user.getFirst_name() + " " + user.getLast_name() + " " + user.getEmail());
        } else {
            user = userMayExist.get();
        }
        schedule.setUser_id(user.getId());
        SchedulePatient patient = RepairMapper.toEntity(schedule);
        iScheduleRepository.save(patient);

        return CompletableFuture.completedFuture(RepairMapper.toModel(patient));

    }


    @Override
    public List<CheckModel> getSchedulesByDateTimeDentistId(Date date, Time time, Integer dentist_id) {
        var schedules = iScheduleRepository.getAllByDateTimeDentistId(date, time, dentist_id);
        return RepairMapper.toModelCheckList(schedules);
    }


    @Override
    @Async
    public CompletableFuture<List<AppointmentModel>> getAllAppointmentTime() {
        var allusedAppointments = iScheduleRepository.getAllAppointmentTime();

        return CompletableFuture.completedFuture(RepairMapper.toTimeModelList(allusedAppointments));
    }


}
