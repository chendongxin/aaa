package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionSmsCostDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportData;
import com.hqjy.mustang.transfer.export.model.dto.SmsCostReportData;
import com.hqjy.mustang.transfer.export.model.entity.SmsCostEntity;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SmsCostQueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionSmsCostService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广短信费用报表数据服务层
 * 计算费用规则：发送失败不计费，每条短信固定0.035元，单条70字以内都是一条计费，最长可以发300字 超过70就是按67字每条计费
 */
@Service
public class PromotionSmsCostServiceImpl implements PromotionSmsCostService {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionSmsCostServiceImpl.class);

    private static final int CONTENT_LENGTH_BASE = 70;
    private static final double CONTENT_LENGTH_CARDINAL = 67.00;

    private PromotionSmsCostDao promotionSmsCostDao;
    private SysDeptServiceFeign deptServiceFeign;

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionSmsCostDao(PromotionSmsCostDao promotionSmsCostDao) {
        this.promotionSmsCostDao = promotionSmsCostDao;
    }

    @Override
    public PageUtil<SmsCostReportData> promotionSmsCostList(PageParams params, SmsCostQueryParams query) {
        List<SmsCostReportData> list = this.check(query);
        List<SmsCostEntity> entityList = promotionSmsCostDao.getData(query);
        List<SmsCostEntity> collect = entityList.stream().filter(x -> x.getStatus() == 2).collect(Collectors.toList());

        list.forEach(l -> {
            collect.forEach(x -> {
                if (l.getDeptId().equals(x.getDeptId())) {
                    if (x.getContent().length() <= CONTENT_LENGTH_BASE) {
                        l.setSendNum(l.getSendNum() + 1);
                    } else {
                        int count = (int) Math.ceil(x.getContent().length() / CONTENT_LENGTH_CARDINAL);
                        l.setSendNum(l.getSendNum() + count);
                    }
                    l.setSendSuccessNum(l.getSendSuccessNum() + 1);
                }
            });
        });

        return new PageUtil<>(params, list);
    }

    @Override
    public String exportPromotionSmsCost(SmsCostQueryParams query) {
        return null;
    }

    private List<SmsCostReportData> check(SmsCostQueryParams query) {
        if (StringUtils.isEmpty(query.getBeginTime())) {
            throw new RRException("请选择开始时间");
        }
        if (StringUtils.isEmpty(query.getEndTime())) {
            throw new RRException("请选择结束时间");
        }
        if (query.getDeptId() == null) {
            throw new RRException("请选择部门");
        }
        List<SmsCostReportData> list = new ArrayList<>();
        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId());

        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();

        deptList.forEach(y -> {
            list.add(new SmsCostReportData().setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }
}
