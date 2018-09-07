package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_email 邮箱绑定实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferEmailEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Integer id;

    /**
	 * 推广公司id company_id
	 **/
    private Integer companyId;

    /**
	 * 推广id user_id
	 **/
    private Integer userId;

    /**
	 * 推广人名称 user_name
	 **/
    private String userName;

    /**
	 * 赛道id pro_id
	 **/
    private Integer proId;

    /**
	 * 赛道（产品名称） pro_name
	 **/
    private String proName;

    /**
	 * 部门id dept_id
	 **/
    private Integer deptId;

    /**
	 * 部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 推广邮箱 email
	 **/
    private String email;

    /**
	 * 邮箱密码 password
	 **/
    private String password;

    /**
	 * 创建人编号 create_user_id
	 **/
    private Long createUserId;

    /**
	 * 创建人名称 create_user_name
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