package com.zm.borrowmoneyandriodapp.service;

import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-08-24 16:31:59
* https://github.com/zenmin/ProjectTemplate
*/

public interface AdminUserService {

    AdminUser getOne(Long id);

    List<AdminUser> list(AdminUser adminUser);

    Pager listByPage(Pager pager,AdminUser adminUser);

    AdminUser save(AdminUser adminUser);

    boolean delete(String ids);

    AdminUser getLoginUser();

    AdminUser getLoginUserByToken(String token);

    boolean updateMyPwd(String oldPwd, String newPwd);
}
