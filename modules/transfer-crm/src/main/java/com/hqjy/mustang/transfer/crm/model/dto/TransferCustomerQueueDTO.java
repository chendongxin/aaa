package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/6/19 10:37
 */
@Data
@Accessors(chain = true)
public class TransferCustomerQueueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息类型 3-招转商机类型
     */
    public int msgType;

    /**
     * 消息体
     */
    private Object msgBody;
}
