package com.hqjy.transfer.allot.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * biz_process 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/15 14:45
 */
@Data
public class AllotProcessEntity implements Serializable {
    /**
     * 流程编号 process_id
     **/
    private Long processId;

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * 业务部门编号 dept_id
     **/
    private Long deptId;

    /**
     * 业务人员编号 user_id
     **/
    private Long userId;

    /**
     * 当前流程状态（1-激活，0-过期） active
     **/
    private Integer active;

    /**
     * 跟进次数 follow_count
     **/
    private Integer followCount;

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
     * 创建人编号 create_id
     **/
    private Integer createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    private static final long serialVersionUID = 1L;
}