package com.zm.borrowmoneyandriodapp.controller;

import com.zm.borrowmoneyandriodapp.components.annotation.RequireRole;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.GeneralUser;
import com.zm.borrowmoneyandriodapp.service.GeneralUserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:05
 * https://github.com/zenmin/ProjectTemplate
 */

@Api(tags = "普通用户管理")
@RestController
@RequestMapping("/api/generalUser")
public class GeneralUserController {

    @Autowired
    GeneralUserService general_userService;

    /**
     * 根据id查询一条数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @PostMapping("/getOne")
    @RequireRole
    public ResponseEntity getOne(Long id) {
        return ResponseEntity.success(general_userService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param general_user
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    @RequireRole
    public ResponseEntity list(GeneralUser general_user) {
        return ResponseEntity.success(general_userService.list(general_user));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param general_user
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    @RequireRole
    public ResponseEntity listByPage(Pager pager, GeneralUser general_user) {
        return ResponseEntity.success(general_userService.listByPage(pager, general_user));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param general_user
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole
    public ResponseEntity saveOrUpdate(GeneralUser general_user) {
        return ResponseEntity.success(general_userService.save(general_user));
    }

    /**
     * 根据id删除   多个用,隔开
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id删除 多个用,隔开", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids", value = "主键 多个用,隔开", required = true)
    @PostMapping("/delete")
    @RequireRole
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(general_userService.delete(ids));
    }


    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "更新当前登录用户的个人信息", response = ResponseEntity.class)
    @PostMapping("/saveMyInfo")
    public ResponseEntity saveMyInfo(GeneralUser generalUser) {
        generalUser.setStatus(null);
        generalUser.setLoginPhone(null);
        return ResponseEntity.success(general_userService.saveMyInfo(generalUser));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的个人信息", response = ResponseEntity.class)
    @PostMapping("/getMyInfo")
    public ResponseEntity getMyInfo() {
        GeneralUser loginUser = general_userService.getLoginUser();
        return ResponseEntity.success(loginUser);
    }
}