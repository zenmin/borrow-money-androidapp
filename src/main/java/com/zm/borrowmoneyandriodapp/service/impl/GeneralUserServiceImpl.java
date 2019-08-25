package com.zm.borrowmoneyandriodapp.service.impl;

import com.zm.borrowmoneyandriodapp.common.constant.CommonConstant;
import com.zm.borrowmoneyandriodapp.components.CacheMap;
import com.zm.borrowmoneyandriodapp.service.GeneralUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.GeneralUser;
import com.zm.borrowmoneyandriodapp.mapper.GeneralUserMapper;
import com.zm.borrowmoneyandriodapp.util.JSONUtil;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.zm.borrowmoneyandriodapp.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:05
 * https://github.com/zenmin/ProjectTemplate
 */

@Service
public class GeneralUserServiceImpl implements GeneralUserService {

    @Autowired
    GeneralUserMapper general_userMapper;

    @Autowired
    CacheMap cacheMap;

    @Override
    public GeneralUser getOne(Long id) {
        return general_userMapper.selectById(id);
    }

    @Override
    public List<GeneralUser> list(GeneralUser general_user) {
        if (StringUtils.isNotBlank(general_user.getCreateTimeQuery())) {
            general_user.setCreateTime(DateUtil.parseToDate(general_user.getCreateTimeQuery()));
        }
        List<GeneralUser> general_users = general_userMapper.selectList(new QueryWrapper<>(general_user));
        return general_users;
    }

    @Override
    public Pager listByPage(Pager pager, GeneralUser general_user) {
        if (StringUtils.isNotBlank(general_user.getCreateTimeQuery())) {
            general_user.setCreateTime(DateUtil.parseToDate(general_user.getCreateTimeQuery()));
        }
        IPage<GeneralUser> general_userIPage = general_userMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(general_user));
        return pager.of(general_userIPage);
    }

    @Override
    @Transactional
    public GeneralUser save(GeneralUser general_user) {
        if (Objects.nonNull(general_user.getId())) {
            general_userMapper.updateById(general_user);
        } else {
            general_userMapper.insert(general_user);
        }
        return general_user;
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
        int i = general_userMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public GeneralUser getLoginUser() {
        HttpServletRequest request = StaticUtil.getRequest();
        Object token = request.getAttribute("token");
        Object o = cacheMap.get(token.toString());
        GeneralUser generalUser = JSONUtil.parseObject(o.toString(), GeneralUser.class);
        return generalUser;
    }

    @Override
    public GeneralUser saveMyInfo(GeneralUser generalUser) {
        GeneralUser loginUser = this.getLoginUser();
        // 查询当期用户是否已经有额度
        GeneralUser user = general_userMapper.selectById(loginUser.getId());
        if (Objects.nonNull(user.getPositionCode())) {
            generalUser.setPositionCode(null);
        }
        generalUser.setId(user.getId());
        general_userMapper.updateById(generalUser);
        return generalUser;
    }


}
