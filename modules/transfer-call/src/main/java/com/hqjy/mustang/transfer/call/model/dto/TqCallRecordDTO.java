package com.hqjy.mustang.transfer.call.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/18 14:12
 */
@Data
public class TqCallRecordDTO implements Serializable {

    /**
     * 电话记录id
     */
    private int id;

    /**
     * 座席账号
     */
    private int uin;

    /**
     * 客户电话
     */
    private String caller_id;

    /**
     * 座席电话
     */
    private String called_id;

    /**
     * 呼叫方式 3:外呼电话 4:直线呼入
     */
    private int call_style;

    /**
     * 处理状态 1:处理 其他：未处理
     */
    private int deal_state;

    /**
     * 该次电话备注
     */
    private String resume;

    /**
     * 电话开始呼叫时间；utc10位
     */
    private int insert_time;

    /**
     * 是否接通  1:接通  0、2、其他:未接通
     */
    private int is_called_phone;

    /**
     * 呼叫途径 1：呼叫中心 2：工作手机
     */
    private int pathway;

    /**
     * 通话时长（秒）；long型
     */
    private Long duration;

    /**
     * 号码归属地，格式为86751755，86为国际码，后面加0为归属地区号如：86-0751-0755
     */
    private int area_id;

    /**
     * 客户归属地；如：浙江杭州市固定电话
     */
    private String callerArea;

    /**
     * 中继号码
     */
    private String dnis;

    /**
     * 呼叫节点
     */
    private String queuename;

    /**
     * 电话满意度评价id；满意度节点中的id值
     */
    private String satisfaction_degree;

    /**
     * 录音URL链接
     */
    private String recordfile;

    /**
     * 坐席工号
     */
    private String seatid;

    /**
     * 电话记录唯一事件标识
     */
    private String fsunique_id;

    /**
     * 呼入系统应答时间；utc10位
     */
    private String caller_stime;

    /**
     * 响铃时长（秒）；long型
     */
    private Long ringTime;

    /**
     * 进入队列时间；utc10位
     */
    private String caller_queue_time;

    /**
     * 中继或第三方电话记录配置的电话媒体id
     */
    private String media_id;

    /**
     * 插库时间
     */
    private Long insert_db_time;

    /**
     * 挂机方 1：座席侧  2：客户侧
     */
    private String hangup_side;

    /**
     * 自定义参数
     */
    private String client_id;
}
