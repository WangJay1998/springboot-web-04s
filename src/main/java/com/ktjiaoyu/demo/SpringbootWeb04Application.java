package com.ktjiaoyu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.ktjiaoyu.demo.mapper")

public class SpringbootWeb04Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWeb04Application.class, args);
    }

}
