package com.hqjy.mustang.transfer.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.*;
import com.hqjy.mustang.transfer.export.model.query.QueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionService;
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
 * @apiNote 招转推广报表数据服务层
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Override
    public R promotionList(QueryParams params) {
        List<PromotionReportData> list = new ArrayList<>();
        PromotionReportTotal total = new PromotionReportTotal();

        //TODO 数据统计业务待完成
        return R.result(new PromotionReportResult().setList(list).setTotal(total));
    }

    @Override
    public R exportPromotion(QueryParams params) {
        try {
            R r = this.promotionList(params);
            if (MapUtils.getLong(r, Constant.CODE) == 0) {
                String result = MapUtils.getString(r, Constant.RESULT);
                PromotionReportResult report = JSON.parseObject(result, PromotionReportResult.class);
                ExcelUtil<PromotionReportData, PromotionReportTotal> util1 = new ExcelUtil<>(PromotionReportData.class, PromotionReportTotal.class);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                util1.getListToExcel(report.getList(), "招转推广报表", report.getTotal(), os);
                //aliyun目录
                String dir = "export";
                //文件名称
                String fileName = "招转推广报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
                //上传文件至阿里云
                String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
                //下载地址有效时间1个小时
                URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
                return R.result(visitUrl.toString());
            }
            return R.error("导出招转推广报表数据：获取导出数据异常");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
