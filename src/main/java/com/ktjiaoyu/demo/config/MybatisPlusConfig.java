package com.ktjiaoyu.demo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
//    @Bean
//    public AutoMapper autoMapper() {
//        return new AutoMapper(new String[] { "demo.entity","demo.bean" }); //配置实体类所在目录（可多个,暂时不支持通过符*号配置）
//    }
}
