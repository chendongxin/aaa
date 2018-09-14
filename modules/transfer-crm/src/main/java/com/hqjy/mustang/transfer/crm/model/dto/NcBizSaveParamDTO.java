package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * NC 保存商机数据对象
 *
 * @author : heshuangshuang
 * @date : 2018/7/5 9:38
 */
@Data
@Accessors(chain = true)
public class NcBizSaveParamDTO {

    /**
     * 野马客户Id，冗余
     */
    private Long customerId;

    /**
     * 野马销售类型，冗余,用于判断请求NC的不同，如果没有，默认为0，所以不用包装类
     */
    private int saleType;

    /**
     * 分配人员Id,用于查询nc映射，设置销售人员
     */
    private Long userId;

    /**
     * 销售人员编码
     */
    private String saleman;
    private String name;
    private String tel;
    private String lxqq;
    private String csw_shou;
    private String logurl;
    private String note;
    private String creator;
    private String axis_id;
    private String true_name;
    private String channelname;
}
