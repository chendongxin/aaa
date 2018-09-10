package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer 客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferCustomerEntity implements Serializable {
    /**
	 * 客户编号 customer_id
	 **/
    private Long customerId;

    /**
	 * NC客户编号 nc_id
	 **/
    private String ncId;

    /**
	 * 产品 pro_id
	 **/
    private Long proId;

    /**
	 * 赛道（产品)名称 pro_name
	 **/
    private String proName;

    /**
	 * 客户来源 source_id
	 **/
    private Long sourceId;

    /**
	 * 推广公司 company_id
	 **/
    private Long companyId;

    /**
	 * 名称 name
	 **/
    private String name;

    /**
	 * 客户状态 status
	 **/
    private Byte status;

    /**
	 * 过去方式：见数据字典GET_WAY枚举 get_way
	 **/
    private Byte getWay;

    /**
	 * 当前跟进人ID user_id
	 **/
    private Long userId;

    /**
	 * 当前跟进人名称 user_name
	 **/
    private String userName;

    /**
	 * 当前跟进人部门ID dept_id
	 **/
    private Long deptId;

    /**
	 * 部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 最后跟进人 last_user_id
	 **/
    private Long lastUserId;

    /**
	 * 最后跟进人名称 last_user_name
	 **/
    private String lastUserName;

    /**
	 * 首次跟进人 first_user_id
	 **/
    private Long firstUserId;

    /**
	 * 首次跟进人名称 first_user_name
	 **/
    private String firstUserName;

    /**
	 * 推广用户ID gen_user_id
	 **/
    private Long genUserId;

    /**
	 * 推广用户名称 gen_user_name
	 **/
    private String genUserName;

    /**
	 * 手机号码 phone
	 **/
    private String phone;

    /**
	 * 微信 we_chat
	 **/
    private String weChat;

    /**
	 * qq qq
	 **/
    private String qq;

    /**
	 * 座机 land_line
	 **/
    private String landLine;

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