package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xieyuqing
 * @ description 跟进记录信息
 * @ date create in 2018/5/29 17:26
 */

@Data
@Accessors(chain = true)
public class NcFollowUpDTO {
    private String teacher;
    private Integer shangmen;
    /**
     * 跟进状态
     */
    private Integer followstate;
    /**
     * 意向方案
     */
    private Integer yxfa;
    /**
     * 意向度
     */
    private Integer yxd;
    /**
     * 顾虑
     */
    private Integer gl;
    /**
     * 回访切入口
     */
    private String returnstatus;

    /**
     * 下次联系时间
     */
    private String nexttime;
}