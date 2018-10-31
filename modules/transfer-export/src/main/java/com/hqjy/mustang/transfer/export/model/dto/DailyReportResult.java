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
@ApiModel(value = "招转日常报表输出对象")
public class DailyReportResult {

    private PageUtil<DailyReportData> pageList;

    private DailyReportTotal total;
}
