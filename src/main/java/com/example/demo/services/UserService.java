package com.example.demo.services;

import com.example.demo.Interfaces.IUserService;
import com.example.demo.entities.DentalRepair;
import com.example.demo.entities.User;
import com.example.demo.mappers.RepairMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.RepairModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.IRepairRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final IRepairRepository repairRepository;

    @Async
    @Override
    public CompletableFuture<List<UserModel>> getAllUsers() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        return CompletableFuture.completedFuture(UserMapper.toUserModelList(users));
    }

    @Override
    @Async
    public CompletableFuture<RepairModel> createRepair(RepairModel repairModel) {
        DentalRepair repair = RepairMapper.toEntity(repairModel);
        var saved = repairRepository.save(repair);
        return CompletableFuture.completedFuture(RepairMapper.toModel(saved));
    }



    @Override
    public UserModel getUserById(int id) {
        return null;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return null;
    }

    @Override
    public CompletableFuture<UserModel> createUser(UserModel model) {
        var user = UserMapper.toEntity(model, passwordEncoder);

        var alreadyExists = userRepository.findByEmail(model.getEmail());
        if (alreadyExists.isPresent()) {
            throw new IllegalArgumentException("Username already exists with this email :" + model.getEmail());
        }

        var savedUser = userRepository.save(user);
        return CompletableFuture.completedFuture(UserMapper.toModel(savedUser));
    }

    @Override
    public UserModel updateUser(UserModel user) {
        return null;
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(Integer user_id) {
        userRepository.deleteById(user_id);
    return CompletableFuture.completedFuture(null);
    }


}
