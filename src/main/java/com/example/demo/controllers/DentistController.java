package com.example.demo.controllers;

import com.example.demo.Interfaces.IDentistService;
import com.example.demo.Interfaces.IScheduleService;
import com.example.demo.Interfaces.IUserService;
import com.example.demo.models.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("dentist")
@RequiredArgsConstructor
public class DentistController {
    private final IUserService userService;
    private final IDentistService dentistService;
    private final IScheduleService scheduleService;

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserModel userModel, BindingResult result) {
        return ResponseEntity.
                ok(userService.createUser(userModel));
    }

    @DeleteMapping("user-delete")
    public ResponseEntity<?> deleteUser(Integer user_id) {
        userService.delete(user_id);
        return ResponseEntity.ok("Deleted user with id " + user_id);
    }

    @GetMapping("get-all-patients")
    public List<UserModel> getAllPatients() {
        return userService.getAllUsers();
    }

    @PostMapping("create-user-repair")
    public ResponseEntity<?> createUserRepair(@RequestBody @Valid RepairModel repairModel, BindingResult result) {

        return ResponseEntity.ok(userService.createRepair(repairModel));
    }

    @GetMapping("get-all-user-repairs")
    public List<RepairModel> getAllUserRepairs() {
        return dentistService.getAllRepairs();
    }

    @GetMapping("get-repairs-by-dentist-id")
    public List<DentistImportantModel> getRepairsByDentistId(@RequestParam @Valid Integer dentist_id) {
        return dentistService.getAllInformationsByDentistid(dentist_id);
    }

    @PostMapping("schedule-patient")
    public ResponseEntity<?> schedulePatient(@RequestBody @Valid SchedulModel schedulModel, BindingResult result) throws ParseException {
        return ResponseEntity.ok(scheduleService.createSchedul(schedulModel));
    }


    @DeleteMapping("delete-schedule")
    public ResponseEntity<?> deleteSchedule(Integer schedule_id) {
        scheduleService.deleteById(schedule_id);
        return ResponseEntity.ok("Deleted Schedule with id " + schedule_id);
    }

    @GetMapping("get-all-schedulings-by-date")
    public List<SchedulModel> getAllSchedulingByDate(@RequestBody @Valid DateModel date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = sdf.parse(sdf.format(date.getDate()));

        return scheduleService.getAllSchedulingsByDate(dates);
    }



}
