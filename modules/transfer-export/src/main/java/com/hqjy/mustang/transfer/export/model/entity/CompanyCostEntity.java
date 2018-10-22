package com.hqjy.mustang.transfer.export.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xyq
 * @date create on 2018年10月22日17:38:56
 * @apiNote
 */
@Data
public class CompanyCostEntity {

    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 部门id
     **/
    private Integer deptId;

    /**
     * 推广公司id
     **/
    private Integer companyId;

    /**
     * 推广来源id
     */
    private Long sourceId;

    /**
     * 推广方式id
     **/
    private Integer wayId;

    /**
     * 人民币主动
     **/
    private BigDecimal initiativeMoney;

    /**
     * 人民币被动
     **/
    private BigDecimal passiveMoney;

    /**
     * 虚拟主动
     **/
    private BigDecimal initiativeVirtual;

    /**
     * 虚拟被动
     **/
    private BigDecimal passiveVirtual;

    /**
     * 创建时间
     **/
    private Date createTime;

}
