package com.example.demo.mappers;

import com.example.demo.entities.Dentist;
import com.example.demo.entities.User;
import com.example.demo.models.DentistModel;
import com.example.demo.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User toEntity(UserModel userModel, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setId(userModel.getId());
        user.setFirst_name(userModel.getFirst_name());
        user.setLast_name(userModel.getLast_name());
        user.setEmail(userModel.getEmail());
        user.setContact_number(userModel.getContact_number());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        return user;
    }

    public static Dentist toEntity(DentistModel dentistModel, PasswordEncoder passwordEncoder) {
        Dentist dentist = new Dentist();
        dentist.setEmail(dentistModel.getEmail());
        dentist.setPassword(passwordEncoder.encode(dentistModel.getPassword()));

        return dentist;
    }

    public static DentistModel toModel(Dentist dentist) {
        return DentistModel.builder().
                email(dentist.getEmail()).

                build();
    }

    public static UserModel toModel(User entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setFirst_name(entity.getFirst_name());
        user.setContact_number(entity.getContact_number());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setLast_name(entity.getLast_name());

        return UserModel.builder().id(entity.getId()).
                first_name(entity.getFirst_name()).
                last_name(entity.getLast_name()).
                email(entity.getEmail()).
                password(entity.getPassword()).
                contact_number(entity.getContact_number()).
                build();

    }

    public static List<UserModel> toUserModelList(List<User> entities) {
        var list = new ArrayList<UserModel>();
        for (var entity : entities) {
            list.add(toModel(entity));
        }
        return list;
    }
}
