package com.hqjy.mustang.transfer.crawler.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * transfer_email 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/12 15:27
 */
@Data
public class TransferEmailEntity implements Serializable {
    /**
     * 编号 id
     **/
    @NotNull(message = "编号不能为空", groups = {RestfulValid.PUT.class})
    private Long id;

    /**
     * 推广公司id company_id
     **/
    @NotNull(message = "推广公司不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long companyId;

    /**
     * 推广公司名称 company_name
     **/
    @NotBlank(message = "推广公司名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String companyName;

    /**
     * 推广id user_id
     **/
    @NotNull(message = "推广人不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long userId;

    /**
     * 推广人名称 user_name
     **/
    @NotBlank(message = "推广人名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String userName;

    /**
     * 赛道id pro_id
     **/
    @NotNull(message = "赛道不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long proId;

    /**
     * 赛道（产品名称） pro_name
     **/
    @NotBlank(message = "赛道名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String proName;

    /**
     * 部门id dept_id
     **/
    @NotNull(message = "部门不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long deptId;

    /**
     * 部门名称 dept_name
     **/
    @NotBlank(message = "部门名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String deptName;

    /**
     * 推广邮箱 email
     **/
    @NotBlank(message = "邮箱不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @Email(message = "邮箱格式不正确", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String email;

    /**
     * 邮箱密码 password
     **/
    @NotBlank(message = "密码不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String password;

    /**
     * 状态 0：正常 1：禁用 status
     **/
    private Integer status;

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