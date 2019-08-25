package com.zm.borrowmoneyandriodapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zm.borrowmoneyandriodapp.entity.base.EntityModel;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;

/**
* Create by Code Generator
* JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
* @Author ZengMin
* @Date 2019-08-24 16:31:59
* https://github.com/zenmin/ProjectTemplate
*/

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("admin_user")
@ApiModel(value="AdminUser", description="管理员信息")
@Table(name = "admin_user")
@Entity
public class AdminUser extends EntityModel {

    @ApiModelProperty(value = "创建时间 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    private String realName;

    private String username;

    private String password;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String lastLoginIp;

    private Integer status;
}
