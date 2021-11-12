package com.ktjiaoyu.demo.config;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Swagger;
import net.sf.saxon.functions.Concat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerInfo {
    //配置swagger的bean示例
    @Bean
    //添加ApiOperiation注解的被扫描.paths(PathSelectors.any()).build();
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(false).
                        select().
                        apis(RequestHandlerSelectors.basePackage("com.ktjiaoyu.demo.controller"))
                .build();
    }


    public ApiInfo apiInfo(){
      return new ApiInfoBuilder().title("swagger和springBoot整合").description("swagger的API文档") .version("1.0").build();
    }
}
