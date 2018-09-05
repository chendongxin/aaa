package com.hqjy.transfer.allot.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * biz_customer_repeat 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/19 10:32
 */
@Data
public class AllotCustRepeatEntity implements Serializable {
    /**
     * 重单主键 repeat_id
     **/
    private Long repeatId;

    /**
     * 客户主键 customer_id
     **/
    private Long customerId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 客户名称 name
     **/
    private String name;

    /**
     * 性别:-1：女   0：未知   1：男 sex
     **/
    private Integer sex;

    /**
     * 地址 address
     **/
    private String address;

    /**
     * 来源url url
     **/
    private String url;

    /**
     * 0：系统   1：官网    2：客服   3：代理网   source
     **/
    private Integer source;

    /**
     * 备注 memo
     **/
    private String memo;

    /**
     * 创建人 create_id
     **/
    private Long createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    private static final long serialVersionUID = 1L;
}