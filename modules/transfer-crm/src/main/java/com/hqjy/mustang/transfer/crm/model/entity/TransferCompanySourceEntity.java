package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * transfer_company_source 推广公司-来源关联实体类
 *
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
@Accessors(chain = true)
public class TransferCompanySourceEntity implements Serializable {
    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 推广公司id company_id
     **/
    private Long companyId;

    /**
     * 来源id source_id
     **/
    private Long sourceId;

    /**
     * 来源名称
     */
    private String name;

    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态 0启用 1禁用 status
     **/
    private byte status;

    /**
     * 是否被用 0 未被用 1 被用
     **/
    private Integer sign;

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