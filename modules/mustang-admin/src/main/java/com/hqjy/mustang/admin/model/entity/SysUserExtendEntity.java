package com.hqjy.mustang.admin.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * sys_user_extend 实体类
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/09/07 16:04
 */
@Data
public class SysUserExtendEntity implements Serializable {
    /**
	 * 表自增主键 id
	 **/
    private Long id;

    /**
	 * 用户ID 对应sys_user表中主键 user_id
	 **/
    private Long userId;

    /**
     *用户名，对应sys_user表中user_name
     */
    private String userName;

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
    @JSONField(serialize = false)
    @Length(min = 4, max = 8, message = "密码长度4-8位", groups = {RestfulValid.POST.class})
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$", message = "密码必须包含数字和字母", groups = {RestfulValid.POST.class})
    private String tqPw;

    /**
	 * 创建人编号 create_id
	 **/
    private Long createId;

    /**
	 * 创建时间 create_time
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



    private static final long serialVersionUID = 1L;
}