package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_way_source 实体类
 * 
 * @author : xyq
 * @date : 2018/09/17 12:04
 */
@Data
@Accessors(chain = true)
public class TransferWaySourceEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Long id;

    /**
	 * 推广方式id way_id
	 **/
    private Long wayId;

    /**
     * 推广方式名称
     */
    private String genWay;

    /**
     * 来源平台名称
     */
    private String name;

    /**
     * 推广方式名称
     */
    private String wayName;

    /**
	 * 来源id source_id
	 **/
    private Long sourceId;

    /**
     * 推广平台名称
     */
    private String sourceName;

    /**
     * 排序号 seq
     **/
    private Integer seq;

    /**
	 * 状态,数据字典STATUS( 0-正常 1-禁用) status
	 **/
    private Byte status;

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