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
@ApiModel(value = "电销商机拨打排行报表数据输出")
public class SellCallReportResult {

    private List<SellCallReportData> list;

    private SellCallReportTotal total;

}
