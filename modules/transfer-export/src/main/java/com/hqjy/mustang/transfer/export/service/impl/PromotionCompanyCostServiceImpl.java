package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.*;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.common.model.crm.TransferGenWayInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionCompanyCostDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.feign.TransferGenWayFeign;
import com.hqjy.mustang.transfer.export.model.dto.*;
import com.hqjy.mustang.transfer.export.model.entity.CompanyCostEntity;
import com.hqjy.mustang.transfer.export.model.entity.CompanyCustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionCompanyCostService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.apache.poi.hssf.usermodel.*;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广公司费用业务层
 */
@Service
public class PromotionCompanyCostServiceImpl implements PromotionCompanyCostService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionCompanyCostServiceImpl.class);

    private final static String INITIATIVE_RMB = "人民币主动";
    private final static String INITIATIVE_VIRTUAL = "虚拟币主动";
    private final static String PASSIVE_RMB = "人民币被动";
    private final static String PASSIVE_VIRTUAL = "虚拟币被动";

    private PromotionCompanyCostDao promotionCompanyCostDao;
    private SysDeptServiceFeign deptServiceFeign;
    private TransferGenWayFeign transferGenWayFeign;

    @Autowired
    public void setTransferGenWayFeign(TransferGenWayFeign transferGenWayFeign) {
        this.transferGenWayFeign = transferGenWayFeign;
    }

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionCompanyCostDao(PromotionCompanyCostDao promotionCompanyCostDao) {
        this.promotionCompanyCostDao = promotionCompanyCostDao;
    }

    /**
     * 获取推广公司费用报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    @Override
    public CompanyCostReportResult promotionCompanyCostList(PageParams params, CompanyCostQueryParams query) {
        List<CompanyCostReportData> list = this.initReport(query);
        this.setReportValue(query, list);
        CompanyCostReportTotal total = this.countTotal(list);
        PageUtil<CompanyCostReportData> page = new PageUtil<>(params, list);

        return new CompanyCostReportResult().setList(page.getList()).setTotal(total);
    }

    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private CompanyCostReportTotal countTotal(List<CompanyCostReportData> list) {
        CompanyCostReportTotal total = new CompanyCostReportTotal();
        //确定推广方式合计对象
        List<TransferGenWayCost> totalGenWayCosts = new ArrayList<>();
        List<TransferGenWayCost> genWayCosts = list.get(0).getGenWayCosts();
        genWayCosts.forEach(w -> {
            TransferGenWayCost genWay = new TransferGenWayCost().setWayId(w.getWayId()).setGenWay(w.getGenWay());
            totalGenWayCosts.add(genWay);
        });
        total.setGenWayCosts(totalGenWayCosts);
        //数据合计处理
        total.setDate("合计");
        list.forEach(x -> {
            total.setTotalCost(new BigDecimal(total.getTotalCost()).add(new BigDecimal(x.getTotalCost())).toString());
            total.setNum(total.getNum() + x.getNum());
            List<TransferGenWayCost> genWayCosts1 = x.getGenWayCosts();
            genWayCosts1.forEach(w -> {
                totalGenWayCosts.forEach(tw -> {
                    if (w.getWayId().equals(tw.getWayId())) {
                        tw.setCost(new BigDecimal(tw.getCost()).add(new BigDecimal(w.getCost())).toString());
                    }
                });
            });
        });

        return total;
    }

    /**
     * 设置报表数据
     *
     * @param query 前端请求参数
     * @param list  初始化后的推广公司费用报表对象
     */
    private void setReportValue(CompanyCostQueryParams query, List<CompanyCostReportData> list) {
        List<String> costType = this.checkCostType(query);
        List<CompanyCostEntity> companyCost = promotionCompanyCostDao.getCompanyCost(query);
        List<CompanyCustomerEntity> customerEntities = promotionCompanyCostDao.countCustomer(query);

        List<TransferGenWayInfo> allGenWayList = transferGenWayFeign.getAllGenWayList();
        List<TransferGenWayCost> wayCosts = new ArrayList<>();
        allGenWayList.forEach(w -> {
            wayCosts.add(new TransferGenWayCost().setWayId(w.getWayId()).setGenWay(w.getGenWay()));
        });

        list.forEach(x -> {
            customerEntities.forEach(y -> {
                if (x.getDate().equals(y.getDate())) {
                    x.setNum(y.getNum());
                }
            });
            companyCost.forEach(y -> {
                BigDecimal money = this.getCostTypeMoney(costType, y);
                if (x.getDate().equals(y.getDate())) {
                    wayCosts.forEach(w -> {
                        if (w.getWayId().equals(y.getWayId())) {
                            w.setCost(money.toString());
                        }
                    });
                }
            });
            x.setGenWayCosts(wayCosts);
            wayCosts.forEach(w -> {
                x.setTotalCost(new BigDecimal(w.getCost()).add(new BigDecimal(x.getTotalCost())).toString());
            });
        });
    }

    /**
     * 初始化 推广公司费用报表对象（列出所选日期间的所有日期）
     *
     * @param query 前端请求查询条件
     * @return 返回初始化对象
     */
    private List<CompanyCostReportData> initReport(CompanyCostQueryParams query) {
        List<CompanyCostReportData> list = new ArrayList<>();
        List<String> dates = DateUtils.getBetweenDates(query.getBeginTime(), query.getEndTime());
        dates.forEach(x -> {
            list.add(new CompanyCostReportData().setDate(x));
        });
        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId());
        List<String> ids = new ArrayList<>();
        if (deptInfo.isEmpty()) {
            throw new RRException("部门不存在");
        }
        deptInfo.forEach(y -> {
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }


    /**
     * 判断费用类型
     *
     * @param query 前端请求参数
     * @return 返回报表计算费用类型
     */
    private List<String> checkCostType(CompanyCostQueryParams query) {
        List<String> costType = new ArrayList<>();
        if (query.getGetWay().equals(Constant.GetWayStatus.ACTIVE_GET.getValue())) {
            if (query.getCostType() == Constant.CostType.RMB.getValue()) {
                costType.add(INITIATIVE_RMB);
            } else {
                costType.add(INITIATIVE_VIRTUAL);
            }
        } else {
            if (query.getCostType() == Constant.CostType.RMB.getValue()) {
                costType.add(PASSIVE_RMB);
            } else {
                costType.add(PASSIVE_VIRTUAL);
            }
        }
        return costType;
    }

    /**
     * 获取所选费用类型对应的费用
     *
     * @param costType 费用类型
     * @param y        推广公司费用对象
     * @return 返回费用
     */
    private BigDecimal getCostTypeMoney(List<String> costType, CompanyCostEntity y) {
        BigDecimal money;
        switch (costType.get(0)) {
            case INITIATIVE_RMB:
                money = y.getInitiativeMoney();
                break;
            case INITIATIVE_VIRTUAL:
                money = y.getInitiativeVirtual();
                break;
            case PASSIVE_RMB:
                money = y.getPassiveMoney();
                break;
            case PASSIVE_VIRTUAL:
                money = y.getPassiveVirtual();
                break;
            default:
                money = BigDecimal.ZERO;
        }
        return money;
    }


    /**
     * 导出推广公司费用报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    @Override
    public String exportPromotionCompanyCost(CompanyCostQueryParams query) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("招转推广公司费用报表");

            List<String> headerValue = new ArrayList<>();
            headerValue.add("日期");
            headerValue.add("总费用");
            headerValue.add("商机量");
            List<TransferGenWayInfo> allGenWayList = transferGenWayFeign.getAllGenWayList();
            for (TransferGenWayInfo w : allGenWayList) {
                headerValue.add(w.getGenWay());
            }

            //获取报表数据
            List<CompanyCostReportData> list = this.initReport(query);
            this.setReportValue(query, list);
            //设置表头标题
            this.setSheetHead(query, wb, sheet, allGenWayList);
            //设置报表表头列
            this.createWorkBookColumn(wb, sheet, headerValue);
            //写入数据
            this.setSheetData(sheet, list);

            wb.write(os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "招转推广公司费用报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (IOException e) {
            LOG.error("招转推广公司费用报表导出异常->{}", e.getMessage());
            throw new RRException("招转日常数据报表导出异常");
        }
    }

    /**
     * 设置表头标题
     *
     * @param query         请求参数
     * @param wb            工作簿对象
     * @param sheet         工作表 对象
     * @param allGenWayList 推广方式集合对象
     */
    private void setSheetHead(CompanyCostQueryParams query, HSSFWorkbook wb, HSSFSheet sheet, List<TransferGenWayInfo> allGenWayList) {
        HSSFFont font = wb.createFont();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 28);

        //设置表头标题
        HSSFCell cell = sheet.createRow(0).createCell((allGenWayList.size() + 3) / 2);
        String sheetHead = query.getDeptName() + "_" + query.getCompanyName() + "_" + query.getSourceName();
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(sheetHead);
    }

    /**
     * 设置报表表头列
     *
     * @param wb          工作簿对象
     * @param sheet       工作表 对象
     * @param headerValue 报表表头列集合
     */
    private void createWorkBookColumn(HSSFWorkbook wb, HSSFSheet sheet, List<String> headerValue) {
        HSSFFont font = wb.createFont();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        font.setFontName("Arail narrow");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFRow header = sheet.createRow(1);
        header.setHeightInPoints(20);
        for (int i = 0; i < headerValue.size(); i++) {
            String value = headerValue.get(i);
            HSSFCell cell = header.createCell(i);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, 8 * 200);
            // 设置列中写入内容为String类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            //设置值
            cell.setCellValue(value);
        }
        sheet.createFreezePane(0, 1);
    }

    /**
     * 写入数据
     *
     * @param sheet 工作表 对象
     * @param list  报表整体数据集合
     */
    private void setSheetData(HSSFSheet sheet, List<CompanyCostReportData> list) {
        int size = list.size();
        //标签页最大65535条记录
        int sheetSize = 65535;
        if (size > sheetSize) {
            throw new RRException("本次导出记录:" + size + ",大于65535条，请分段进行导出");
        }
        for (int i = 0; i < size; i++) {
            CompanyCostReportData r = list.get(i);
            HSSFRow row = sheet.createRow(i + 2);
            //行高
            row.setHeightInPoints(16);
            this.buildCell(row, 0, r.getDate());
            this.buildCell(row, 1, r.getTotalCost());
            this.buildCell(row, 2, String.valueOf(r.getNum()));
            List<TransferGenWayCost> genWayCosts = r.getGenWayCosts();
            for (int j = 0; j < genWayCosts.size(); j++) {
                this.buildCell(row, j + 3, genWayCosts.get(j).getCost());
            }
        }
    }

    /**
     * 设置单元值
     *
     * @param row    行
     * @param column 列
     * @param value  单元格值
     */
    private void buildCell(HSSFRow row, int column, String value) {
        row.createCell(column).setCellValue(value);
    }
}
