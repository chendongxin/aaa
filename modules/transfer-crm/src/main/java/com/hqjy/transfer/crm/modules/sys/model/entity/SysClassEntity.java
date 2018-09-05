package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_class 实体类
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/05/23 14:13
 */
@Data
@Accessors(chain = true)
public class SysClassEntity implements Serializable {
    /**
	 * 主键id,自增 class_id
	 **/
    private Long classId;

    /**
	 * 班次名称 class_name
	 **/
    private String className;

    /**
	 * 上班时间 start_time
	 **/
    private String startTime;

    /**
	 * 下班时间 stop_time
	 **/
    private String stopTime;

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