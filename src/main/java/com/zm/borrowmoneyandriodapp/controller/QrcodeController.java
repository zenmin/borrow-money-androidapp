package com.zm.borrowmoneyandriodapp.controller;

import com.zm.borrowmoneyandriodapp.components.annotation.RequireRole;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.Qrcode;
import com.zm.borrowmoneyandriodapp.service.QrcodeService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-08-25 10:31:36
* https://github.com/zenmin/ProjectTemplate
*/

@Api(tags = "二维码地址")
@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {

    @Autowired
    QrcodeService qrcodeService;

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id",value = "主键",required = true)
    @PostMapping("/getOne")
    @RequireRole
    public ResponseEntity getOne(Long id){
        return ResponseEntity.success(qrcodeService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     * @param qrcode
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    @RequireRole
    public ResponseEntity list(Qrcode qrcode){
        return ResponseEntity.success(qrcodeService.list(qrcode));
    }

    /**
     * 带ID更新 不带ID新增
     * @param qrcode
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole
    public ResponseEntity saveOrUpdate(Qrcode qrcode){
        return ResponseEntity.success(qrcodeService.save(qrcode));
    }

    /**
     * 根据id删除   多个用,隔开
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id删除 多个用,隔开", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids",value = "主键 多个用,隔开",required = true)
    @PostMapping("/delete")
    @RequireRole
    public ResponseEntity delete(String ids){
        return ResponseEntity.success(qrcodeService.delete(ids));
    }


}