package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * transfer_gen_way 推广方式实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
@Accessors(chain = true)
public class TransferGenWayEntity implements Serializable {

    /**
     * 来源平台与推广方式对应编号 id
     **/
    private Long id;
    /**
	 * 编号 way_id
	 **/
    private Long wayId;

    /**
	 * 推广方式 gen_way
	 **/
    private String genWay;

    /**
     * 排序号 seq
     **/
    private Integer seq;

    /**
     * 状态,见数据字典STATUS status( 0-启用 1-禁用)
     **/
    private byte status;

    /**
     * 父推广方式名称
     */
    private String parentName;

    /**
     * 子推广方式
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