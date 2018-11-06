package com.hqjy.mustang.common.model.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 13:22
 */
@Data
public class SysUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号 user_id
     **/
    private Long userId;

    /**
     * 用户名 user_name
     **/
    private String userName;

    /**
     * 手机号码 唯一索引 phone
     **/
    private String phone;

    /**
     * 头像 avatar
     **/
    private String avatar;

    /**
     * 用户盐 salt
     **/
    private String salt;

    /**
     * 用户密码 password 密码相关不允许序列化
     **/
    private String password;

    /**
     * 最后登录IP login_ip
     **/
    private String loginIp;

    /**
     * 最后登录时间 login_time
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 状态 0正常 1禁用 status
     **/
    private Integer status;

    /**
     * 创建人编号 creator_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    /**
     * 部门ID列表
     */
    private List<Long> deptIdList;

    /**
     * 赛道ID列表
     */
    private List<Long> proIdList;

}