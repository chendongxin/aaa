package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 外呼自定义参数
 *
 * @author : heshuangshuang
 * @date : 2018/9/18 16:34
 */
@Data
@Accessors(chain = true)
public class TqCallClienIdDTO {
    private Long customerId;
    private Long userId;
    private String userName;
}
