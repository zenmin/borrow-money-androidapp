package com.zm.borrowmoneyandriodapp.components.aop;

import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.common.constant.RequestConstant;
import com.zm.borrowmoneyandriodapp.components.annotation.RequireRole;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Describle This Class Is 全局权限验证Aspect
 * @Author ZengMin
 * @Date 2019/6/29 16:40
 */
@Component
@Aspect
@Slf4j
public class RoleAspect {

    @Pointcut("@annotation(com.zm.borrowmoneyandriodapp.components.annotation.RequireRole)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object execAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = StaticUtil.getRequest();
        boolean b = this.validRole(joinPoint, request);
        if (b) {
            return joinPoint.proceed();
        } else {
            throw new CommonException(DefinedCode.NOTAUTH, "您没有权限操作！");
        }
    }

    private boolean validRole(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        Object attribute = request.getAttribute(CommonConstant.ISADMIN);
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            RequireRole annotation = method.getAnnotation(RequireRole.class);
            String roleCode = annotation.value();
            Integer i = Integer.parseInt(attribute.toString());
            return i.toString().equals(roleCode);
        } else {
            throw new IllegalArgumentException("该注解仅用于Controller方法上！");
        }
    }


}
