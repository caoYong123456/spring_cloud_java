package com.example.service.user;

import com.example.entity.test.TestEntity;
import com.example.entity.user.User;

import java.util.List;

public interface UserService {

    User queryUser(String userName, String password);

    User getById(Integer id);

    List<User> getList(User user);

    void save(User user);

    void update(User user);
}
