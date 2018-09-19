package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;

/**
 * @author : heshuangshuang
 * @date : 2018/9/18 17:13
 */
@Data
public class TqResponseDTO {
    private Integer errorCode;
    private String message;
    private Object data;
}
