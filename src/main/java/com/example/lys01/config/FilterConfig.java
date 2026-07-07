package com.example.lys01.config;

import com.example.lys01.filter.EmployeeLoginCheck;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<EmployeeLoginCheck> loginCheckFilter() {
        FilterRegistrationBean<EmployeeLoginCheck> registration = new FilterRegistrationBean<>();
        registration.setFilter(new EmployeeLoginCheck());
        registration.addUrlPatterns("/*");
        registration.setName("登录过滤器");
        registration.setOrder(1);
        return registration;
    }
}
