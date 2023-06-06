package com.example.service.user.menu;

import com.example.dao.user.UserDaoMapper;
import com.example.dao.user.menu.UserMenuDaoMapper;
import com.example.entity.user.Menu;
import com.example.entity.user.User;
import com.example.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMenuServiceImpl implements UserMenuService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMenuDaoMapper userMenuDaoMapper;

    @Override
    public List<Menu> menuList(Menu menu) {
        return userMenuDaoMapper.menuList(menu);
    }


}
