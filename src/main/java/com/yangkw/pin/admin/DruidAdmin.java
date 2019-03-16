package com.yangkw.pin.admin;

/**
 * 类DruidAdmin.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-03-16
 */
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;
@WebServlet(urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name="loginUsername",value="admin"),// 用户名
                @WebInitParam(name="loginPassword",value="admin"),// 密码
                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidAdmin extends StatViewServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
