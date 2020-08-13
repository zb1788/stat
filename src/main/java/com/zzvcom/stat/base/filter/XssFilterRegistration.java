package com.zzvcom.stat.base.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zzvcom.web.filter.XssFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * @公司 vcom
 * @功能描述 跨站脚本攻击过滤器
 * @作者 lixinyun
 * @时间 2019/6/12 15:56
 */
@Configuration
public class XssFilterRegistration {
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(-1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/images/*,/js/*,/styles/*,/pages/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
