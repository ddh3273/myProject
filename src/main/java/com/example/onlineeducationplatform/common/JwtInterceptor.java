package com.example.onlineeducationplatform.common;

/**
 * 功能：校验
 * 作者：ddh
 * 日期： 2024/9/23 21:22
 */
//401权限错误
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.onlineeducationplatform.entity.User;
import com.example.onlineeducationplatform.exception.ServiceException;
import com.example.onlineeducationplatform.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
//前端登录完之后后台要把token返回给前端

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");  //header里面传过来的参数
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");  //url参数 ？token=xxx
        }
//如果不是映射到方法直接通过
        if(handler instanceof HandlerMethod){
              AuthAccess annotation =((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
        if(annotation!=null){
        return true;
    }
        }


        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }
        // 获取 token 中的adminId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);


        } catch (JWTDecodeException e) {
            throw new ServiceException("401", "请登录");
        }
// 根据token中的userid查询数据库
        User user=userMapper.selectById(Integer.valueOf(userId));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }

        try {
            // 用户密码加签验证 token
//通过用户密码加密之后生成一个验证期
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "token验证失败，请重新登录");
        }
        return true;
    }
}
//1.拿到token
// 2.验证token有没有
// 3，拿到id
// 4.通过userid去数据库查询
// 5，验证user是否存在
// 6.通过user拿到密码然后再去生成验证器
// 7.通过验证器去验证token


