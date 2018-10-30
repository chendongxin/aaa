package com.hqjy.mustang.transfer.sms.model.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Autor: hutao
 * Date: 2018-10-29-17:10
 */
@Data
public class SmsResultListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SmsStatusDTO> reportList;
}
