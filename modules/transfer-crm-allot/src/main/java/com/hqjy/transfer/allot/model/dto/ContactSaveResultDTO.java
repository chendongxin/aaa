package com.hqjy.transfer.allot.model.dto;

import lombok.Data;

/**
 * 联系方式保存结果
 *
 * @author : heshuangshuang
 * @date : 2018/6/14 14:13
 */
@Data
public class ContactSaveResultDTO {

    /**
     * 保存的主联系方式
     */
    private Integer type;

    /**
     * 联系方式详情
     */
    private String detail;

    /**
     * 主联系方式状态，true：保存成功，false：保存失败，重单
     */
    private Boolean contacStatus;

    /**
     * 如果保存失败，重单 获取之前客户Id
     */
    private Long oldCustomerId;

}
