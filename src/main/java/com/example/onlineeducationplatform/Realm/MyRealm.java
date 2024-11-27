//package com.example.onlineeducationplatform.Realm;
//
//import com.example.onlineeducationplatform.service.UserService;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthenticatingRealm;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 功能：Realm
// * 作者：ddh
// * 日期： 2024/11/27 22:09
// */
//@Component
//public class MyRealm extends AuthorizingRealm {
//@Autowired
//public UserService userService;
////授权
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
////验证
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        //这里使用的系统自带的token
//        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
//        String username=usernamePasswordToken.getUsername();
//
//        return null;
//    }
//}
