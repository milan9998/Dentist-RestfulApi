package com.example.demo.services;

import com.example.demo.mappers.RepairMapper;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DateModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.repositories.IScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {
    IScheduleRepository iScheduleRepository;

    @Override
    public void deleteById(Integer id) {
        iScheduleRepository.deleteById(id);
    }



    @Override
    public List<SchedulModel> getAllSchedulingsByDate(Date date) {

        var all = iScheduleRepository.getAllSchedulingsByDate(date);

        return RepairMapper.toModelListAll(all);
    }


}
