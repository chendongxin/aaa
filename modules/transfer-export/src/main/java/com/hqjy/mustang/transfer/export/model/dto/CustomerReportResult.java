package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.transfer.export.util.PageUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/7/14 11:11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "客服推广报表输出对象")
public class CustomerReportResult {

    private PageUtil<CustomerReportData> pageList;

    private CustomerReportTotal total;
}
