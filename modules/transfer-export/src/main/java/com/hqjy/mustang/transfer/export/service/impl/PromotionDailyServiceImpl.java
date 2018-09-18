package com.hqjy.mustang.transfer.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportData;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportTotal;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionDailyService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转日常报表数据服务层
 */
@Service
public class PromotionDailyServiceImpl implements PromotionDailyService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);

    @Override
    public R promotionDailyList(PageParams params, DailyQueryParams query) {
        List<DailyReportData> list = new ArrayList<>();

        //TODO 数据统计业务待完成
        if (params != null) {
            //需要分页
            return R.result(new PageUtil<>(params, list));
        }
        //不需要分页
        return R.result(list);
    }


    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private DailyReportTotal countTotal(List<DailyReportData> list) {
        DailyReportTotal total = new DailyReportTotal();
        list.forEach(x -> {
            //TODO,导出报表统计待处理
        });
        return total;
    }


    @Override
    public R exportPromotionDaily(DailyQueryParams query) {
        try {
            R r = this.promotionDailyList(null, query);
            if (MapUtils.getLong(r, Constant.CODE) == 0) {
                String result = MapUtils.getString(r, Constant.RESULT);
                List<DailyReportData> list = JSON.parseArray(result, DailyReportData.class);
                DailyReportTotal total = this.countTotal(list);

                ExcelUtil<DailyReportData, DailyReportTotal> util1 = new ExcelUtil<>(DailyReportData.class, DailyReportTotal.class);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                util1.getListToExcel(list, "招转日常数据报表_", total, os);
                //aliyun目录
                String dir = "export";
                //文件名称
                String fileName = "招转日常数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
                //上传文件至阿里云
                String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
                //下载地址有效时间1个小时
                URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
                return R.result(visitUrl.toString());
            }
            return R.error("导出日常报表数据：获取导出数据异常");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
