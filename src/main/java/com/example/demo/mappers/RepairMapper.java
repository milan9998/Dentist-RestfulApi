package com.example.demo.mappers;

import com.example.demo.entities.DentalRepair;
import com.example.demo.entities.SchedulePatient;
import com.example.demo.models.CheckModel;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;
import com.example.demo.models.SchedulModel;

import java.util.ArrayList;
import java.util.List;

public class RepairMapper {
    public static DentalRepair toEntity(RepairModel repairModel) {
        DentalRepair dentalRepair = new DentalRepair();

        dentalRepair.setId(repairModel.getId());
        dentalRepair.setName_of_repair(repairModel.getName_of_repair());
        dentalRepair.setPrice(repairModel.getPrice());
        dentalRepair.setDentist_id(repairModel.getDentist_id());
        dentalRepair.setUser_id(repairModel.getUser_id());

        return dentalRepair;
    }




    public static SchedulePatient toEntity(SchedulModel model) {
        SchedulePatient schedulePatient = new SchedulePatient();
        schedulePatient.setUser_id(model.getUser_id());
        schedulePatient.setDentist_id(model.getDentist_id());
        schedulePatient.setDate(model.getDate());
        schedulePatient.setTime(model.getTime());
        return schedulePatient;
    }
    public static SchedulModel toModel(SchedulePatient schedulePatient) {

        return SchedulModel.builder().
                user_id(schedulePatient.getUser_id()).
                dentist_id(schedulePatient.getDentist_id()).
                date(schedulePatient.getDate()).
                time(schedulePatient.getTime()).
                build();

    }
    public static List<SchedulModel> toModelListAll(List<SchedulePatient> schedulePatient){
        List<SchedulModel> list = new ArrayList<>();
        for (SchedulePatient schedulePatient1: schedulePatient) {
            list.add(toModel(schedulePatient1));
        }
        return list;
    }


    public static List<RepairModel> toModelList(List<DentalRepair> dentalRepairs) {
        List<RepairModel> repairModels = new ArrayList<>();
        for (DentalRepair dentalRepair : dentalRepairs) {
            repairModels.add(toModel(dentalRepair));
        }
        return repairModels;

    }



    public static DentistImportantModel toImportantModel(DentalRepair dentalRepair) {

        return DentistImportantModel.builder().

                name_of_repair(dentalRepair.
                        getName_of_repair()).
                        user_id(dentalRepair.getUser_id()).
                        price(dentalRepair.getPrice()).
                        build();
    }

    public static RepairModel toModel(DentalRepair dentalRepair) {


        return RepairModel.builder().
                id(dentalRepair.getId()).
                name_of_repair(dentalRepair.getName_of_repair()).
                price(dentalRepair.getPrice()).
                dentist_id(dentalRepair.getDentist_id()).
                user_id(dentalRepair.getUser_id()).build();
    }

    public static CheckModel toModelCheck(SchedulePatient schedulePatient) {

        return CheckModel.builder().
                dentist_id(schedulePatient.getDentist_id()).
                date(schedulePatient.getDate()).
                time(schedulePatient.getTime()).build();
    }

    public static List<CheckModel> toModelCheckList(List<SchedulePatient> schedulePatient) {
        List<CheckModel> list = new ArrayList<>();
        for (SchedulePatient schedulePatient1: schedulePatient) {
            list.add(toModelCheck(schedulePatient1));

        }
        return list;
    }




    public static List<DentistImportantModel> toModelImportantList(List<DentalRepair> dentalRepairs) {
        List<DentistImportantModel> dentistImportantModels = new ArrayList<>();
        for (DentalRepair dentalRepair : dentalRepairs) {
            dentistImportantModels.add(toImportantModel(dentalRepair));
        }
        return  dentistImportantModels;
    }

}
