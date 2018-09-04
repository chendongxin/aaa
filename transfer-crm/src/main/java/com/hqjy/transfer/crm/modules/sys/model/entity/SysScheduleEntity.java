package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_schedule 实体类
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/05/23 14:15
 */
@Data
@Accessors(chain = true)
public class SysScheduleEntity implements Serializable {
    /**
	 * 主键id,自增 id
	 **/
    private Long id;

    /**
	 * 部门id dept_id
	 **/
    private Long deptId;

    /**
	 * 用户ID user_id
	 **/
    private Long userId;

    /**
     * 用户姓名（冗余字段）
     */
    private String userName;

    /**
	 * 班次id class_id
	 **/
    private Long classId;

    /**
     * 班次名称 冗余字段
     */
    private String className;

    /**
	 * 值班日期 duty_date
	 **/
    private Date dutyDate;

    /**
	 * 创建人 create_id
	 **/
    private Long createId;

    /**
	 * 创建时间 create_time
	 **/
    private Date createTime;

    /**
	 * 最后更新人（操作人id） update_id
	 **/
    private Long updateId;

    /**
	 * 最后更新时间 update_time
	 **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}