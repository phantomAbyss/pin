package com.yangkw.pin.admin;

/**
 * 类DruidFilter.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-03-16
 */
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        })
public class DruidFilter extends WebStatFilter {

}
