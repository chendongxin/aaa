package com.hqjy.mustang.transfer.export.model.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "电销专员排行报表数据输出对象")
public class SellAttacheReportResult {

    private List<SellAttacheReportData> list;

    private SellAttacheReportTotal total;
}
