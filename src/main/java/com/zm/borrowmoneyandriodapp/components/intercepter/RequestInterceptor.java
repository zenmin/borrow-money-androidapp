package com.zm.borrowmoneyandriodapp.components.intercepter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.service.LoginService;
import com.zm.borrowmoneyandriodapp.util.IpHelper;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Describle This Class Is 验证所有请求权限
 * @Author ZengMin
 * @Date 2019/1/3 19:18
 */
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new CommonException(DefinedCode.NOTAUTH, "登陆超时，请重新登录！");
        }
        // cacheManager验证用户登录与否
        if (!loginService.checkLogin(token, request)) {
            throw new CommonException(DefinedCode.NOTAUTH, "登录超时，请重新登录！");
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        String params = StaticUtil.objectMapper.writeValueAsString(parameterMap);
        log.info("客户端ip:[{}]请求URL:[{}] ,请求params:[{}]", IpHelper.getRequestIpAddr(request), request.getRequestURL(), params);
        return true;
    }

}
