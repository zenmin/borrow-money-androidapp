package com.zm.borrowmoneyandriodapp.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import com.zm.borrowmoneyandriodapp.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-24 16:31:59
 * https://github.com/zenmin/ProjectTemplate
 */

@Api(tags = "管理员信息")
@RestController
@RequestMapping("/api/adminUser")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    /**
     * 根据id查询一条数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id) {
        return ResponseEntity.success(adminUserService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param adminUser
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(AdminUser adminUser) {
        return ResponseEntity.success(adminUserService.list(adminUser));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param adminUser
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, AdminUser adminUser) {
        return ResponseEntity.success(adminUserService.listByPage(pager, adminUser));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param adminUser
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(AdminUser adminUser) {
        return ResponseEntity.success(adminUserService.save(adminUser));
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
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(adminUserService.delete(ids));
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @ApiOperation(value = "修改密码", response = ResponseEntity.class)
    @PostMapping("/updateMyPwd")
    public ResponseEntity updateMyPwd(@RequestParam(required = true) String oldPwd, @RequestParam(required = true) String newPwd) {
        return ResponseEntity.success(adminUserService.updateMyPwd(oldPwd, newPwd));
    }

}