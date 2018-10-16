package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.export.dao.CustomerDao;
import com.hqjy.mustang.transfer.export.dao.InvalidDao;
import com.hqjy.mustang.transfer.export.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.entity.CustomerExportEntity;
import com.hqjy.mustang.transfer.export.model.entity.InvalidExportEntity;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import com.hqjy.mustang.transfer.export.model.query.InvalidQueryParams;
import com.hqjy.mustang.transfer.export.service.InvalidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isGeneralSeat;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isSuperAdmin;

@Service
public class InvalidServiceImpl implements InvalidService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    @Autowired
    private InvalidDao invalidDao;

    @Override
    public String exportInvalid(InvalidQueryParams query) {
        try {
            List<InvalidExportEntity> invalidCustomerList = this.getExportData(query);
            ExcelUtil<InvalidExportEntity, Object> util1 = new ExcelUtil<>(InvalidExportEntity.class, Object.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(invalidCustomerList, "无效客户报表", null, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "无效客户报表" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("客户报表导出异常->{}", e.getMessage());
            throw new RRException("客户报表导出异常:" + e.getMessage());
        }
    }

    private List<InvalidExportEntity> getExportData(InvalidQueryParams query) {
        if (isGeneralSeat() ) {
            query.setUserId(getUserId());
        }
        if (isSuperAdmin()) {
            return invalidDao.getExportData(query);
        }
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        query.setUserAllDeptId(StringUtils.listToString(ids));
        return invalidDao.getExportData(query);
    }
}
