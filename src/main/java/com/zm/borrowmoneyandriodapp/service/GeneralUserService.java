package com.zm.borrowmoneyandriodapp.service;

import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.GeneralUser;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-08-25 10:31:05
* https://github.com/zenmin/ProjectTemplate
*/

public interface GeneralUserService {

    GeneralUser getOne(Long id);

    List<GeneralUser> list(GeneralUser general_user);

    Pager listByPage(Pager pager,GeneralUser general_user);

    GeneralUser save(GeneralUser general_user);

    boolean delete(String ids);

    GeneralUser getLoginUser();

    GeneralUser saveMyInfo(GeneralUser generalUser);

}
