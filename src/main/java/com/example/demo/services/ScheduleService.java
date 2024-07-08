package com.example.demo.services;

import com.example.demo.repositories.IScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {
    IScheduleRepository iScheduleRepository;

    @Override
    public void deleteById(Integer id) {
        iScheduleRepository.deleteById(id);
    }
}
