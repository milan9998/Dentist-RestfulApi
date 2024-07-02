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
}
