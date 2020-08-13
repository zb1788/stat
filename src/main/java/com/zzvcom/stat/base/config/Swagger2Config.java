package com.zzvcom.stat.base.config;

import org.springframework.beans.factory.annotation.Value;
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

/**
 * @公司 vcom
 * @功能描述 swagger2配置类
 * @作者 lixinyun
 * @时间 2019/6/17 11:21
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 基础包
     */
    private static final String BASE_PACKAGE = "com.zzvcom.stat";

    /**
     * 是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
     */
    @Value("${swagger.enable}")
    private boolean enableSwagger;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return  new ApiInfoBuilder()
                //页面标题
                .title("基于Spring Boot开发的后端模板api文档")
                //页面描述
                .description("模板api")
                //创建人
                .contact(new Contact("lixinyun 13939094656","","lixinyun@zzvcom.com"))
                //版本号
                .version("1.0.0")
                .build();
    }
}
