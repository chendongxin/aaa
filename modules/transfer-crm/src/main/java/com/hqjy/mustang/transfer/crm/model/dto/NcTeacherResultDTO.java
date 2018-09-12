package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 获取招生老师接口的返回对象
 *
 * @author xieyuqing
 * @ date create in 2018/5/29 17:26
 */
@Data
public class NcTeacherResultDTO {

    private Integer code;
    private List<NcTeacherDTO> msg;

}
