package com.hqjy.transfer.allot.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 权重轮询算法对象
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:56
 */
@Data
public class WeigthRoundDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 权重
     */
    private Integer weights;
}
