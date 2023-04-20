package com.example.controller.user;

import com.example.service.user.UserService;
import com.example.utils.AjaxResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getToken",method = RequestMethod.GET)
    public AjaxResult getToken(){
        AjaxResult ajaxResult = new AjaxResult();
        HashMap hashMap = new HashMap();
        hashMap.put("token","admin-token");
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("成功");
        ajaxResult.setData(hashMap);
        return ajaxResult;
    }

    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public AjaxResult getInfo(@RequestParam("token") String token){
        AjaxResult ajaxResult = new AjaxResult();
        HashMap hashMap = new HashMap();
        hashMap.put("id","6");
        hashMap.put("username","admin");
        hashMap.put("role","2");
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("已登录");
        ajaxResult.setData(hashMap);
        return ajaxResult;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Map<String,String> data, HttpServletRequest request, HttpServletResponse response){
        AjaxResult ajaxResult = new AjaxResult();
        String userName = data.get("username");
        String password = data.get("password");
        //登录
        String token = userService.login(userName, password);
        if (StringUtils.isBlank(token)) {
            ajaxResult.setCode(400);
            ajaxResult.setMessage("没有token");
            return ajaxResult;
        }
        //CookieUtils.setCookie(request,response,cookieName,token,60*60,false);
        HashMap tokenMap = new HashMap();
        tokenMap.put("token",token);
        ajaxResult.setCode(20000);
        ajaxResult.setMessage("登陆成功");
        ajaxResult.setData(tokenMap);
        return ajaxResult;
    }
}
