package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.SellDeptDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.SellDeptReportData;
import com.hqjy.mustang.transfer.export.model.dto.SellDeptReportResult;
import com.hqjy.mustang.transfer.export.model.dto.SellDeptReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import com.hqjy.mustang.transfer.export.service.SellDeptService;
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
 * @apiNote 部门电销排行报表数据服务层
 */
@Service
public class SellDeptServiceImpl implements SellDeptService {

    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);
    private SysDeptServiceFeign sysDeptServiceFeign;
    private SellDeptDao sellDeptDao;

    @Autowired
    public void setSysDeptServiceFeign(SysDeptServiceFeign sysDeptServiceFeign) {
        this.sysDeptServiceFeign = sysDeptServiceFeign;
    }

    @Autowired
    public void setSellDeptDao(SellDeptDao sellDeptDao) {
        this.sellDeptDao = sellDeptDao;
    }

    @Override
    public SellDeptReportResult sellDeptList(PageParams params, SellQueryParams query) {
        List<SellDeptReportData> list = this.check(query);
        if (!list.isEmpty()) {
            this.setSaleNum(query, list);
            this.setSaleRate(list);
        }
        SellDeptReportTotal total = this.countTotal(list);
        PageUtil<SellDeptReportData> pageList = new PageUtil<>(params, list);
        return new SellDeptReportResult().setPageList(pageList).setTotal(total);
    }

    private List<SellDeptReportData> check(SellQueryParams query) {

        List<SellDeptReportData> list = new ArrayList<>();
        List<SysDeptInfo> deptInfo = sysDeptServiceFeign.getDeptEntityByDeptId(query.getDeptId());

        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();

        deptList.forEach(y -> {
            list.add(new SellDeptReportData().setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }

    private void setSaleNum(SellQueryParams query, List<SellDeptReportData> list) {
        List<CustomerEntity> visitBusiness = sellDeptDao.countVisitBusiness(query);
        List<CustomerEntity> validBusiness = sellDeptDao.countValidBusiness(query);
        List<CustomerEntity> dealBusiness = sellDeptDao.countDealBusiness(query);
        List<CustomerEntity> createBusiness = sellDeptDao.countBusiness(query);
        List<CustomerEntity> visitValidBusiness = sellDeptDao.countVisitValidBusiness(query);
        List<CustomerEntity> reservation = sellDeptDao.countReservation(query);
        list.forEach(x -> {
            //上门量
            visitBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitNum(y.getNum());
                }
            });

            //商机量
            createBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setBusinessNum(y.getNum());
                }
            });
            //商机有效量
            validBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setValidNum(y.getNum());
                }
            });
            //成交量
            dealBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setDealNum(y.getNum());
                }
            });
            //预约量
            reservation.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setReservationNum(y.getNum());
                }
            });
            //有效上门量
            visitValidBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitValidNum(y.getNum());
                }
            });
        });
    }

    private void setSaleRate(List<SellDeptReportData> list) {
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
    public String exportSellDept(SellQueryParams query) {
        try {
            List<SellDeptReportData> list = this.getSellDeptData(query);
            SellDeptReportTotal total = this.countTotal(list);
            ExcelUtil<SellDeptReportData, SellDeptReportTotal> util1 = new ExcelUtil<>(SellDeptReportData.class, SellDeptReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, "部门电销排行数据报表_", total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "部门电销排行数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("部门电销排行数据报表导出异常->{}", e.getMessage());
            throw new RRException("部门电销排行数据报表导出异常");
        }
    }

    private List<SellDeptReportData> getSellDeptData(SellQueryParams query) {
        List<SellDeptReportData> list = this.check(query);
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
    private SellDeptReportTotal countTotal(List<SellDeptReportData> list) {
        SellDeptReportTotal total = new SellDeptReportTotal();
        list.forEach(x -> {
            total.setVisitNum(total.getVisitNum() + x.getVisitNum());
            total.setBusinessNum(total.getBusinessNum() + x.getBusinessNum());
            total.setValidNum(total.getValidNum() + x.getValidNum());
            total.setDealNum(total.getDealNum() + x.getDealNum());
        });
        DecimalFormat df = new DecimalFormat("0.00%");
        //有效商机上门率:有效上门量/有效商机总量
        total.setVisitValidRate(df.format(total.getValidNum() == 0 ? 0 : (double) total.getVisitValidNum() / total.getValidNum()));
        //商机有效率:有效商机量/商机量
        total.setValidRate(df.format(total.getBusinessNum() == 0 ? 0 : (double) total.getValidNum() / total.getBusinessNum()));
        //实际上门率:上门量/预约量
        total.setVisitRate(df.format(total.getReservationNum() == 0 ? 0 : (double) total.getVisitNum() / total.getReservationNum()));
        return total;
    }
}
