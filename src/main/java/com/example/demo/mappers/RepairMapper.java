package com.example.demo.mappers;

import com.example.demo.entities.DentalRepair;
import com.example.demo.models.DentistImportantModel;
import com.example.demo.models.RepairModel;

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

    public static DentistImportantModel toImportantModel(DentalRepair dentalRepair) {
        DentalRepair dentalRepairToModel = new DentalRepair();

        dentalRepairToModel.setUser_id(dentalRepair.getUser_id());
        dentalRepairToModel.setName_of_repair(dentalRepair.getName_of_repair());
        dentalRepairToModel.setPrice(dentalRepair.getPrice());


        return DentistImportantModel.builder().

                name_of_repair(dentalRepair.
                        getName_of_repair()).
                        user_id(dentalRepair.getUser_id()).
                        price(dentalRepair.getPrice()).
                        build();
    }

    public static RepairModel toModel(DentalRepair dentalRepair) {
        DentalRepair dentalRepairToModel = new DentalRepair();

        dentalRepairToModel.setId(dentalRepair.getId());
        dentalRepairToModel.setName_of_repair(dentalRepair.getName_of_repair());
        dentalRepairToModel.setPrice(dentalRepair.getPrice());
        dentalRepairToModel.setDentist_id(dentalRepair.getDentist_id());
        dentalRepairToModel.setUser_id(dentalRepair.getUser_id());

        return RepairModel.builder().
                id(dentalRepair.getId()).
                name_of_repair(dentalRepair.getName_of_repair()).
                price(dentalRepair.getPrice()).
                dentist_id(dentalRepair.getDentist_id()).
                user_id(dentalRepair.getUser_id()).build();
    }

    public static List<RepairModel> toModelList(List<DentalRepair> dentalRepairs) {
        List<RepairModel> repairModels = new ArrayList<>();
        for (DentalRepair dentalRepair : dentalRepairs) {
            repairModels.add(toModel(dentalRepair));
        }
        return repairModels;

    }

    public static List<DentistImportantModel> toModelImportantList(List<DentalRepair> dentalRepairs) {
        List<DentistImportantModel> dentistImportantModels = new ArrayList<>();
        for (DentalRepair dentalRepair : dentalRepairs) {
            dentistImportantModels.add(toImportantModel(dentalRepair));
        }
        return  dentistImportantModels;
    }

}
