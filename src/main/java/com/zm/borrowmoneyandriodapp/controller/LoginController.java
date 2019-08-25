package com.zm.borrowmoneyandriodapp.controller;

import com.google.common.collect.ImmutableMap;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.components.annotation.RateLimiter;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import com.zm.borrowmoneyandriodapp.entity.vo.AdminUserVo;
import com.zm.borrowmoneyandriodapp.service.AdminUserService;
import com.zm.borrowmoneyandriodapp.service.LoginService;
import com.zm.borrowmoneyandriodapp.util.IpHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @RateLimiter(value = "3", target = CommonConstant.LIMIT_USER_IP)
    public ResponseEntity loginUser(@RequestParam(required = true) String phone,
                                     @RequestParam(required = true) String code, HttpServletRequest request) {

        String token = loginService.loginByUser(phone, code, IpHelper.getRequestIpAddr(request));

        return ResponseEntity.success(ImmutableMap.of("token", token));
    }

    @PostMapping("/reg_user")
    @ApiOperation(value = "普通用户注册", response = ResponseEntity.class)
    @RateLimiter(value = "2", target = CommonConstant.LIMIT_USER_IP)
    public ResponseEntity regUser(@RequestParam(required = true) String phone,
                                    @RequestParam(required = true) String code, HttpServletRequest request) {

        boolean success = loginService.regUser(phone, code, IpHelper.getRequestIpAddr(request));

        return ResponseEntity.success(success);
    }

}
