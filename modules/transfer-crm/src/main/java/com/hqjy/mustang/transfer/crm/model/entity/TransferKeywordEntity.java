package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * transfer_keyword 关键词配置实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
@Accessors(chain = true)
public class TransferKeywordEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Integer id;

    /**
	 * 关联编号 parent_id
	 **/
    private Integer parentId;

    /**
	 * 名称 name
	 **/
    private String name;

    /**
     * 排序号 seq
     **/
    private Integer seq;

    /**
	 * 状态,见数据字典STATUS status( 0-正常 1-禁用)
	 **/
    private Byte status;

    /**
     * 是否被用 0 未被用 1 被用
     **/
    private Integer sign;

    /**
	 * 属性级别：1-岗位类别，2-关键词 level
	 **/
    private Byte level;

    /**
     * 父关键词名称
     */
    private String parentName;

    /**
     * 子关键词
     */
    private List<TransferKeywordEntity> children;

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