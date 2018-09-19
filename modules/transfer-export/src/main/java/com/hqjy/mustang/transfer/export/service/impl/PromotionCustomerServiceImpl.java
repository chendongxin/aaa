package com.hqjy.mustang.transfer.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.CustomerReportData;
import com.hqjy.mustang.transfer.export.model.dto.CustomerReportTotal;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.CustomerQueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionCustomerService;
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
 * @apiNote 客服推广报表数据服务层
 */
@Service
public class PromotionCustomerServiceImpl implements PromotionCustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionCustomerServiceImpl.class);

    @Override
    public R promotionCustomerList(PageParams params, CustomerQueryParams query) {
        List<CustomerReportData> list = new ArrayList<>();

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
    private CustomerReportTotal countTotal(List<CustomerReportData> list) {
        CustomerReportTotal total = new CustomerReportTotal();
        list.forEach(x -> {
            //TODO,导出报表统计待处理
        });
        return total;
    }


    @Override
    public R exportPromotionCustomer(CustomerQueryParams query) {
        try {
            R r = this.promotionCustomerList(null, query);
            if (MapUtils.getLong(r, Constant.CODE) == 0) {
                String result = MapUtils.getString(r, Constant.RESULT);
                List<CustomerReportData> list = JSON.parseArray(result, CustomerReportData.class);
                CustomerReportTotal total = this.countTotal(list);

                ExcelUtil<CustomerReportData, CustomerReportTotal> util1 = new ExcelUtil<>(CustomerReportData.class, CustomerReportTotal.class);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                util1.getListToExcel(list, "客服推广数据报表_", total, os);
                //aliyun目录
                String dir = "export";
                //文件名称
                String fileName = "客服推广数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
                //上传文件至阿里云
                String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
                //下载地址有效时间1个小时
                URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
                return R.result(visitUrl.toString());
            }
            return R.error("导出客服推广报表数据：获取导出数据异常");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
