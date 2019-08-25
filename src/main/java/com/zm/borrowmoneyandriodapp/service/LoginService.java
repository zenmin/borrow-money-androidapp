package com.zm.borrowmoneyandriodapp.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/24 16:42
 */
public interface LoginService {

    String loginByAdmin(String username, String password,String ip);

    void logOutAdmin();

    boolean checkLogin(String token, HttpServletRequest request);

    String loginByUser(String phone, String code,String ip);

    boolean regUser(String phone, String code, String requestIpAddr);

}
