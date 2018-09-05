package com.hqjy.transfer.allot.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * biz_customer_contact 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/13 15:41
 */
@Data
public class AllotContactEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键 contact_id
     **/
    private Long contactId;

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * F
     * 主要联系方式(0-否，1-是，默认0)：用于向nc保存，获取NC主键, is_primary
     **/
    private Integer isPrimary;

    /**
     * 类型：0：手机   1：微信   2：QQ   3：邮箱   4：座机 type
     **/
    private Integer type;

    /**
     * 联系方式详情 detail
     **/
    private String detail;

    /**
     * 创建人编号（业务员） create_id
     **/
    private Long createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 更新人ID update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    private Date updateTime;
}