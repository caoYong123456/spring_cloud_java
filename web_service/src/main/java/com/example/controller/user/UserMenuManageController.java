package com.example.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.constant.AjaxResult;
import com.example.entity.test.TestEntity;
import com.example.entity.user.Menu;
import com.example.entity.user.Meta;
import com.example.entity.user.User;
import com.example.service.user.UserService;
import com.example.service.user.menu.UserMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/user/menu")
public class UserMenuManageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMenuService userMenuService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "limit",defaultValue = "10") int limit,
                           Menu menu){

        AjaxResult ajaxResult = new AjaxResult();
        HashMap map = new HashMap();
        PageHelper.offsetPage((page-1) * limit,limit);
        List<Menu> list = userMenuService.menuList(menu);
        logger.info("list:"+ JSON.toJSONString(list));
        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        //计算总页数
        int pageNumTotal = (int) Math.ceil((double)pageInfo.getTotal()/(double)limit);
        if(page > pageNumTotal){
            PageInfo<TestEntity> entityPageInfo = new PageInfo<>();
            entityPageInfo.setList(new ArrayList<>());
            entityPageInfo.setTotal(pageInfo.getTotal());
            entityPageInfo.setPageNum(page);
            entityPageInfo.setPageSize(limit);
            map.put("pageInfo",entityPageInfo);
        }else {
            map.put("pageInfo",pageInfo);
        }
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        ajaxResult.setData(map);
        logger.info("responseMsg:"+ JSON.toJSONString(ajaxResult));
        return ajaxResult;
    }
}
