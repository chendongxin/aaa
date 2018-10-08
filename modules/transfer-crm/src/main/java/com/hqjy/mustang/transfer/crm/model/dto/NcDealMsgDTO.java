package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author xyq
 * @date create on 2018/9/30
 * @apiNote
 */
@Data
public class NcDealMsgDTO {

    /**
     * nc主键
     */
    private String ncPk;
    /**
     * 成交状态
     */
    private String state;
    /**
     * 成交时间
     */
    private Date minRegDate;
}
