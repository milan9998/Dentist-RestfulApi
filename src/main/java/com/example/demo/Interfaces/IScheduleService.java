package com.example.demo.Interfaces;


import com.example.demo.entities.SchedulePatient;
import com.example.demo.models.AppointmentModel;
import com.example.demo.models.CheckModel;
import com.example.demo.models.SchedulModel;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IScheduleService {
    void deleteById(Integer id);

    List<SchedulModel> getAllSchedulingsByDate(Date date);
    SchedulModel createSchedul(SchedulModel schedul) throws ParseException;

    List<CheckModel> getSchedulesByDateTimeDentistId(Date date, Time time, Integer dentist_id);

    List<AppointmentModel> getAllAppointmentTime();


}
