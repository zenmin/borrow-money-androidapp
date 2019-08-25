package com.zm.borrowmoneyandriodapp.components.aop;

import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.components.CacheMap;
import com.zm.borrowmoneyandriodapp.components.annotation.RateLimiter;
import com.zm.borrowmoneyandriodapp.util.IpHelper;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Describle This Class Is 全局权限验证Aspect
 * @Author ZengMin
 * @Date 2019/6/29 16:40
 */
@Scope
@Component
@Aspect
@Slf4j
public class RateLimiterAspect {

    @Autowired
    CacheMap guavaCache;

    public static final String USER_LIMIT = "USER:LIMIT:";     // 单用户限流

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object execAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        this.validRate(joinPoint);
        return joinPoint.proceed();
    }

    private void validRate(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            // 判断限流注解在方法上 还是在类上
            RateLimiter methodRateLimiter = method.getAnnotation(RateLimiter.class);
            // 如果在方法上  以方法的为准
            if (Objects.nonNull(methodRateLimiter)) {
                this.limit(methodRateLimiter, method);
            } else {
                RateLimiter classRateLimiter = method.getDeclaringClass().getAnnotation(RateLimiter.class);
                if (Objects.nonNull(classRateLimiter)) {
                    this.limit(classRateLimiter, method);
                }
            }
        }
    }

    private boolean limit(RateLimiter rateLimiter, Method method) {
        // 限流数量 用户：每10秒的请求次数  全局：每秒接口响应次数
        String value = rateLimiter.value();
        // 限流目标
        String target = rateLimiter.target();
        // IP全局限流
        if (CommonConstant.LIMIT_USER_IP.equals(target)) {
            int limit = Integer.parseInt(value);
            String key = USER_LIMIT + method.getName() + IpHelper.getRequestIpAddr(StaticUtil.getRequest());
            // 判断此IP在系统的操作次数
            Object o = guavaCache.limitGet(key);
            if (Objects.nonNull(o)) {
                int i = Integer.parseInt(o.toString());
                if (i >= limit) {
                    throw new CommonException(DefinedCode.USER_LIMIT_ERROR, "操作频繁，请稍后再试！");
                } else {
                    guavaCache.limitSet(key, i + 1);
                }
            } else {
                guavaCache.limitSet(key, 1);
            }
        }
        return true;
    }
}

