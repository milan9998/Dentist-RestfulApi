package com.example.demo.controllers;

import com.example.demo.Interfaces.IDentistService;
import com.example.demo.Interfaces.IScheduleService;
import com.example.demo.Interfaces.IUserService;
import com.example.demo.entities.Dentist;
import com.example.demo.models.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

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
    public ResponseEntity<?> deleteUser(@RequestParam Integer user_id) {
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
    @GetMapping("get-all-used-appointments")
    public List<AppointmentModel> getAllAppointments() {
        return scheduleService.getAllAppointmentTime();
    }

    @GetMapping("get-repairs-by-dentist-id")
    public List<DentistImportantModel> getRepairsByDentistId(@RequestParam @Valid Integer dentist_id) {
        return dentistService.getAllInformationsByDentistid(dentist_id);
    }

    @PostMapping("schedule-patient")
    public ResponseEntity<?> schedulePatient(@RequestBody @Valid SchedulModel schedulModel, BindingResult result) {

        try {
            CompletableFuture<SchedulModel> future = scheduleService.createSchedul(schedulModel);
            SchedulModel resultModel = future.join(); // Wait for completion and get the result

            return ResponseEntity.ok(resultModel);
        } catch (CompletionException e) {
            // Handle the exception
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(cause.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cause.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("delete-schedule")
    public ResponseEntity<?> deleteSchedule(@RequestParam Integer schedule_id) {
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
