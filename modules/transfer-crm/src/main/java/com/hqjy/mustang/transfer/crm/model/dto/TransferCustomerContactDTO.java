package com.hqjy.mustang.transfer.crm.model.dto;


import lombok.Data;

/**
 * @author xieyuqing
 * @ description 客户联系方式
 * @ date create in 2018年8月20日10:30:23
 */
@Data
public class TransferCustomerContactDTO {

    /**
     * 客户编号
     **/
    private Long customerId;
    /**
     * 邮箱
     **/
    private String email;

    /**
     * 手机
     **/
    private String phone;
    /**
     * 微信
     **/
    private String weiXin;
    /**
     * QQ
     **/
    private String qq;

    /**
     * 座机
     **/
    private String landLine;
}
