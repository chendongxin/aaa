package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * transfer_gen_cost 推广费用实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferGenCostEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Long id;

    /**
	 * 部门id dept_id
	 **/
    private Integer deptId;

    /**
	 * 部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 推广公司id company_id
	 **/
    private Long companyId;

    /**
     * 推广公司名称
     */
    private String name;

    /**
     * 赛道id
     */
    private Long proId;

    /**
     * 赛道名称
     */
    private String genWay;

    /**
     * 推广来源id
     */
    private Long sourceId;

    /**
	 * 推广方式id way_id
	 **/
    private Long wayId;

    /**
	 * 推广日期 格式YYYY-MM-DD gen_day
	 **/
    private Date genDay;

    /**
	 * 人民币主动 initiative_money
	 **/
    private BigDecimal initiativeMoney;

    /**
	 * 人民币被动 passive_money
	 **/
    private BigDecimal passiveMoney;

    /**
	 * 虚拟主动 initiative_virtual
	 **/
    private BigDecimal initiativeVirtual;

    /**
	 * 虚拟被动 passive_virtual
	 **/
    private BigDecimal passiveVirtual;

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