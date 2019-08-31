package com.zm.borrowmoneyandriodapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
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
import com.zm.borrowmoneyandriodapp.util.HttpClientUtil;
import com.zm.borrowmoneyandriodapp.util.JSONUtil;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
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

    @Value("${spring.profiles.active}")
    String env;

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
        Object token = request.getHeader("token");
        if (Objects.nonNull(token)) {
            cacheMap.remove(token.toString());
        }
    }

    @Override
    public boolean checkLogin(String token, HttpServletRequest request) {
        Object o = cacheMap.get(token);
        boolean b = Objects.nonNull(o);
        if (b) {
            boolean isAdmin = token.startsWith(CommonConstant.ADMIN_TOKEN_KEY);
            if (!isAdmin) {
                GeneralUser generalUser = JSONUtil.parseObject(o.toString(), GeneralUser.class);
                if (generalUser.getStatus() == 0) {
                    throw new CommonException(DefinedCode.AUTHERROR, "用户已被禁用，请联系客服！");
                }
            }
            request.setAttribute(CommonConstant.ISADMIN, isAdmin ? 1 : 0);
        }
        return b;
    }

    @Override
    public String loginByUser(String phone, String code, String ip) {

        // 验证验证码
//            Map<String, Object> map = ImmutableMap.of("appkey", "2c302339f63c4", "phone", phone, "zone", "86", "code", code);
//            String s = null;
//            try {
//                s = HttpClientUtil.sendPost("http://webapi.sms.mob.com/sms/verify", map);
//                System.out.println(s);
//            } catch (IOException e) {
//                e.printStackTrace();
////            }
//            Map result = StaticUtil.readToMap(s, "");
//            String status = result.get("status").toString();
//            if (!"200".equals(status)) {
//                throw new CommonException(DefinedCode.PARAMS_ERROR, "验证码错误！");
//            }
            Object o = cacheMap.limitGet(phone);
            if (Objects.nonNull(o)) {
                if (!code.equals(o.toString())) {
                    throw new CommonException(DefinedCode.PARAMS_ERROR, "验证码错误！");
                }
            } else {
                throw new CommonException(DefinedCode.PARAMS_ERROR, "验证码错误！");
            }

        // 查询手机号是否存在
        GeneralUser generalUser = generalUserMapper.selectOne(new QueryWrapper<GeneralUser>().eq("loginPhone", phone));
        if (Objects.isNull(generalUser)) {
            // 用户不存在  执行注册
            GeneralUser user = new GeneralUser(phone, 1);
            generalUserMapper.insert(user);
            generalUser = user;
        }

        if (generalUser.getStatus() == 0) {
            throw new CommonException(DefinedCode.AUTHERROR, "用户已被禁用，请联系客服！");
        }
        cacheMap.limitRemove(phone);
        // 执行登录  生成token
        String token = StaticUtil.md5Hex(generalUser.getId().toString());
        cacheMap.set(token, JSONUtil.toJSONString(new GeneralUser(generalUser.getId(), generalUser.getLoginPhone(), generalUser.getStatus(), generalUser.getName())));
        return token;
    }

}
