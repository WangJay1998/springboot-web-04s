package com.ktjiaoyu.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login.html");
//        registry.addViewController("/user/list").setViewName("/user/list.html");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        String[]excludePathPatterns={"/","/dologin"};
//        registry.addInterceptor(new AnthorInt())
//                .addPathPatterns("/*")
//        .excludePathPatterns(excludePathPatterns);
//    }


}
