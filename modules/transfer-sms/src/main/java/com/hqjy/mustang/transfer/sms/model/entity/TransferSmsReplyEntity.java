package com.hqjy.mustang.transfer.sms.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_sms_reply 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/30 14:00
 */
@Data
public class TransferSmsReplyEntity implements Serializable {
    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 客户编号customer_Id
     */
    private Long customerId;

    /**
     * 部门id dept_id
     **/
    private Long deptId;

    /**
     * 部门名称 dept_name
     **/
    private String deptName;

    /**
     * 客户名称 name
     **/
    private String name;

    /**
     * 手机号码 phone
     **/
    private String phone;

    /**
     * 发送内容 content
     **/
    private String content;

    /**
     * 短息实际发送时间 submit_time
     **/
    private Date submitTime;

    /**
     * 回复状态：0 未回复，1：已回复 status
     **/
    private Integer status;

    /**
     * 创建人编号 create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人ID create_user_name
     **/
    private String createUserName;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    private static final long serialVersionUID = 1L;
}