package com.example.onlineeducationplatform.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 功能：拦截器
 * 作者：ddh
 * 日期： 2024/9/23 21:54
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())  //配置jwt拦截规则 通过@Bean方法注入到spring容器中去
                .addPathPatterns("/**")//拦截所有的请求路径
                .excludePathPatterns("/login","/register");
        super.addInterceptors((registry));
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
