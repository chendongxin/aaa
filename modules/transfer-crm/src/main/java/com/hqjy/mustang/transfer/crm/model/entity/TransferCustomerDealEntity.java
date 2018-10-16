package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_deal 成交客户实体类
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
@Accessors(chain = true)
public class TransferCustomerDealEntity implements Serializable {
    /**
     * 自增主键 deal_id
     **/
    private Long dealId;

    /**
     * 客户id customer_id
     **/
    private Long customerId;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 赛道id pro_id
     **/
    private Long proId;

    /**
     * 部门id dept_id
     **/
    private Long deptId;

    /**
     * 成交人员 user_id
     **/
    private Long userId;

    /**
     * 成交人名称 user_name
     **/
    private String userName;

    /**
     * 创建人编号 create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人名称 create_user_name
     **/
    private String createUserName;

    /**
     * 创建时间（成交时间） create_time
     **/
    private Date createTime;

    /**
     * 创建时间（商机创建时间)
     **/
    private Date bCreateTime;

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

    /**
     * 删除人编号 delete_user_id
     **/
    private Long deleteUserId;

    /**
     * 删除状态：0-未删除，1-删除
     **/
    private boolean isDeleted;

    /**
     * 删除时间 update_time
     **/
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}