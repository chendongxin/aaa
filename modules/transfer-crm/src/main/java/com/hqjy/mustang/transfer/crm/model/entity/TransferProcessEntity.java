package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * transfer_process 客户流程记录实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
@Accessors(chain = true)
public class TransferProcessEntity implements Serializable {
    /**
	 * 流程编号 process_id
	 **/
    private Long processId;

    /**
	 * 客户编号 customer_id
	 **/
    private Long customerId;

    /**
	 * 业务员部门编号 dept_id
	 **/
    private Long deptId;

    /**
	 * 业务员部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 业务人员编号 user_id
	 **/
    private Long userId;

    /**
	 * 业务人员名称 user_name
	 **/
    private String userName;

    /**
	 * 当前流程状态,见数据字典STATUS active
	 **/
    private Boolean active;

    /**
	 * 跟进次数 follow_count
	 **/
    private Long followCount;

    /**
	 * 最后跟进编号 last_follow_id
	 **/
    private Long lastFollowId;

    /**
	 * 备注 memo
	 **/
    private String memo;

    /**
	 * 到期时间 expire_time
	 **/
    private Date expireTime;

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