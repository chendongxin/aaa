package com.hqjy.transfer.allot.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author : heshuangshuang
 * @date : 2018/6/7 16:56
 */
@Data
public class AllotClassDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排版ID
     */
    private Long classId;

    /**
     * 值班日期
     */
    private LocalDate dutyDate;
    /**
     * 值班开始时间
     */
    private LocalTime startTime;
    /**
     * 值班结束时间
     */
    private LocalTime stopTime;

    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date stop;


}
