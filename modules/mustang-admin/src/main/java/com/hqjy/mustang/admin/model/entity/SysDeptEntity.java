package com.hqjy.mustang.admin.model.entity;

import com.hqjy.mustang.common.base.validator.RestfulValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_dept 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/14 14:38
 */
@Data
public class SysDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门编号 dept_id
     **/
    private Long deptId;

    /**
     * 父编号 parent_id
     **/
    @Min(value = 0, message = "父部门不能小于0", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @NotNull(message = "父部门不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Long parentId;

    /**
     * 部门名称 dept_name
     **/
    @NotBlank(message = "部门名称不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @Length(min = 1, max = 10, message = "部门名称长度1-10位", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private String deptName;

    /**
     * 排序号 seq
     **/
    @Min(value = 0, message = "排序号不能小于0", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @NotNull(message = "排序号不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer seq;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 权重值 商机分配算法需要的 weights
     **/
    @Min(value = 0, message = "权重值不能小于0", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    @NotNull(message = "权重值不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer weights;

    /**
     * 状态 0正常 1禁用 status
     **/
    @NotNull(message = "状态不能为空", groups = {RestfulValid.POST.class, RestfulValid.PUT.class})
    private Integer status;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 更新人编号 update_id
     **/
    private Long updateId;

    /**
     * 更新时间 update_time
     **/
    private Date updateTime;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<SysDeptEntity> children;
}