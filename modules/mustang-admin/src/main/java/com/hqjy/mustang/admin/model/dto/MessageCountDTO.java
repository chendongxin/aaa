package com.hqjy.mustang.admin.model.dto;

import lombok.Data;

/**
 * @author : heshuangshuang
 * @date : 2018/6/21 16:32
 */
@Data
public class MessageCountDTO {
    private int total;
    private int message;
    private int notice;
    private int todo;
}
