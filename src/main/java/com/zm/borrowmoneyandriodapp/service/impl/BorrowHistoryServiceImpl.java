package com.zm.borrowmoneyandriodapp.service.impl;

import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.service.BorrowHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.BorrowHistory;
import com.zm.borrowmoneyandriodapp.mapper.BorrowHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.zm.borrowmoneyandriodapp.util.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:17
 * https://github.com/zenmin/ProjectTemplate
 */

@Service
public class BorrowHistoryServiceImpl implements BorrowHistoryService {

    @Autowired
    BorrowHistoryMapper borrow_historyMapper;

    @Override
    public BorrowHistory getOne(Long id) {
        return borrow_historyMapper.selectById(id);
    }

    @Override
    public List<BorrowHistory> list(BorrowHistory borrow_history) {
        if (StringUtils.isNotBlank(borrow_history.getCreateTimeQuery())) {
            borrow_history.setCreateTime(DateUtil.parseToDate(borrow_history.getCreateTimeQuery()));
        }
        List<BorrowHistory> borrow_historys = borrow_historyMapper.selectList(new QueryWrapper<>(borrow_history).orderByDesc("createTime"));
        return borrow_historys;
    }

    @Override
    public Pager listByPage(Pager pager, BorrowHistory borrow_history) {
        if (StringUtils.isNotBlank(borrow_history.getCreateTimeQuery())) {
            borrow_history.setCreateTime(DateUtil.parseToDate(borrow_history.getCreateTimeQuery()));
        }
        IPage<BorrowHistory> borrow_historyIPage = borrow_historyMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(borrow_history).orderByDesc("createTime"));
        return pager.of(borrow_historyIPage);
    }

    @Override
    @Transactional
    public BorrowHistory save(BorrowHistory borrow_history) {
        if (Objects.nonNull(borrow_history.getId())) {
            // 管理员更新
            Integer statusCode = borrow_history.getStatusCode();
            if (Objects.nonNull(statusCode)) {
                if (statusCode == CommonConstant.BORROW_STATUS.REJECT.getCode()) {
                    borrow_history.setTimeSix(new Date());
                }
                if (statusCode == CommonConstant.BORROW_STATUS.SQ.getCode()) {
                    borrow_history.setTimeOne(new Date());
                }
                if (statusCode == CommonConstant.BORROW_STATUS.SHZ.getCode()) {
                    borrow_history.setTimeTwo(new Date());
                }
                if (statusCode == CommonConstant.BORROW_STATUS.QYZ.getCode()) {
                    borrow_history.setTimeThree(new Date());
                }
                if (statusCode == CommonConstant.BORROW_STATUS.FKZ.getCode()) {
                    borrow_history.setTimeFour(new Date());
                }
                if (statusCode == CommonConstant.BORROW_STATUS.HKZ.getCode()) {
                    borrow_history.setTimeFive(new Date());
                }
            }
            borrow_historyMapper.updateById(borrow_history);
        } else {
            // 用户提交申请
            // 查询当前用户是否已经有再审批中的
            Integer count = borrow_historyMapper.selectCount(new QueryWrapper<BorrowHistory>().eq("uid", borrow_history.getUid()).in("statusCode", Lists.newArrayList(1, 2, 3, 4, 5)));
            if (count > 0) {
                throw new CommonException(DefinedCode.PARAMS_ERROR, "你已有在进行中的借款记录！");
            }
            borrow_historyMapper.insert(borrow_history);
        }
        return borrow_history;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if (ids.indexOf(",") != -1) {
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        } else {
            list.add(Long.valueOf(ids));
        }
        int i = borrow_historyMapper.deleteBatchIds(list);
        return i > 0;
    }


}
