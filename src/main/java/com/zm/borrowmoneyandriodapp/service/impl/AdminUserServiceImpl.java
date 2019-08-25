package com.zm.borrowmoneyandriodapp.service.impl;

import com.zm.borrowmoneyandriodapp.common.CommonException;
import com.zm.borrowmoneyandriodapp.common.constant.DefinedCode;
import com.zm.borrowmoneyandriodapp.components.CacheMap;
import com.zm.borrowmoneyandriodapp.service.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zm.borrowmoneyandriodapp.entity.DO.Pager;
import com.zm.borrowmoneyandriodapp.entity.AdminUser;
import com.zm.borrowmoneyandriodapp.mapper.AdminUserMapper;
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
* @Author ZengMin
* @Date 2019-08-24 16:31:59
* https://github.com/zenmin/ProjectTemplate
*/

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    CacheMap cacheMap;

    @Override
    public AdminUser getOne(Long id){
        return adminUserMapper.selectById(id);
    }

    @Override
    public List<AdminUser> list(AdminUser adminUser) {
        if(StringUtils.isNotBlank(adminUser.getCreateTimeQuery())){
            adminUser.setCreateTime(DateUtil.parseToDate(adminUser.getCreateTimeQuery()));
        }
        List<AdminUser> adminUsers = adminUserMapper.selectList(new QueryWrapper<>(adminUser));
        return adminUsers;
    }

    @Override
    public Pager listByPage(Pager pager, AdminUser adminUser) {
        if(StringUtils.isNotBlank(adminUser.getCreateTimeQuery())){
            adminUser.setCreateTime(DateUtil.parseToDate(adminUser.getCreateTimeQuery()));
        }
        IPage<AdminUser> adminUserIPage = adminUserMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(adminUser));
        return Pager.of(adminUserIPage);
    }

    @Override
    @Transactional
    public AdminUser save(AdminUser adminUser) {
        if(Objects.nonNull(adminUser.getId())){
            adminUserMapper.updateById(adminUser);
        }else {
            adminUserMapper.insert(adminUser);
        }
        return adminUser;
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
        int i = adminUserMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public AdminUser getLoginUser() {
        HttpServletRequest request = StaticUtil.getRequest();
        String token = request.getHeader("token");
        Object o = cacheMap.get(token);
        AdminUser user = JSONUtil.parseObject(o.toString(), AdminUser.class);
        return user;
    }

    @Override
    public AdminUser getLoginUserByToken(String token) {
        Object o = cacheMap.get(token);
        AdminUser user = JSONUtil.parseObject(o.toString(), AdminUser.class);
        return user;
    }

    @Override
    public boolean updateMyPwd(String oldPwd, String newPwd) {
        AdminUser loginUser = this.getLoginUser();
        AdminUser user = adminUserMapper.selectOne(new QueryWrapper<AdminUser>().eq("id", loginUser.getId()).eq("password", StaticUtil.md5Hex(oldPwd)));
        if (Objects.isNull(user)) {
            throw new CommonException(DefinedCode.PARAMS_ERROR, "原密码错误！");
        }
        user.setPassword(StaticUtil.md5Hex(newPwd));
        int i = adminUserMapper.updateById(user);
        return i > 0;
    }


}
