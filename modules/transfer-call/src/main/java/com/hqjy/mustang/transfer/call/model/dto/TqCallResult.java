package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 17:35
 */
@Data
public class TqCallResult implements Serializable {
    private Integer result_status;
    private String result_desc;
}
