package com.yangkw.pin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 类Test.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-03-16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BasicTest {
    @Test
    public void logTest(){
        log.info("hahhaha");
    }

}
