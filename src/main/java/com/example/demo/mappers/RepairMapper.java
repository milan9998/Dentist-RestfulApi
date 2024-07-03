package com.example.demo.mappers;

import com.example.demo.entities.DentalRepair;
import com.example.demo.models.RepairModel;

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
}
