package com.m.monitor.me.admin.config;

import com.m.monitor.me.admin.login.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 需要忽略静态资源
     * 2019/7/2 17:28
     * miaozongpei@ucfgroup.com
     */
    private static List<String> staticPath = new ArrayList<String>() {
        {
            add("/assets/**");
            add("/js/**");
            add("/long/**");
            add("/img/**");
            add("/error");
            add("**/*.css");
            add("/doLogin");
            add("/login");

        }
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor()).addPathPatterns("/**").
                excludePathPatterns(staticPath);
    }

    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }
}