package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.transfer.export.util.PageUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "部门电销排行报表输出对象")
public class SellDeptReportResult {

    private PageUtil<SellDeptReportData> pageList;

    private SellDeptReportTotal total;
}
