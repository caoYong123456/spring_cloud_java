package com.example.controller.user;

import com.auth0.jwt.JWT;
import com.example.entity.user.User;
import com.example.entity.user.UserInfo;
import com.example.service.user.UserService;
import com.example.utils.AjaxResult;
import com.example.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public AjaxResult getInfo(@RequestParam("token") String token){
        AjaxResult ajaxResult = new AjaxResult();
        //解密获取
        String userId = JWT.decode(token).getAudience().get(0); //得到token中的userid载荷
        //根据userid查询数据库
        User user = userService.getById(Integer.valueOf(userId));
        if(user == null){
            ajaxResult.setCode(400);
            ajaxResult.setMessage("未获取到用户信息,请重新登陆");
        }else{
            HashMap hashMap = new HashMap();
            hashMap.put("avatar", "any");
            hashMap.put("introduction", "介绍");
            hashMap.put("roles", new String[]{"any"});
            hashMap.put("name", user.getUserName());
            ajaxResult.setCode(20000);
            ajaxResult.setMessage("已登录");
            ajaxResult.setData(hashMap);
        }
        return ajaxResult;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Map<String,String> data, HttpServletRequest request, HttpServletResponse response){
        AjaxResult ajaxResult = new AjaxResult();
        String userName = data.get("username");
        String password = data.get("password");
        //校验用户名和密码
        User user = userService.queryUser(userName, password);
        if (user == null) {
            ajaxResult.setCode(400);
            ajaxResult.setMessage("用户名或密码错误");
        }else {
            //生成token
            String token = TokenUtils.getToken(user.getId().toString(), user.getPassword());
            //将用户存入session,用userid作为键
            request.getSession().setAttribute(user.getId().toString(),user);
            HashMap tokenMap = new HashMap();
            tokenMap.put("token",token);
            ajaxResult.setCode(20000);
            ajaxResult.setMessage("登陆成功");
            ajaxResult.setData(tokenMap);
        }
        return ajaxResult;
    }
}
