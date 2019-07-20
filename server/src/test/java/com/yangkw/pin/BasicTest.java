package com.yangkw.pin;

import com.yangkw.pin.domain.request.LoginRequest;
import com.yangkw.pin.domain.user.UserInfo;
import com.yangkw.pin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 类Test.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-03-16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BasicTest {

    @Test
    public void logTest() {

    }


}
