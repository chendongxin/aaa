package com.hqjy.mustang.transfer.export.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转日常报表数据输出对象")
public class DailyReportResult {

    private List<DailyReportData> list;

    private DailyReportTotal total;
}