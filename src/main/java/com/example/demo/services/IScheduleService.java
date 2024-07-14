package com.example.demo.services;


import com.example.demo.models.DateModel;
import com.example.demo.models.SchedulModel;

import java.util.Date;
import java.util.List;

public interface IScheduleService {
    void deleteById(Integer id);

    List<SchedulModel> getAllSchedulingsByDate(Date date);


}
