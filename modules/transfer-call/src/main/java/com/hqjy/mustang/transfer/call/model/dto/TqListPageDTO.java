package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/18 17:13
 */
@Data
public class TqListPageDTO<T extends Serializable> {
    private Integer listSize;
    private Integer total;
    private Integer pageSize;
    private Integer page;
    private List<T> list;
}
