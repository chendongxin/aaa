package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_gen_way 推广方式实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferGenWayEntity implements Serializable {
    /**
	 * 编号 way_id
	 **/
    private Long wayId;

    /**
	 * 来源id source_id
	 **/
    private Integer sourceId;

    /**
	 * 推广方式 gen_way
	 **/
    private String genWay;

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