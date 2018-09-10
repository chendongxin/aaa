package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * transfer_gen_company 推广公司实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferGenCompanyEntity implements Serializable {
    /**
	 * 编号 company_id
	 **/
    private Long companyId;

    /**
     * 父编号 parent_id
     */
    private Long parentId;

    /**
	 * 名称 name
	 **/
    private String name;

    /**
	 * 状态 0启用 1禁用 status
	 **/
    private Integer status;

    /**
     * 父推广公司名称
     */
    private String parentName;

    /**
     * 子推广公司
     */
    private List<TransferGenCompanyEntity> children;

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