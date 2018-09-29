package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/7/13 9:13
 */
@Data
public class TransferAnswerCountEntity {

    /**
     * 坐席id
     */
    private Long customerId;

    /**
     * 客户接听数
     */
    private int answerNum;
}
