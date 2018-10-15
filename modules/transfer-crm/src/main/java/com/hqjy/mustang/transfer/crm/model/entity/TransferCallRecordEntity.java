package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_call_record 实体类
 * 
 * @author : xyq
 * @date : 2018/10/11 16:16
 */
@Data
public class TransferCallRecordEntity implements Serializable {
    /**
	 * 通话记录主键 id
	 **/
    private Long id;

    /**
	 * 外呼自定义参数，需要base64解码 params
	 **/
    private String params;

    /**
	 * 野马客户ID customer_id
	 **/
    private Long customerId;

    /**
	 * 电话记录唯一事件标识 unique_id
	 **/
    private String uniqueId;

    /**
	 * 座席账号 cno
	 **/
    private String cno;

    /**
	 * 座席电话 client_number
	 **/
    private String clientNumber;

    /**
	 * 客户电话 customer_number
	 **/
    private String customerNumber;

    /**
	 * 客户归属地 customer_area
	 **/
    private String customerArea;

    /**
	 * 当前归属人ID user_id
	 **/
    private Long userId;

    /**
	 * 当前归属人名称 user_name
	 **/
    private String userName;

    /**
	 * 呼叫方式 3:外呼电话 4:直线呼入 call_style
	 **/
    private String callStyle;

    /**
	 * 呼叫途径 1：呼叫中心 2：工作手机 pathway
	 **/
    private String pathway;

    /**
	 * 是否接通  1:接通  0、2、其他:未接通 status
	 **/
    private String status;

    /**
	 * 电话开始呼叫时间 start_time
	 **/
    private Date startTime;

    /**
	 * 通话时长（秒）；long型 total_duration
	 **/
    private Long totalDuration;

    /**
	 * 录音URL链接 record_file
	 **/
    private String recordFile;

    /**
	 * 响铃时长（秒）；long型 ring_time
	 **/
    private Long ringTime;

    /**
	 * 电话提供商记录写入时间 insert_db_time
	 **/
    private Integer insertDbTime;

    /**
	 * 创建人编号 create_user_id
	 **/
    private Long createUserId;

    /**
	 * 创建人姓名 create_user_name
	 **/
    private String createUserName;

    /**
	 * 创建时间 create_time
	 **/
    private Date createTime;

    private static final long serialVersionUID = 1L;
}