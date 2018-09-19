package com.hqjy.mustang.transfer.call.model.dto;

import com.hqjy.mustang.common.base.utils.StringUtils;
import lombok.Data;

/**
 * @author : heshuangshuang
 * @date : 2018/9/18 17:01
 */
@Data
public class TqAccessTokenDTO {

    private String access_token;

    private Long expires_in;

    private Long errorCode;

    private String message;

    public boolean isSuccess() {
        return StringUtils.isNotEmpty(this.access_token);
    }
}
