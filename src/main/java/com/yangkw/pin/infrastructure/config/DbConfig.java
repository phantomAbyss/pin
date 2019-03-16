package com.yangkw.pin.infrastructure.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * 类DBConfig.java的实现描述：TODO
 *
 * @author kaiwen.ykw 2018-12-21
 */
@Configuration
public class DbConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new WebStatFilter());
        filter.setName("druidWebStatFilter");
        filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filter.addUrlPatterns("/*");
        return filter;
    }

    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        servlet.setName("druidStatViewServlet");
        servlet.addInitParameter("resetEnable", "false");
        servlet.addInitParameter("loginUsername","druid");
        servlet.addInitParameter("loginPassword","kevin");
        return servlet;
    }
}
