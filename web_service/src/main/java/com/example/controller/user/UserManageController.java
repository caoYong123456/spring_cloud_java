package com.example.controller.user;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.entity.test.TestEntity;
import com.example.entity.user.User;
import com.example.service.user.UserService;
import com.example.utils.AjaxResult;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserManageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "limit",defaultValue = "10") int limit,
                           User user){
        AjaxResult ajaxResult = new AjaxResult();
        HashMap map = new HashMap();
        PageHelper.offsetPage((page-1) * limit,limit);
        List<User> list = userService.getList(user);
        logger.info("list:"+ JSON.toJSONString(list));
        PageInfo<User> pageInfo = new PageInfo<>(list);
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

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public AjaxResult save(@RequestBody User user){
        AjaxResult ajaxResult = new AjaxResult();
        if(user.getId() != null){
            userService.update(user);
        }else{
            userService.save(user);
        }
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }
}
