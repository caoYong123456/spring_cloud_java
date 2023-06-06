package com.example.dao.user.menu;

import com.example.entity.user.Menu;
import com.example.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMenuDaoMapper {

    List<Menu> menuList(Menu menu);
}
