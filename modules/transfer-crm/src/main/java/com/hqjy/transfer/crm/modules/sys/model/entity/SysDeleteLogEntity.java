package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_delete_log 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 16:41
 */
@Data
public class SysDeleteLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号 id
     **/
    private Long id;

    /**
     * 级联删除key 级联删除多条数据，通过此字段进行关联一次操作 keyword
     **/
    private String keyword;

    /**
     * 表名 table_name
     **/
    private String tableName;

    /**
     * 记录内容 此字段为json类型，利于查询 content
     **/
    private String content;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 创建人编号 delete_id
     **/
    private Long deleteId;

    /**
     * 创建日期 delete_time
     **/
    private Date deleteTime;
}