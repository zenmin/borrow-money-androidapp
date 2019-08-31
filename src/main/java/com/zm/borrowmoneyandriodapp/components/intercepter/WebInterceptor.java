package com.zm.borrowmoneyandriodapp.components.intercepter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {

    @Bean
    RequestInterceptor requestInterceptor(){
        return new RequestInterceptor();
    }

    @Bean
    SqlInterceptor sqlInterceptor(){
        return new SqlInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor())
                .addPathPatterns("/api/**").excludePathPatterns("/api/login/login_admin",
                "/api/login/login_user","/api/login/reg_user","/api/login/logOut","/api/login/login_send");

        registry.addInterceptor(sqlInterceptor()).addPathPatterns("/order/**");

    }

}