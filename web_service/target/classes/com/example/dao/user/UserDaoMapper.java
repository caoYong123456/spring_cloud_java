package com.example.dao.user;

import com.example.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDaoMapper {

    User queryUser(@Param("userName") String userName, @Param("password") String password);
}
