package com.example.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserManageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate template;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public AjaxResult list(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "limit",defaultValue = "10") int limit,
                           User user){

        /**
         * 测试服务之间的调用
         */
        HttpHeaders headers = new HttpHeaders();
        // 设置以json的方式提交
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //将请求头部和参数合成一个请求
        Map paramMap = new HashMap<>();
        paramMap.put("limit",1);
        JSONObject jsonObj = new JSONObject(paramMap);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonObj.toString(), headers);
        String url = "http://127.0.0.1:9090/local/test/list";
        url += "?limit=" + 1;
        //String result = template.postForObject(url,requestEntity, String.class);
        String result = template.getForObject(url, String.class);

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
            user.setUpdateTime(new Date());
            user.setCreateTime(new Date());
            user.setUserType("1");
            user.setPassword("111111");
            user.setDeleteFlag("0");
            userService.save(user);
        }
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public AjaxResult delete(@RequestParam(required=false, value = "id") Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        userService.delete(id);
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        return ajaxResult;
    }
}
