package com.example.dao.user;

import com.example.entity.user.Menu;
import com.example.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDaoMapper {

    User getById(Integer id);

    User queryUser(@Param("userName") String userName, @Param("password") String password);

    List<User> getList(User user);

    void save(User user);

    void update(User user);

    void delete(Integer id);

    List<Menu> getMenuList();

    List<Menu> getChildrenMenuList(@Param("parentId") Integer parentId);
}
