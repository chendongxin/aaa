package com.hqjy.mustang.transfer.sms.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * transfer_sms_template 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:15
 */
@Data
public class TransferSmsTemplateEntity implements Serializable {

    @NotNull(message = "模板编号不能为空", groups = {RestfulValid.PUT.class})
    private Long id;

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
     * 模板名称 name
     **/
    @NotBlank(message = "模板名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String name;

    /**
     * 模板内容 content
     **/
    @NotBlank(message = "模板内容不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String content;

    /**
     * 创建人编号 create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人ID create_user_name
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