//package com.example.onlineeducationplatform.common;
//
//import com.example.onlineeducationplatform.Realm.MyRealm;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 功能：使用Shiro实现用户权限管理
// * 作者：ddh
// * 日期： 2024/11/27 21:53
// */
//@Configuration
//public class ShiroConfig {
//    @Autowired
//    private MyRealm myRealm;
////安全管理器
//    @Bean
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
//        DefaultWebSecurityManager manager =new DefaultWebSecurityManager();
//        manager.setRealm(myRealm);
//        return manager;
//    }
//
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager manager){
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//        filterFactoryBean.setSecurityManager(manager);
////过滤器
//        Map<String,String>map =new LinkedHashMap<>();
//        map.put("","");  //需要添加的路径，过滤器anno(无需登录)  authc(登录验证)
//
//        filterFactoryBean.setFilterChainDefinitionMap(map);
//        return filterFactoryBean;
//    }
//
//}
//
