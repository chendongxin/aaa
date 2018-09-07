package com.hqjy.mustang.common.model.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user_extend 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/07 16:04
 */
@Data
public class SysUserExtendInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表自增主键 id
     **/
    private Long id;

    /**
     * 用户ID 对应sys_user表中主键 user_id
     **/
    private Long userId;

    /**
     * NC用户ID nc_user_id
     **/
    private String ncUserId;

    /**
     * 小能客服ID 唯一约束 xn_id
     **/
    private String xnId;

    /**
     * 天润坐席号 唯一约束 cno
     **/
    private Integer cno;

    /**
     * TQ账号 tq_id
     **/
    private Integer tqId;

    /**
     * TQ密码 tq_pw
     **/
    private String tqPw;
}