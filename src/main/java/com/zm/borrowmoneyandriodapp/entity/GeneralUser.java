package com.zm.borrowmoneyandriodapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zm.borrowmoneyandriodapp.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.NoArgsConstructor;

/**
* Create by Code Generator
* JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
* @Author ZengMin
* @Date 2019-08-25 10:31:05
* https://github.com/zenmin/ProjectTemplate
*/

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_user")
@ApiModel(value="GeneralUser", description="普通用户管理")
@Table(name = "general_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GeneralUser extends EntityModel {

    @ApiModelProperty(value = "创建时间 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    private String name;

    private String card;

    private String phone;

    private String bankCard;

    private String education;

    private String marry;

    private String income;

    private String address1;

    private String address2;

    private String linkMan;

    private String linkManPhone;

    @ApiModelProperty(value = "登录手机号")
    private String loginPhone;

    @ApiModelProperty(value = "用户状态1启用 0禁用")
    private Integer status;

    @ApiModelProperty(value = "额度编码 1:10000 2:12000")
    private Integer positionCode;

    public GeneralUser(String loginPhone, Integer status) {
        this.loginPhone = loginPhone;
        this.status = status;
    }

    public GeneralUser(Long id,String loginPhone, Integer status) {
        super.setId(id);
        this.loginPhone = loginPhone;
        this.status = status;
    }

}
