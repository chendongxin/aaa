package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * transfer_source 推广来源（平台）实体类
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
public class TransferSourceEntity implements Serializable {
    /**
	 * 主键 source_id
	 **/
    private Long sourceId;

    /**
     * 主键 parent_id
     **/
    private Long parentId;

    /**
	 * 来源名称 name
	 **/
    private String name;

    /**
	 * 电子邮件域名 email_domain
	 **/
    private String emailDomain;

    /**
	 * 状态( 0-正常 1-禁用) status
	 **/
    private Byte status;

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

    /**
     * 子推广公司
     */
    private List<TransferSourceEntity> children;

    /**
     * 该推广方式下的推广平台
     */
    private List<TransferGenWayEntity> genWayList;

    private static final long serialVersionUID = 1L;
}