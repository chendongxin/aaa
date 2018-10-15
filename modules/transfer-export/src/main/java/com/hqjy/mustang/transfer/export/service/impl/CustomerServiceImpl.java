package com.hqjy.mustang.transfer.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.*;
import com.hqjy.mustang.common.base.validator.ValidatorUtils;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import com.hqjy.mustang.transfer.export.dao.CustomerDao;
import com.hqjy.mustang.transfer.export.feign.SysConfigServiceFeign;
import com.hqjy.mustang.transfer.export.feign.SysMessageServiceFeign;
import com.hqjy.mustang.transfer.export.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.CustomerDetailDTO;
import com.hqjy.mustang.transfer.export.model.dto.CustomerMsgBodyDTO;
import com.hqjy.mustang.transfer.export.model.dto.CustomerUpDTO;
import com.hqjy.mustang.transfer.export.model.entity.CustomerExportEntity;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import com.hqjy.mustang.transfer.export.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private SysConfigServiceFeign sysConfigServiceFeign;

//    @Autowired
//    private CustomerSender customerSender;

    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Autowired
    private SysMessageServiceFeign sysMessageServiceFeign;
    @Autowired
    private CustomerDao customerDao;

    /**
     * 导入客户
     *
     * @param file 导入的文件
     * @param upDTO  请求输入参数
     * @return 返回导入结果
     */
    @Override
    public R importCustomer(MultipartFile file, CustomerUpDTO upDTO) {
        try {
            if (!file.isEmpty()) {
                ExcelUtil<CustomerDetailDTO, Object> util = new ExcelUtil<>(CustomerDetailDTO.class, Object.class);
                List<CustomerDetailDTO> customerSaveDTOList = util.getExcelToList(null, file.getInputStream());
                if (customerSaveDTOList.isEmpty()) {
                    return R.error("导入失败:导入文件不存在客户数据");
                }
                Integer limit = Integer.valueOf(sysConfigServiceFeign.getConfig(ConfigConstant.BIZ_IMPORT_LIMIT));
                if (customerSaveDTOList.size() > limit) {
                    return R.error("导入失败:导入客户数超过限制：【" + limit + "】个");
                }
                //校验导入客户的手机号码,得到手机号码格式不正确的数据
                this.checkPhone(customerSaveDTOList);

                List<CustomerDetailDTO> list = customerSaveDTOList.stream().filter(x -> ValidatorUtils.isPhone(x.getPhone())).collect(Collectors.toList());
                list.forEach(c -> {
                    CustomerMsgBodyDTO msgBody = new CustomerMsgBodyDTO();
                    if (StringUtils.isNotEmpty(c.getGender())) {
                        this.setSex(c, msgBody);
                    }
                    msgBody.setProId(upDTO.getProId()).setProName(upDTO.getProName()).setCompanyId(upDTO.getCompanyId()).setCompanyName(upDTO.getCompanyName())
                            .setDeptId(upDTO.getDeptId()).setDeptName(upDTO.getDeptName()).setSourceId(upDTO.getSourceId()).setSourceName(upDTO.getSourceName())
                            .setUserId(upDTO.getUserId()).setGetWay(upDTO.getGetWay()).setNotAllot(upDTO.getNotAllot())
                            .setName(c.getName()).setAge(Byte.valueOf(c.getAge())).setCreateUserId(getUserId()).setCreateUserName(getUserName())
                            .setPhone(c.getPhone()).setEmail(c.getEmail()).setPositionApplied(c.getPositionApplied())
                            .setWorkingPlace(c.getWorkingPlace()).setSchool(c.getSchool()).setMajor(c.getMajor()).setWorkExperience(Byte.valueOf(c.getExperience()))
                            .setNote(c.getNote());

//                    发送客户数据到商机分配消息队列
//                    customerSender.send(JSON.toJSONString(JSON.toJSON(msgBody)));
                });
                Thread.sleep(1000L);
                return R.ok("导入数据已提交到队列中");
            }
            return R.error("导入失败:请选择文件或者文件大小等于0");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    private void checkPhone(List<CustomerDetailDTO> customerSaveDTOList) {
        List<CustomerDetailDTO> collect = customerSaveDTOList.stream().filter(x -> !ValidatorUtils.isPhone(x.getPhone())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            MessageSendVO msg = new MessageSendVO(getUserId(), "客户导入失败数据", "手机号码错误共：" + collect.size() + "条", collect.stream().map(CustomerDetailDTO::getPhone).toArray());
            sysMessageServiceFeign.sendNotice(msg);
        }
    }

    @Override
    public String exportCustomer(CustomerExportQueryParams query) {
        try {
            List<CustomerExportEntity> customerEntityList = this.getExportData(query);
            ExcelUtil<CustomerExportEntity, Object> util1 = new ExcelUtil<>(CustomerExportEntity.class, Object.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(customerEntityList, "客户报表", null, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "客户报表" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
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

    private List<CustomerExportEntity> getExportData(CustomerExportQueryParams query) {
        if (isGeneralSeat() ) {
            query.setUserId(getUserId());
        }
        if (isSuperAdmin()) {
            return customerDao.getExportData(query);
        }
        List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
        List<String> ids = new ArrayList<>();
        userAllDeptId.forEach(x -> {
            ids.add(String.valueOf(x));
        });
        query.setUserAllDeptId(StringUtils.listToString(ids));
        return customerDao.getExportData(query);
    }

    private void setSex(CustomerDetailDTO c, CustomerMsgBodyDTO msgBody) {
        if (c.getGender().equals(Constant.Gender.MAN.getCode())) {
            msgBody.setSex(Constant.Gender.MAN.getValue());
        }
        if (c.getGender().equals(Constant.Gender.WOMEN.getCode())) {
            msgBody.setSex(Constant.Gender.WOMEN.getValue());
        }
        if (c.getGender().equals(Constant.Gender.UNKNOWN.getCode())) {
            msgBody.setSex(Constant.Gender.UNKNOWN.getValue());
        }
    }
}

