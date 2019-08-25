package com.zm.borrowmoneyandriodapp.entity;

import com.zm.borrowmoneyandriodapp.entity.base.EntityModel;
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
* @Date 2019-08-25 10:31:36
* https://github.com/zenmin/ProjectTemplate
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Qrcode", description="二维码地址")
@Table(name = "qrcode")
@Entity
public class Qrcode extends EntityModel {

    @ApiModelProperty(value = "创建时间 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "二维码类型 1支付宝 2微信")
    private Integer type;

    @ApiModelProperty(value = "图片地址")
    private String url;


}
