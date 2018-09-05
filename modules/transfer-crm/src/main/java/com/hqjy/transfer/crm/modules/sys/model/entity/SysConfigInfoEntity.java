package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_config_info 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/19 18:36
 */
@Data
public class SysConfigInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 配置信息主键 info_id
     */
    private Long infoId;

    /**
     * 配置编码 code
     **/
    private String code;

    /**
     * 配置值 json格式 value
     **/
    private String value;

    /**
     * 配置显示值 memo
     **/
    private String memo;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新日期 update_time
     **/
    private Date updateTime;

}