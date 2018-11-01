package com.hqjy.mustang.admin.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号 user_id
     **/
    private Long userId;

    /**
     * 用户名 user_name
     **/
    @NotBlank(message = "用户名不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @Length(min = 1, max = 15, message = "用户名长度1-15位", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String userName;

    /**
     * 手机号码 唯一索引 phone
     **/
    @Pattern(regexp = "^$|^((19[0-9])|(16[0-9])|(17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$", message = "手机格式不正确", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String phone;

    /**
     * 头像 avatar
     **/
    private String avatar;

    /**
     * 用户盐 salt
     **/
    @JSONField(serialize = false)
    private String salt;

    /**
     * 用户密码 password 密码相关不允许序列化
     **/
    @JSONField(serialize = false)
    @Length(min = 8, max = 20, message = "密码长度8-20位", groups = {RestfulValid.POST.class})
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$", message = "密码必须包含数字和字母", groups = {RestfulValid.POST.class})
    private String password;

    /**
     * 最后登录IP login_ip
     **/
    private String loginIp;

    /**
     * 最后登录时间 login_time
     **/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 状态 0正常 1禁用 status
     **/
    @Min(value = 0, message = "状态不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer status;

    /**
     * 创建人编号 creator_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 角色列表
     */
    private List<SysRoleEntity> roleList;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    /**
     * 赛道id列表
     */
    private List<Long> proIdList;

    /**
     * 赛道信息
     */
    private List<SysUserProEntity> userProList;

    /**
     * 用户部门信息列表
     */
    private List<SysUserDeptEntity> userDeptList;

    /**
     * 部门ID列表
     */
    private List<Long> deptIdList;

}