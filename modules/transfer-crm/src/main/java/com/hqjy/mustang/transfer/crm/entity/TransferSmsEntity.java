package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_sms 短信实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferSmsEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Long id;

    /**
	 * 部门id dept_id
	 **/
    private Long deptId;

    /**
	 * 部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 手机号码 phone
	 **/
    private String phone;

    /**
	 * 发送内容 content
	 **/
    private String content;

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

    /**
	 * 更新人编号 update_user_id
	 **/
    private Long updateUserId;

    /**
	 * 更新人名称 update_user_name
	 **/
    private String updateUserName;

    /**
	 * 更新时间 update_time
	 **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}