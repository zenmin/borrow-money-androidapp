package com.zm.borrowmoneyandriodapp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zm.borrowmoneyandriodapp.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:17
 * https://github.com/zenmin/ProjectTemplate
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("borrow_history")
@ApiModel(value = "BorrowHistory", description = "借款记录")
@Table(name = "borrow_history")
@Entity
public class BorrowHistory extends EntityModel {

    @ApiModelProperty(value = "创建时间 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "借款人", hidden = true)
    private Long uid;

    @ApiModelProperty(value = "借款人", hidden = true)
    private String uName;

    @ApiModelProperty(value = "借款月份6 12 24")
    private Integer month;

    @ApiModelProperty(value = "银行卡账号")
    private String receiveAcount;

    @ApiModelProperty(value = "借款用途")
    private String brrowType;

    @ApiModelProperty(value = "借款状态")
    private String status;

    @ApiModelProperty(value = "借款状态编码0 拒绝 1申请 2审核中 3签约中 4 放款 5还款")
    private Integer statusCode;

    @ApiModelProperty(value = "借款申请时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeOne;

    @ApiModelProperty(value = "借款审核时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeTwo;

    @ApiModelProperty(value = "借款签约时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeThree;

    @ApiModelProperty(value = "借款放款时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeFour;

    @ApiModelProperty(value = "借款还款时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeFive;

    @ApiModelProperty(value = "借款拒绝时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeSix;

    @TableField(exist = false)
    @Transient
    private Double positionCode;
}
