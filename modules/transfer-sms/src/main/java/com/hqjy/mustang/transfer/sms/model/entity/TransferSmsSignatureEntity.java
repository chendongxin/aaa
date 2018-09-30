package com.hqjy.mustang.transfer.sms.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * transfer_sms_signature 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/28 15:14
 */
@Data
public class TransferSmsSignatureEntity implements Serializable {

    @NotNull(message = "签名ID不能为空", groups = {RestfulValid.PUT.class})
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
     * 签名内容 signature
     **/
    @NotBlank(message = "签名内容不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @Pattern(regexp = "^$|^【.*】$", message = "签名内容请包涵在【】符号之间，如【恒企教育】", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String signature;

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