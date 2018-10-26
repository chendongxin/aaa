package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "部门电销排行报表数据输出")
public class SellDeptReportResult {

    private List<SellDeptReportData> list;

    private SellDeptReportTotal total;
}
