package com.zm.borrowmoneyandriodapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.components.CacheMap;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import com.zm.borrowmoneyandriodapp.entity.GeneralUser;
import com.zm.borrowmoneyandriodapp.mapper.AdminUserMapper;
import com.zm.borrowmoneyandriodapp.mapper.GeneralUserMapper;
import com.zm.borrowmoneyandriodapp.service.GeneralUserService;
import com.zm.borrowmoneyandriodapp.service.LoginService;
import com.zm.borrowmoneyandriodapp.util.JSONUtil;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/24 16:42
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    CacheMap cacheMap;

    @Autowired
    GeneralUserMapper generalUserMapper;

    @Override
    public String loginByAdmin(String username, String password, String ip) {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(username);
        adminUser.setPassword(StaticUtil.md5Hex(password));
        AdminUser user = adminUserMapper.selectOne(new QueryWrapper<>(adminUser));
        if (Objects.isNull(user)) {
            throw new CommonException(DefinedCode.NOTAUTH, "用户名或密码错误！");
        }
        user.setPassword(null);
        // 执行登录  生成token
        String token = CommonConstant.ADMIN_TOKEN_KEY + StaticUtil.md5Hex(user.getId().toString());
        cacheMap.set(token, JSONUtil.toJSONString(user));
        return token;
    }

    @Override
    public void logOutAdmin() {
        HttpServletRequest request = StaticUtil.getRequest();
        Object token = request.getAttribute("token");
        if (Objects.nonNull(token)) {
            cacheMap.remove(token.toString());
        }
    }

    @Override
    public boolean checkLogin(String token, HttpServletRequest request) {
        Object o = cacheMap.get(token);
        boolean b = Objects.nonNull(o);
        if (b) {
            request.setAttribute(CommonConstant.ISADMIN, token.startsWith(CommonConstant.ADMIN_TOKEN_KEY) ? 1 : 0);
        }
        return b;
    }

    @Override
    public String loginByUser(String phone, String code, String ip) {
        if (phone.length() != 11 || !phone.startsWith("1")) {
            throw new CommonException(DefinedCode.PARAMSERROR, "手机号格式不正确！");
        }
        // 查询手机号是否存在
        GeneralUser generalUser = generalUserMapper.selectOne(new QueryWrapper<GeneralUser>().eq("loginPhone", phone));
        if (Objects.isNull(generalUser)) {
            throw new CommonException(DefinedCode.NOTFOUND, "用户未注册，请先注册！");
        }

        // 验证验证码
        if (true) {
            // 执行登录  生成token
            String token = StaticUtil.md5Hex(generalUser.getId().toString());
            cacheMap.set(token, JSONUtil.toJSONString(new GeneralUser(generalUser.getId(), generalUser.getLoginPhone(), generalUser.getStatus())));
            return token;
        } else {
            throw new CommonException(DefinedCode.PARAMSERROR, "验证码错误！");
        }
    }

    @Override
    public boolean regUser(String phone, String code, String requestIpAddr) {
        // 查询手机号是否存在
        GeneralUser generalUser = generalUserMapper.selectOne(new QueryWrapper<GeneralUser>().eq("loginPhone", phone));
        if (Objects.nonNull(generalUser)) {
            throw new CommonException(DefinedCode.NOTFOUND, "用户已注册，请直接登录！");
        }
        // 验证验证码
        if (true) {
            GeneralUser user = new GeneralUser(phone, 1);
            generalUserMapper.insert(user);
            return true;
        } else {
            throw new CommonException(DefinedCode.PARAMSERROR, "验证码错误！");
        }
    }


}
