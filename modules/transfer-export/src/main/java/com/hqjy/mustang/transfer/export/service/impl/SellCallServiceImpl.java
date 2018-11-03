package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
import com.hqjy.mustang.transfer.export.dao.SellCallDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.SellCallReportData;
import com.hqjy.mustang.transfer.export.model.dto.SellCallReportResult;
import com.hqjy.mustang.transfer.export.model.dto.SellCallReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import com.hqjy.mustang.transfer.export.service.SellCallService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote 电销商机拨打报表数据服务层
 */
@Service
public class SellCallServiceImpl implements SellCallService {

    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    private SellCallDao sellCallDao;

    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }

    @Autowired
    public void setSellCallDao(SellCallDao sellCallDao) {
        this.sellCallDao = sellCallDao;
    }

    @Override
    public SellCallReportResult sellCallList(PageParams params, SellQueryParams query) {
        List<SellCallReportData> list = this.check(query);
        if (!list.isEmpty()) {
            this.setSaleNum(query, list);
            this.setSaleRate(list);
        }
        SellCallReportTotal total = this.countTotal(list);
        PageUtil<SellCallReportData> pageList = new PageUtil<>(params, list);

        return new SellCallReportResult().setPageList(pageList).setTotal(total);

    }

    private List<SellCallReportData> check(SellQueryParams query) {

        List<SellCallReportData> list = new ArrayList<>();
        //获取所选部门下（含子部门，本部门）的电销专员及其对应电销部门
        List<UserDeptInfo> userDeptInfos = sysUserDeptServiceFeign.getUserDeptInfoByDeptId(query.getDeptId());
        List<UserDeptInfo> userDeptInfoList = userDeptInfos.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> deptIds = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        userDeptInfoList.forEach(y -> {
            list.add(new SellCallReportData().setUserId(y.getUserId()).setSellName(y.getUserName()).setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            deptIds.add(String.valueOf(y.getDeptId()));
            userIds.add(String.valueOf(y.getUserId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(deptIds));
        query.setUserIds(StringUtils.listToString(userIds));
        return list;
    }

    private void setSaleNum(SellQueryParams query, List<SellCallReportData> list) {
        List<CustomerEntity> callBusiness = sellCallDao.countCall(query);
        List<CustomerEntity> connectBusiness = sellCallDao.countConnect(query);
        List<CustomerEntity> callTimeBusiness = sellCallDao.countCallTimeConnect(query);
        list.forEach(x -> {
            //拨打量
            callBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setCallNum(y.getNum());
                }
            });
            //接通量
            connectBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setConnectNum(y.getNum());
                }
            });
            //通话时长
            callTimeBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setDuration(y.getDuration());
                }
            });
        });
    }

    private void setSaleRate(List<SellCallReportData> list) {
        DecimalFormat df = new DecimalFormat("0.00%");
        list.forEach(x -> {
            //接通率:接通量/拨打量
            x.setConnectRate(df.format(x.getCallNum() == 0 ? 0 : (double) x.getConnectNum() / x.getCallNum()));
        });
    }

    @Override
    public String exportSellCall(SellQueryParams query) {
        try {
            List<SellCallReportData> list = this.getSellCallData(query);
            SellCallReportTotal total = this.countTotal(list);
            ExcelUtil<SellCallReportData, SellCallReportTotal> util1 = new ExcelUtil<>(SellCallReportData.class, SellCallReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, "电销商机拨打排行数据报表_", total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "电销商机拨打排行数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("电销商机拨打排行数据报表导出异常->{}", e.getMessage());
            throw new RRException("电销商机拨打排行数据报表导出异常");
        }
    }

    private List<SellCallReportData> getSellCallData(SellQueryParams query) {
        List<SellCallReportData> list = this.check(query);
        if (!list.isEmpty()) {
            this.setSaleNum(query, list);
            this.setSaleRate(list);
        }
        return list;
    }

    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private SellCallReportTotal countTotal(List<SellCallReportData> list) {
        SellCallReportTotal total = new SellCallReportTotal();
        total.setDeptName("N/A");
        list.forEach(x -> {
            total.setCallNum(total.getCallNum() + x.getCallNum());
            total.setConnectNum(total.getConnectNum() + x.getConnectNum());
            total.setDuration(DateUtils.secondToTime(DateUtils.timeToSecond(total.getDuration()) + DateUtils.timeToSecond(x.getDuration())));
        });
        DecimalFormat df = new DecimalFormat("0.00%");
        //接通率:接通量/拨打量
        total.setConnectRate(df.format(total.getCallNum() == 0 ? 0 : (double) total.getConnectNum() / total.getCallNum()));
        return total;
    }
}
