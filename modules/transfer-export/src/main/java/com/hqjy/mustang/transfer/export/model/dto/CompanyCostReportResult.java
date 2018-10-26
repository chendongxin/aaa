package com.hqjy.mustang.transfer.export.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018年10月22日15:10:44
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广公司费用报表输出对象")
public class CompanyCostReportResult {

    private List<CompanyCostReportData> list;

    private CompanyCostReportTotal total;
}
