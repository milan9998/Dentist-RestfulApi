package com.example.demo.controllers;

import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
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

    @GetMapping("get-repairs-by-dentist")
    public List<DentistImportantModel> getRepairsByDentist(@RequestParam @Valid Integer dentist_id) {
        return dentistService.getAllImportant(dentist_id);
    }

    @PostMapping("schedule-patient")
    public ResponseEntity<?> schedulePatient(@RequestBody @Valid SchedulModel schedulModel, BindingResult result) {
        return ResponseEntity.ok(dentistService.createSchedul(schedulModel));
    }

    @DeleteMapping("delete-schedule")
    public ResponseEntity<?> deleteSchedule(Integer schedule_id) {
        scheduleService.deleteById(schedule_id);
        return ResponseEntity.ok("Deleted Schedule with id " + schedule_id);
    }



}
