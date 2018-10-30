package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.transfer.export.util.PageUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xyq
 * @date create on 2018年10月22日15:10:44
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广公司费用报表输出对象")
public class CompanyCostReportResult {

    private PageUtil<CompanyCostReportData> pageList;

    private CompanyCostReportTotal total;
}
