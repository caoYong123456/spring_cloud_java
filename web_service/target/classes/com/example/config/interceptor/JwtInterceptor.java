package com.example.config.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.entity.user.User;
import com.example.service.user.UserService;
import com.example.service.user.UserServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) {
        //获取token
        String token = request.getHeader("X-Token");
        // 执行认证
        if (StringUtils.isBlank(token)) {
            logger.info("token为空");
            response.setStatus(401);
            return false;
        }
        //获取token的userid
        String userId = "";
        try {
            //解密获取
            userId = JWT.decode(token).getAudience().get(0); //得到token中的userid载荷
        } catch (JWTDecodeException j) {
            logger.info("获取userid异常，token验证失败");
            response.setStatus(401);
        }
        //根据userid查询数据库
        User user = userService.getById(Integer.valueOf(userId));
        if (user == null) {
            logger.info("用户为空，token验证失败");
            response.setStatus(401);
            return false;
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            logger.info("用户密码加签验证token失败");
            response.setStatus(401);
        }
        return true;
    }
}
