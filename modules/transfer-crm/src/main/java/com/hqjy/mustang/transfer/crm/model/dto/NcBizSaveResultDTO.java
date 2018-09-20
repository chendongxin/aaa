package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

/**
 * nc保存返回结果对象
 *
 * @author : heshuangshuang
 * @date : 2018/7/5 15:06
 */
@Data
public class NcBizSaveResultDTO {
    private Integer code;
    private String msg;

    public NcBizSaveResultDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
