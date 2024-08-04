package com.example.demo.Interfaces;

import com.example.demo.models.RepairModel;
import com.example.demo.models.UserModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserService {
    UserModel getUserById(int id);

    UserModel getUserByEmail(String email);

    UserModel getUserByUsername(String username);

    UserModel createUser(UserModel user);

    UserModel updateUser(UserModel user);

    void delete(Integer user_id);

    List<UserModel> getAllUsers();

    CompletableFuture<RepairModel> createRepair(RepairModel repairModel);



}
