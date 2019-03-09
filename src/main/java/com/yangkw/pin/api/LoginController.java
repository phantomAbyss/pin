package com.yangkw.pin.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.yangkw.pin.domain.BaseResponse;
import com.yangkw.pin.domain.request.LoginRequest;
import com.yangkw.pin.service.CacheService;
import com.yangkw.pin.service.UserService;
import com.yangkw.pin.service.util.ResponseUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类LoginController.java的实现描述：TODO
 *
 * @author kaiwen.ykw 2018-12-21
 */
@RestController
@RequestMapping("auth")
public class LoginController {
    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private WxMaService wxService;
    @Autowired
    private CacheService cacheService;

    @PostMapping("login")
    public BaseResponse login(@RequestBody LoginRequest info) {
        BaseResponse response = new BaseResponse();
        WxMaJscode2SessionResult result = null;
        try {
            result = wxService.getUserService().getSessionInfo(info.getCode());
        } catch (WxErrorException e) {
            LOG.warn("login fail/wx error e:{}", e.getMessage());
        }
        if (result == null) {
            LOG.warn("session result is null code:{}", info.getCode());
            return ResponseUtil.errorResponse("session result is null");
        }
        String openId = result.getOpenid();
        Integer old = userService.find(openId);
        if (old == null) {
            old = userService.insert(info, openId);
        } else {
            userService.update(info, openId);
        }
        String token = cacheService.addUserId(old, result.getSessionKey(), openId);
        response.setSuccess(true);
        response.setData(token);
        return response;
    }
}
