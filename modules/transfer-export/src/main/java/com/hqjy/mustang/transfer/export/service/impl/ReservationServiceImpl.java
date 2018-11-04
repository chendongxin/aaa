package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.export.dao.ReservationDao;
import com.hqjy.mustang.transfer.export.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.entity.ReservationExportEntity;
import com.hqjy.mustang.transfer.export.model.query.ReservationQueryParams;
import com.hqjy.mustang.transfer.export.service.ReservationService;
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

import static com.hqjy.mustang.common.web.utils.ShiroUtils.*;

/**
 * @author xyq
 * @date create on 2018/10/10
 * @apiNote 邀约报表导出服务
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private ReservationDao reservationDao;

    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }

    @Autowired
    public void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public List<ReservationExportEntity> getExportData(ReservationQueryParams params) {
        if (StringUtils.isNotBlank(params.getBeginCreateTime())) {
            params.setBeginCreateTime(DateUtils.getBeginTime(params.getBeginCreateTime()));
        }
        if (StringUtils.isNotBlank(params.getEndCreateTime())) {
            params.setEndCreateTime(DateUtils.getEndTime(params.getEndCreateTime()));
        }
        if (StringUtils.isNotBlank(params.getBeginAppointmentTime())) {
            params.setBeginAppointmentTime(DateUtils.getBeginTime(params.getBeginAppointmentTime()));
        }
        if (StringUtils.isNotBlank(params.getEndAppointmentTime())) {
            params.setEndAppointmentTime(DateUtils.getEndTime(params.getEndAppointmentTime()));
        }
        if (isGeneralSeat()) {
            params.setUserId(getUserId());
        }
        if (isSuperAdmin()) {
            return reservationDao.getExportData(params);
        }
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        if (userAllDeptId.isEmpty()) {
            throw new RRException("用户不存在");
        }
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        params.setUserAllDeptId(StringUtils.listToString(ids));
        return reservationDao.getExportData(params);
    }

    @Override
    public String exportReservation(ReservationQueryParams params) {
        try {
            List<ReservationExportEntity> list = this.getExportData(params);
            ExcelUtil<ReservationExportEntity, Object> util1 = new ExcelUtil<>(ReservationExportEntity.class, Object.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, null, null, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "预约报表" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("预约报表导出异常->{}", e.getMessage());
            throw new RRException("预约报表导出异常:" + e.getMessage());
        }
    }
}
