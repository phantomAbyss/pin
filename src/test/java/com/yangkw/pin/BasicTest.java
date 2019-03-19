package com.yangkw.pin;

import com.yangkw.pin.domain.request.LoginRequest;
import com.yangkw.pin.domain.user.UserInfo;
import com.yangkw.pin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;


/**
 * 类Test.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-03-16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BasicTest {
    @Autowired
    private UserService service;

    @Test
    public void logTest(){
        LoginRequest request = new LoginRequest();
        UserInfo info = new UserInfo();
        info.setCountry("");
        info.setProvince("");
        info.setCity("");
        info.setLanguage("");
        info.setGender((byte)0);
        info.setNickName("");
        info.setAvatarUrl("");
        request.setCode("code");
        request.setUserInfo(info);
        Integer row = service.insert(request,"openId");
        Assert.notNull(row,"insert user error");
    }

}
