package com.zm.borrowmoneyandriodapp.service;

import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.Qrcode;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-08-25 10:31:36
* https://github.com/zenmin/ProjectTemplate
*/

public interface QrcodeService {

    Qrcode getOne(Long id);

    List<Qrcode> list(Qrcode qrcode);

    Pager listByPage(Pager pager,Qrcode qrcode);

    Qrcode save(Qrcode qrcode);

    boolean delete(String ids);

}
