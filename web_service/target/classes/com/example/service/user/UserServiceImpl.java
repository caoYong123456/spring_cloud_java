package com.example.service.user;

import com.example.dao.user.UserDaoMapper;
import com.example.entity.test.TestEntity;
import com.example.entity.user.User;
import com.example.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDaoMapper userDaoMapper;

    @Override
    public User queryUser(String userName, String password) {
        return userDaoMapper.queryUser(userName, password);
    }

    @Override
    public User getById(Integer id){
        return userDaoMapper.getById(id);
    }

    public List<User> getList(User user){
        return userDaoMapper.getList(user);
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }


}
