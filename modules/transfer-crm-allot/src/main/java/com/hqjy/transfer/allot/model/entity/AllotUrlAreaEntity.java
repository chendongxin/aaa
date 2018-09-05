package com.hqjy.transfer.allot.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * cust_url_area 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/19 09:54
 */
@Data
public class AllotUrlAreaEntity implements Serializable {
    /**
     * 推广编号 id
     **/
    private Long id;

    /**
     * 区域编号 area_id
     **/
    private Long areaId;

    /**
     * 部门ID dept_id
     **/
    private Long deptId;

    /**
     * 用户id user_id
     **/
    private Long userId;

    /**
     * 推广url url
     **/
    private String url;

    /**
     * 状态：0 正常 1禁用 status
     **/
    private Integer status;

    /**
     * 备注 remark
     **/
    private String remark;

    /**
     * 创建者ID create_id
     **/
    private Long createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 更新者ID update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}