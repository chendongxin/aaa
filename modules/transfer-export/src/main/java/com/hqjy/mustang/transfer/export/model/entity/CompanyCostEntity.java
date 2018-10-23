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
     * 创建日期
     **/
    private String date;

}
