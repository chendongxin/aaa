package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/5/30 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NcResponseDTO {


    private String code;

    private String msg;

    private String data;
}
