package com.zm.borrowmoneyandriodapp.service.impl;

import com.zm.borrowmoneyandriodapp.service.QrcodeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.Qrcode;
import com.zm.borrowmoneyandriodapp.mapper.QrcodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.zm.borrowmoneyandriodapp.util.DateUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
* Create Code Generator
* @Author ZengMin
* @Date 2019-08-25 10:31:36
* https://github.com/zenmin/ProjectTemplate
*/

@Service
public class QrcodeServiceImpl implements QrcodeService {

    @Autowired
    QrcodeMapper qrcodeMapper;

    @Override
    public Qrcode getOne(Long id){
        return qrcodeMapper.selectById(id);
    }

    @Override
    public List<Qrcode> list(Qrcode qrcode) {
        if(StringUtils.isNotBlank(qrcode.getCreateTimeQuery())){
            qrcode.setCreateTime(DateUtil.parseToDate(qrcode.getCreateTimeQuery()));
        }
        List<Qrcode> qrcodes = qrcodeMapper.selectList(new QueryWrapper<>(qrcode));
        return qrcodes;
    }

    @Override
    public Pager listByPage(Pager pager, Qrcode qrcode) {
        if(StringUtils.isNotBlank(qrcode.getCreateTimeQuery())){
            qrcode.setCreateTime(DateUtil.parseToDate(qrcode.getCreateTimeQuery()));
        }
        IPage<Qrcode> qrcodeIPage = qrcodeMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(qrcode));
        return pager.of(qrcodeIPage);
    }

    @Override
    @Transactional
    public Qrcode save(Qrcode qrcode) {
        if(Objects.nonNull(qrcode.getId())){
            qrcodeMapper.updateById(qrcode);
        }else {
            qrcodeMapper.insert(qrcode);
        }
        return qrcode;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if(ids.indexOf(",") != -1){
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        }else {
            list.add(Long.valueOf(ids));
        }
        int i = qrcodeMapper.deleteBatchIds(list);
        return i > 0;
    }


}
