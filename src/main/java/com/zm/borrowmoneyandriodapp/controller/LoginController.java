package com.zm.borrowmoneyandriodapp.controller;

import com.google.common.collect.ImmutableMap;
import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.components.CacheMap;
import com.zm.borrowmoneyandriodapp.components.annotation.RateLimiter;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import com.zm.borrowmoneyandriodapp.entity.vo.AdminUserVo;
import com.zm.borrowmoneyandriodapp.service.AdminUserService;
import com.zm.borrowmoneyandriodapp.service.LoginService;
import com.zm.borrowmoneyandriodapp.util.HttpClientUtil;
import com.zm.borrowmoneyandriodapp.util.IpHelper;
import com.zm.borrowmoneyandriodapp.util.SmsUtil;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/24 16:36
 */
@RestController
@RequestMapping("/api/login")
@Api(tags = "用户登录")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    CacheMap cacheMap;

    @Value("${spring.profiles.active}")
    String env;

    @Autowired
    SmsUtil smsUtil;

    @PostMapping("/login_admin")
    @ApiOperation(value = "管理员登录", response = ResponseEntity.class)
    @RateLimiter(value = "3", target = CommonConstant.LIMIT_USER_IP)
    public ResponseEntity loginAdmin(@RequestParam(required = true) String username,
                                     @RequestParam(required = true) String password, HttpServletRequest request) {

        String token = loginService.loginByAdmin(username, password, IpHelper.getRequestIpAddr(request));

        return ResponseEntity.success(ImmutableMap.of("token", token));
    }

    @PostMapping("/userInfo")
    @ApiOperation(value = "管理员用户信息", response = ResponseEntity.class)
    public AdminUserVo userInfo(@RequestHeader String token) {
        AdminUser user = adminUserService.getLoginUserByToken(token);
        return new AdminUserVo(user.getId().toString(), user.getRealName(), token, user.getStatus(), true);
    }

    @PostMapping("/logOut")
    @ApiOperation(value = "管理员登出", response = ResponseEntity.class)
    public ResponseEntity logOutAdmin() {
        loginService.logOutAdmin();
        return ResponseEntity.success(true);
    }

    @PostMapping("/login_user")
    @ApiOperation(value = "普通用户登录", response = ResponseEntity.class)
    @RateLimiter(value = "5", target = CommonConstant.LIMIT_USER_IP)
    public ResponseEntity loginUser(@RequestParam(required = true) String phone,
                                    @RequestParam(required = true) String code, HttpServletRequest request) {
        String token = loginService.loginByUser(phone, code, IpHelper.getRequestIpAddr(request));
        return ResponseEntity.success(ImmutableMap.of("token", token));
    }


    @PostMapping("/login_send")
    @RateLimiter(value = "1", target = CommonConstant.LIMIT_USER_IP)
    public ResponseEntity loginSend(@RequestParam(required = true) String phone, HttpServletRequest request) {
        Object o = cacheMap.limitGet(phone);
        if (Objects.nonNull(o)) {
            throw new CommonException(DefinedCode.PARAMSERROR, "已经发送过短信，请稍后再试！");
        }
        // 生成验证码
        String code = String.valueOf((int)(Math.random() * 10000));
        if (env.startsWith("dev")) {
            code = "123456";
        } else {
            smsUtil.sendSms(phone, ImmutableMap.of("code", code));
        }
        cacheMap.limitSet(phone, code);
        return ResponseEntity.success(true);
    }
}
