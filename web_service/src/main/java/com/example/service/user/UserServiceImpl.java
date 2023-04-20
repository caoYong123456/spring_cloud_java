package com.example.service.user;

import com.example.dao.user.UserDaoMapper;
import com.example.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDaoMapper userDaoMapper;

    @Override
    public String login(String userName, String password) {
        try {
            //校验用户名和密码
            User user = userDaoMapper.queryUser(userName, password);
            if (user == null) {
                logger.info("用户账号密码错误");
                return null;
            }
            //生成token
            String token = "test-token";
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
