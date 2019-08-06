package com.tof.deployment.config;

import com.tof.deployment.filter.LogIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author leiwenxue
 * @version 1.0
 * @ClassName FilterConfig
 * @desc 过滤器配置
 * @date 2019/8/6 下午5:32
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean logIdFilterBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogIdFilter());
        filterFilterRegistrationBean.setName("logIdFilter");
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}
