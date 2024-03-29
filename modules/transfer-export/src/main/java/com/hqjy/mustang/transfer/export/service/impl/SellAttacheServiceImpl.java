package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
import com.hqjy.mustang.transfer.export.dao.SellAttacheDao;
import com.hqjy.mustang.transfer.export.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportData;
import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportResult;
import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import com.hqjy.mustang.transfer.export.service.SellAttacheService;
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
 * @apiNote 电销专员排行报表数据服务层
 */
@Service
public class SellAttacheServiceImpl implements SellAttacheService {

    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    private SellAttacheDao sellAttacheDao;

    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }

    @Autowired
    public void setSellAttacheDao(SellAttacheDao sellAttacheDao) {
        this.sellAttacheDao = sellAttacheDao;
    }

    /**
     * 获取电销专员排行报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    @Override
    public SellAttacheReportResult sellAttacheList(PageParams params, SellQueryParams query) {
        List<SellAttacheReportData> list = this.check(query);
        if (!list.isEmpty()) {
            this.setSaleNum(query, list);
            this.setSaleRate(list);
        }
        SellAttacheReportTotal total = this.countTotal(list);
        PageUtil<SellAttacheReportData> pageList = new PageUtil<>(params, list);

        return new SellAttacheReportResult().setPageList(pageList).setTotal(total);
    }

    private List<SellAttacheReportData> check(SellQueryParams query) {
        List<SellAttacheReportData> list = new ArrayList<>();
        //获取所选部门下（含子部门，本部门）的电销专员及其对应电销部门
        List<UserDeptInfo> userDeptInfos = sysUserDeptServiceFeign.getUserDeptInfoByDeptId(query.getDeptId());
        List<UserDeptInfo> userDeptInfoList = userDeptInfos.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> deptIds = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        userDeptInfoList.forEach(y -> {
            list.add(new SellAttacheReportData().setUserId(y.getUserId()).setName(y.getUserName()).setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            deptIds.add(String.valueOf(y.getDeptId()));
            userIds.add(String.valueOf(y.getUserId()));
        });

        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(deptIds));
        query.setUserIds(StringUtils.listToString(userIds));
        return list;
    }

    private void setSaleNum(SellQueryParams query, List<SellAttacheReportData> list) {
        List<CustomerEntity> visitBusiness = sellAttacheDao.countVisitBusiness(query);
        List<CustomerEntity> validBusiness = sellAttacheDao.countValidBusiness(query);
        List<CustomerEntity> dealBusiness = sellAttacheDao.countDealBusiness(query);
        List<CustomerEntity> countBusiness = sellAttacheDao.countBusiness(query);
        List<CustomerEntity> allotBusiness = sellAttacheDao.countAllotBusiness(query);
        List<CustomerEntity> visitValidBusiness = sellAttacheDao.countVisitValidBusiness(query);
        List<CustomerEntity> reservation = sellAttacheDao.countReservation(query);
        list.forEach(x -> {
            //上门量
            visitBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setVisitNum(y.getNum());
                }
            });
            //商机有效量
            validBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setValidNum(y.getNum());
                }
            });
            //成交量
            dealBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setDealNum(y.getNum());
                }
            });

            //商机量
            countBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setBusinessNum(y.getNum());
                }
            });
            //分配商机量
            allotBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setAllotNum(y.getNum());
                }
            });
            //预约量
            reservation.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setReservationNum(y.getNum());
                }
            });
            //有效上门量
            visitValidBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId()) && x.getUserId().equals(y.getUserId())) {
                    x.setVisitValidNum(y.getNum());
                }
            });
        });
    }

    private void setSaleRate(List<SellAttacheReportData> list) {
        DecimalFormat df = new DecimalFormat("0.00%");
        list.forEach(x -> {
            //有效商机上门率:有效上门量/有效商机总量
            x.setVisitValidRate(df.format(x.getValidNum() == 0 ? 0 : (double) x.getVisitValidNum() / x.getValidNum()));
            //商机有效率:有效商机量/商机量
            x.setValidRate(df.format(x.getBusinessNum() == 0 ? 0 : (double) x.getValidNum() / x.getBusinessNum()));
            //实际上门率:上门量/预约量
            x.setVisitRate(df.format(x.getReservationNum() == 0 ? 0 : (double) x.getVisitNum() / x.getReservationNum()));
        });
    }

    @Override
    public String exportSellAttache(SellQueryParams query) {
        try {
            List<SellAttacheReportData> list = this.getSellAttacheData(query);
            SellAttacheReportTotal total = this.countTotal(list);
            ExcelUtil<SellAttacheReportData, SellAttacheReportTotal> util1 = new ExcelUtil<>(SellAttacheReportData.class, SellAttacheReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, null, total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "电销专员排行数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("电销专员排行数据报表导出异常->{}", e.getMessage());
            throw new RRException("电销专员排行数据报表导出异常");
        }
    }

    private List<SellAttacheReportData> getSellAttacheData(SellQueryParams query) {
        List<SellAttacheReportData> list = this.check(query);
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
    private SellAttacheReportTotal countTotal(List<SellAttacheReportData> list) {
        SellAttacheReportTotal total = new SellAttacheReportTotal();
        total.setDeptName("N/A");
        list.forEach(x -> {
            total.setVisitNum(total.getVisitNum() + x.getVisitNum());
            total.setValidNum(total.getValidNum() + x.getValidNum());
            total.setDealNum(total.getDealNum() + x.getDealNum());
            total.setBusinessNum(total.getBusinessNum() + x.getBusinessNum());
            total.setAllotNum(total.getAllotNum() + x.getAllotNum());
            total.setVisitValidNum(total.getVisitValidNum() + x.getVisitValidNum());
        });
        DecimalFormat df = new DecimalFormat("0.00%");
        //有效商机上门率:有效上门量/有效商机总量
        total.setVisitValidRate(df.format(total.getValidNum() == 0 ? 0 : (double) total.getVisitValidNum() / total.getValidNum()));
        //商机有效率:有效商机量/商机量
        total.setValidRate(df.format(total.getBusinessNum() == 0 ? 0 : (double) total.getBusinessNum() / total.getBusinessNum()));
        //实际上门率:上门量/预约量
        total.setVisitRate(df.format(total.getReservationNum() == 0 ? 0 : (double) total.getVisitNum() / total.getReservationNum()));
        return total;
    }
}
