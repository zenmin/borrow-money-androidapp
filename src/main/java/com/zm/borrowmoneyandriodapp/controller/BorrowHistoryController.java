package com.zm.borrowmoneyandriodapp.controller;

import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.components.annotation.RequireRole;
import com.zm.borrowmoneyandriodapp.entity.GeneralUser;
import com.zm.borrowmoneyandriodapp.service.GeneralUserService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.BorrowHistory;
import com.zm.borrowmoneyandriodapp.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:17
 * https://github.com/zenmin/ProjectTemplate
 */

@Api(tags = "借款记录")
@RestController
@RequestMapping("/api/borrowHistory")
public class BorrowHistoryController {

    @Autowired
    BorrowHistoryService borrow_historyService;

    @Autowired
    GeneralUserService generalUserService;

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
        return ResponseEntity.success(borrow_historyService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param borrow_history
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    @RequireRole
    public ResponseEntity list(BorrowHistory borrow_history) {
        return ResponseEntity.success(borrow_historyService.list(borrow_history));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param borrow_history
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    @RequireRole
    public ResponseEntity listByPage(Pager pager, BorrowHistory borrow_history) {
        return ResponseEntity.success(borrow_historyService.listByPage(pager, borrow_history));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param borrow_history
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole
    public ResponseEntity saveOrUpdate(BorrowHistory borrow_history) {
        return ResponseEntity.success(borrow_historyService.save(borrow_history));
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
        return ResponseEntity.success(borrow_historyService.delete(ids));
    }


    /**
     * 带ID更新 不带ID新增
     *
     * @param borrow_history
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/approved")
    public ResponseEntity approved(BorrowHistory borrow_history) {
        GeneralUser loginUser = generalUserService.getLoginUser();
        borrow_history.setUid(loginUser.getId());
        borrow_history.setUName(loginUser.getName());
        if (StringUtils.isBlank(borrow_history.getReceiveAcount()) || StringUtils.isBlank(borrow_history.getBrrowType())) {
            throw new CommonException(DefinedCode.PARAMSERROR, "银行卡账号或借款用途不能为空");
        }
        if (borrow_history.getMonth() != 6 && borrow_history.getMonth() != 12 && borrow_history.getMonth() != 24) {
            throw new CommonException(DefinedCode.PARAMSERROR, "月份有误！");
        }
        borrow_history.setTimeOne(new Date());
        borrow_history.setStatus(CommonConstant.BORROW_STATUS.SQ.getValue());
        borrow_history.setStatusCode(CommonConstant.BORROW_STATUS.SQ.getCode());
        return ResponseEntity.success(borrow_historyService.save(borrow_history));
    }

    /**
     * 当前登录用户的借款记录
     *
     * @return
     */
    @ApiOperation(value = "当前登录用户的借款记录", response = ResponseEntity.class)
    @PostMapping("/approvedList")
    public ResponseEntity approvedList() {
        GeneralUser loginUser = generalUserService.getLoginUser();
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setId(loginUser.getId());
        return ResponseEntity.success(borrow_historyService.list(borrowHistory));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param borrow_history
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/saveMyInfo")
    @RequireRole
    public ResponseEntity saveMyInfo(BorrowHistory borrow_history) {
        borrow_history.setUid(generalUserService.getLoginUser().getId());
        return ResponseEntity.success(borrow_historyService.save(borrow_history));
    }


}