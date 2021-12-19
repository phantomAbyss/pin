package com.pinche.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.pinche.domain.request.LoginRequest;
import com.pinche.domain.response.LoginResponse;
import com.pinche.domain.user.UserDO;
import com.pinche.service.CacheService;
import com.pinche.service.UserService;
import com.pinche.service.annotation.ParamCheck;
import com.pinche.service.util.ResponseUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Parmaze
 * @date 2021/12/16
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

    @GetMapping()
    public String test() {
        return "success";
    }

    @PostMapping("login")
    @ParamCheck
    public LoginResponse login(@RequestBody @Validated LoginRequest info, BindingResult bindingResult) {
        LoginResponse response = new LoginResponse();
        WxMaJscode2SessionResult result = null;
        try {
            result = wxService.getUserService().getSessionInfo(info.getCode());
        } catch (WxErrorException e) {
            LOG.warn("login fail/wx error e:{}", e.getMessage());
        }
        if (result == null) {
            LOG.warn("session result is null code:{}", info.getCode());
            return ResponseUtil.errorResponse(response, "session result is null");
        }
        UserDO old = userService.find(result.getOpenid());
        Integer id;
        if (old == null) {
            id = userService.insert(info, result.getOpenid());
        } else {
            id = old.getId();
            userService.update(info, result.getOpenid());
        }
        String token = cacheService.addUserId(id, result.getSessionKey(), result.getOpenid());
        response.setSuccess(true);
        response.setToken(token);
        return response;
    }
}
