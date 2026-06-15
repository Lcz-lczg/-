package com.spboot.app.controller;

import com.spboot.app.service.WechatAuthService;
import com.spboot.app.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wechat")
@Api("微信登录")
public class WechatLoginController {

    @Autowired
    private WechatAuthService wechatAuthService;

    @ApiOperation(value = "微信登录", httpMethod = "POST")
    @PostMapping("/login")
    public R<Object> login(@RequestBody Map<String, String> body) {
        return wechatAuthService.login(body.get("code"));
    }
}
