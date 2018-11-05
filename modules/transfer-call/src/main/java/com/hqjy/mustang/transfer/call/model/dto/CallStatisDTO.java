package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CallStatisDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 外呼总数
     */
    private int seatAnswer;
    /**
     * 客户接听数
     */
    private int customerAnswer;
    /**
     * 客户接听率
     */
    private String answerRate;
    /**
     * 总通话时长
     */
    private String talkTime;
    private long talkSecond;
    /**
     * 有效通话时长
     */
    private String validTime;
    private long validSecond;
    /**
     * 平均通话时长
     */
    private String averageTime;
}
