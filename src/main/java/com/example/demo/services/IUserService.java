package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface IUserService {
    UserModel getUserById(int id);

    UserModel getUserByEmail(String email);

    UserModel getUserByUsername(String username);

    UserModel createUser(UserModel user);

    UserModel updateUser(UserModel user);

    void delete(Integer user_id);

    List<UserModel> getAllUsers();
}
