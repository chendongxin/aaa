package com.hqjy.mustang.transfer.sms.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * transfer_sms 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:54
 */
@Data
public class TransferSmsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号 id
     **/
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
     * 客户名称
     */
    private String name;

    /**
     * 手机号码 phone
     **/
    @NotBlank(message = "手机号码不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String phone;

    /**
     * 发送内容 content
     **/
    @NotBlank(message = "内容不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class, RestfulValid.DELETE.class})
    private String content;

    /**
     * 短息提交发送时间 send_time
     **/
    private Date sendTime;

    /**
     * 短息实际发送时间 submit_time
     **/
    private Date submitTime;

    /**
     * 接收时间 done_time
     **/
    private Date doneTime;

    /**
     * 状态对应显示值 status_value
     **/
    private String statusValue;

    /**
     * 状态：0 待发送，1发送成功，其他：发送失败对应code status
     **/
    private Integer status;

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

    /**
     * 时间戳
     */
    private String timeStemp;
}