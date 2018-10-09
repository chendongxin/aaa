package com.hqjy.mustang.transfer.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.*;
import com.hqjy.mustang.common.base.validator.ValidatorUtils;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisLockUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.feign.SysConfigServiceFeign;
import com.hqjy.mustang.transfer.crm.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.feign.SysMessageServiceFeign;
import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.model.entity.*;
import com.hqjy.mustang.transfer.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
@Slf4j
public class TransferCustomerServiceImpl extends BaseServiceImpl<TransferCustomerDao, TransferCustomerEntity, Long> implements TransferCustomerService {

    @Autowired
    private TransferCustomerDetailDao transferCustomerDetailDao;
    @Autowired
    private TransferCustomerContactService transferCustomerContactService;
    @Autowired
    private TransferCustomerRepeatService transferCustomerRepeatService;
    @Autowired
    private TransferCustomerDetailService transferCustomerDetailService;
    @Autowired
    private TransferProcessService transferProcessService;
    @Autowired
    private TransferFollowService transferFollowService;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SysMessageServiceFeign sysMessageServiceFeign;
    @Autowired
    private SysConfigServiceFeign sysConfigServiceFeign;
    @Autowired
    private SysDeptServiceFeign sysDeptServiceFeign;
    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;
    //    @Autowired
//    private ThreadPoolExecutor receiveExecutor;
    @Autowired
    private RedisLockUtils redisLockUtils;
//    @Autowired
//    private CustomerSender customerSender;

    /**
     * 获取某客户的基本数据
     *
     * @param customerId 客户编号
     * @return 返回结果
     */
    @Override
    public TransferCustomerDetailDTO getCustomerData(Long customerId) {
        TransferCustomerEntity customerEntity = baseDao.findOne(customerId);
        TransferCustomerDetailEntity customerDetail = transferCustomerDetailService.getCustomerDetailByCustomerId(customerId);
        TransferCustomerDetailDTO customer = new TransferCustomerDetailDTO()
                .setStatus(customerEntity.getStatus()).setName(customerEntity.getName()).setCreateUserId(customerEntity.getCreateUserId())
                .setCreateUserName(customerEntity.getCreateUserName()).setDeptId(customerEntity.getDeptId()).setDeptName(customerEntity.getDeptName())
                .setCreateTime(customerEntity.getCreateTime()).setAge(customerDetail.getAge()).setSex(customerDetail.getSex()).setGetWay(customerEntity.getGetWay())
                .setEducationId(customerDetail.getEducationId()).setPositionApplied(customerDetail.getPositionApplied())
                .setMajor(customerDetail.getMajor()).setApplyType(customerDetail.getApplyType()).setApplyKey(customerDetail.getApplyKey())
                .setSchool(customerDetail.getSchool()).setGraduateDate(customerDetail.getGraduateDate()).setWorkExperience(customerDetail.getWorkExperience())
                .setWorkingPlace(customerDetail.getWorkingPlace()).setResumeDetail(customerDetail.getResumeDetail()).setNote(customerDetail.getNote());
        customer.setContactList(transferCustomerContactService.findListByCustomerId(customerId));
        return customer;
    }

    /**
     * 新增客户
     *
     * @param customerDto 客户信息对象
     */
    @Override
    @Transactional(rollbackFor = RRException.class)
    public R saveTransferCustomer(TransferCustomerDTO customerDto) {
        try {
            boolean phone = ValidatorUtils.isPhone(customerDto.getPhone());
            if (!phone) {
                throw new RRException("手机号码格式错误");
            }
            Map<String, Object> customerHashMap = new HashMap<>();
            customerHashMap.put("proId", customerDto.getProId());
            customerHashMap.put("phone", customerDto.getPhone());
            List<TransferCustomerEntity> transferCustomerList = baseDao.findList(customerHashMap);
            if (transferCustomerList.size() > 0) {
                //如果存在，将客户添加到重单客户表中
                System.out.println("list.get(0) = " + transferCustomerList.get(0));
                TransferCustomerEntity customer = baseDao.findOne(transferCustomerList.get(0).getCustomerId());
                transferCustomerRepeatService.save(
                        new TransferCustomerRepeatEntity()
                                .setCustomerId(customer.getCustomerId()).setPhone(customerDto.getPhone()).setWeChat(customerDto.getWeChat()).setQq(customerDto.getWeChat())
                                .setLandLine(customerDto.getLandLine()).setDeptId(customerDto.getDeptId()).setDeptName(customerDto.getDeptName()).setCompanyId(customerDto.getCompanyId())
                                .setCompanyName(customerDto.getCompanyName()).setSourceId(customerDto.getSourceId()).setSourceName(customerDto.getSourceName()).setName(customerDto.getName())
                                .setMemo(customerDto.getNote()).setUserId(customerDto.getFirstUserId()).setUserName(customerDto.getFirstUserName()).setCreateUserId(getUserId()).
                                setCreateUserName(getUserName()).setProId(customerDto.getProId()).setProName(customerDto.getProName())
                );
                return R.error(StatusCode.BIZ_CUSTOMER_HAS_EXIT);
            } else {
                Date date = new Date();
                TransferCustomerEntity entity = new TransferCustomerEntity()
                        .setPhone(customerDto.getPhone()).setWeChat(customerDto.getWeChat()).setQq(customerDto.getQq()).setLandLine(customerDto.getLandLine())
                        .setDeptId(customerDto.getDeptId()).setDeptName(customerDto.getDeptName()).setCompanyId(customerDto.getCompanyId()).setCompanyName(customerDto.getCompanyName())
                        .setSourceId(customerDto.getSourceId()).setSourceName(customerDto.getSourceName()).setProId(customerDto.getProId()).setProName(customerDto.getProName())
                        .setFirstUserId(customerDto.getFirstUserId()).setFirstUserName(customerDto.getFirstUserName()).setName(customerDto.getName()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
                        .setAllotTime(date).setGetWay(customerDto.getGetWay());
                super.save(entity);
                customerDto.setCustomerId(entity.getCustomerId());
                transferCustomerDetailService.save(
                        new TransferCustomerDetailEntity()
                                .setCustomerId(entity.getCustomerId()).setSex(customerDto.getSex()).setAge(customerDto.getAge())
                                .setEducationId(customerDto.getEducationId()).setSchool(customerDto.getSchool()).setGraduateDate(customerDto.getGraduateDate())
                                .setMajor(customerDto.getMajor()).setApplyType(customerDto.getApplyType()).setPositionApplied(customerDto.getPositionApplied())
                                .setWorkingPlace(customerDto.getWorkingPlace()).setApplyKey(customerDto.getApplyKey()).setWorkExperience(customerDto.getWorkExperience())
                                .setNote(customerDto.getNote()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
                );
                transferCustomerContactService.save(customerDto);
                Date time = new Date();
                TransferProcessEntity transferProcessEntity = new TransferProcessEntity()
                        .setUserId(customerDto.getFirstUserId()).setCustomerId(entity.getCustomerId()).setDeptId(customerDto.getDeptId())
                        .setCreateTime(time).setExpireTime(time).setMemo("客户新增操作").setCreateUserId(getUserId()).setCreateUserName(getUserName());
                transferProcessService.save(transferProcessEntity);
                return R.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }
    }

    /**
     * 转移客户
     *
     * @param dto 请求参数
     * @return 返回转移结果
     */
    @Override
    @Transactional(rollbackFor = RRException.class)
    public R transferCustomer(TransferCustomerTransferDTO dto) {
        Date date = new Date();
        try {
            //转移客户，新增新的流程记录
            dto.getCustomerId().forEach(c -> {
//                String error = "用户【" + getUserId() + "】转移商机【" + c + "】:";
                TransferProcessEntity process = transferProcessService.getProcessByCustomerId(c);
                if (null == process) {
                    return;
                }
                //设置流程过期
                int i = transferProcessService.disableProcessActive(process);
                if (i == 0) {
                    return;
                }
                //新增流程记录
                TransferProcessEntity newProcess = new TransferProcessEntity().setCreateTime(date).setMemo("客户转移操作").setActive(Boolean.FALSE)
                        .setDeptId(dto.getDeptId()).setDeptName(dto.getDeptName()).setUserId(dto.getUserId()).setUserName(getUserName()).setCustomerId(c).setCreateUserId(getUserId()).setCreateUserName(getUserName());
                int save = transferProcessService.save(newProcess);
                if (save == 0) {
                    return;
                }
                //更新客户主表(同步激活状态流程)
                int update = baseDao.update(new TransferCustomerEntity().setAllotTime(date).setUpdateTime(date)
                        .setCustomerId(c).setDeptId(dto.getDeptId()).setDeptName(dto.getDeptName()).setUserId(dto.getUserId()).setUserName(getUserName()).setUpdateUserId(getUserId()).setUpdateUserName(getUserName()));
                if (update == 0) {
                    return;
                }
                //转移发送消息给用户
                MessageSendVO msg = new MessageSendVO(dto.getUserId(), "来自" + getUserName() + "转移的商机", "客户编号：" + c, newProcess);
                sysMessageServiceFeign.sendMessage(msg);
            });
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }

    }

    private List<TransferCustomerContactEntity> checkContactHasExit(TransferCustomerDTO customerDto) {
        List<TransferCustomerContactEntity> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(customerDto.getPhone())) {
            TransferCustomerContactEntity detail = transferCustomerContactService.getByDetail(Constant.CustomerContactType.PHONE.getValue(), customerDto.getPhone());
            if (detail != null) {
                list.add(detail);
            }
            return list;
        }
        if (StringUtils.isNotEmpty(customerDto.getLandLine())) {
            TransferCustomerContactEntity detail = transferCustomerContactService.getByDetail(Constant.CustomerContactType.LAND_LINE.getValue(), customerDto.getLandLine());
            if (detail != null) {
                list.add(detail);
            }
            return list;
        }
        if (StringUtils.isNotEmpty(customerDto.getWeChat())) {
            TransferCustomerContactEntity detail = transferCustomerContactService.getByDetail(Constant.CustomerContactType.WE_CHAT.getValue(), customerDto.getWeChat());
            if (detail != null) {
                list.add(detail);
            }
            return list;
        }
        if (StringUtils.isNotEmpty(customerDto.getQq())) {
            TransferCustomerContactEntity detail = transferCustomerContactService.getByDetail(Constant.CustomerContactType.QQ.getValue(), customerDto.getQq());
            if (detail != null) {
                list.add(detail);
            }
            return list;
        }
        return list;
    }


    /**
     * 导入客户
     *
     * @param file  导入的文件
     * @param upDTO 请求输入参数
     * @return 返回导入结果
     */
    @Override
    public R importCustomer(MultipartFile file, TransferCustomerUpDTO upDTO) {
        try {
            if (!file.isEmpty()) {
                ExcelUtil<TransferCustomerDetailDTO, Object> util = new ExcelUtil<>(TransferCustomerDetailDTO.class, Object.class);
                List<TransferCustomerDetailDTO> customerSaveDTOList = util.getExcelToList(null, file.getInputStream());
                if (customerSaveDTOList.isEmpty()) {
                    return R.error("导入失败:导入文件不存在客户数据");
                }
                Integer limit = Integer.valueOf(sysConfigServiceFeign.getConfig(ConfigConstant.BIZ_IMPORT_LIMIT));
                if (customerSaveDTOList.size() > limit) {
                    return R.error("导入失败:导入客户数超过限制：【" + limit + "】个");
                }
                //校验导入客户的手机号码,得到手机号码格式不正确的数据
                this.checkPhone(customerSaveDTOList);

                List<TransferCustomerDetailDTO> list = customerSaveDTOList.stream().filter(x -> ValidatorUtils.isPhone(x.getPhone())).collect(Collectors.toList());
                list.forEach(c -> {
                    TransferCustomerMsgBodyDTO msgBody = new TransferCustomerMsgBodyDTO();
                    if (StringUtils.isNotEmpty(c.getGender())) {
                        this.setSex(c, msgBody);
                    }
                    msgBody.setProId(upDTO.getProId()).setCompanyId(upDTO.getCompanyId()).setDeptId(upDTO.getDeptId()).setSourceId(upDTO.getSourceId())
                            .setUserId(upDTO.getUserId()).setGetWay(upDTO.getGetWay()).setNotAllot(upDTO.getNotAllot()).setCustomerId(c.getCustomerId())
                            .setName(c.getName()).setSex(c.getSex()).setAge(c.getAge()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
                            .setPhone(c.getPhone()).setEmail(c.getEmail()).setPositionApplied(c.getPositionApplied()).setWorkingPlace(c.getWorkingPlace())
                            .setSchool(c.getSchool()).setMajor(c.getMajor()).setWorkExperience(c.getWorkExperience())
                            .setNote(c.getNote());

                    //发送客户数据到商机分配消息队列
//                    customerSender.send(JSON.toJSONString(new TransferCustomerQueueDTO()
//                            .setMsgType(2)
//                            .setMsgBody(JSON.toJSON(msgBody))));
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

    @Override
    public R exportCustomer(PageQuery query) {
        try {
            List<TransferCustomerEntity> customerEntityList = this.findExportCustomer(query);
            String customerIds = "";
            customerEntityList.forEach(x -> {
                customerIds.concat(String.valueOf(x.getCompanyId()));
            });
            List<TransferCustomerExportEntity> customerExportList = transferCustomerDetailDao.getAllInformationExport(customerIds);
            List<TransferCustomerExportDTO> exportList = new ArrayList<>();
            this.setCustomerExportData(customerExportList, exportList);

            ExcelUtil<TransferCustomerExportDTO, Object> util1 = new ExcelUtil<>(TransferCustomerExportDTO.class, Object.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(exportList, "客户列表", null, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "客户数据_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            return R.ok(OssFileUtils.getVisitUrl(recordFile, 3600).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    private void checkPhone(List<TransferCustomerDetailDTO> customerSaveDTOList) {
        List<TransferCustomerDetailDTO> collect = customerSaveDTOList.stream().filter(x -> !ValidatorUtils.isPhone(x.getPhone())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            MessageSendVO msg = new MessageSendVO(getUserId(), "客户导入失败数据", "手机号码错误共：" + collect.size() + "条", collect.stream().map(TransferCustomerDetailDTO::getPhone).toArray());
            sysMessageServiceFeign.sendNotice(msg);
        }
    }

    private void setSex(TransferCustomerDetailDTO c, TransferCustomerMsgBodyDTO msgBody) {
        if (c.getSex().equals(Constant.Gender.MAN.getCode())) {
            msgBody.setSex(Constant.Gender.MAN.getValue());
        }
        if (c.getSex().equals(Constant.Gender.WOMEN.getCode())) {
            msgBody.setSex(Constant.Gender.WOMEN.getValue());
        }
        if (c.getSex().equals(Constant.Gender.UNKNOWN.getCode())) {
            msgBody.setSex(Constant.Gender.UNKNOWN.getValue());
        }
    }

    @Override
    public void formatQueryTime(PageQuery pageQuery) {
        if (StringUtils.isNotEmpty(MapUtils.getString(pageQuery, "beginCreateTime"))) {
            pageQuery.put("beginCreateTime", DateUtils.getBeginTime(MapUtils.getString(pageQuery, "beginCreateTime")));
        }
        if (StringUtils.isNotEmpty(MapUtils.getString(pageQuery, "endCreateTime"))) {
            pageQuery.put("endCreateTime", DateUtils.getEndTime(MapUtils.getString(pageQuery, "endCreateTime")));
        }
        if (StringUtils.isNotEmpty(MapUtils.getString(pageQuery, "beginAllotTime"))) {
            pageQuery.put("beginAllotTime", DateUtils.getBeginTime(MapUtils.getString(pageQuery, "beginAllotTime")));
        }
        if (StringUtils.isNotEmpty(MapUtils.getString(pageQuery, "endAllotTime"))) {
            pageQuery.put("endAllotTime", DateUtils.getEndTime(MapUtils.getString(pageQuery, "endAllotTime")));
        }
    }

    private List<TransferCustomerEntity> findExportCustomer(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, "customerId");
        if (customerId != null && MapUtils.getLong(pageQuery, "customerId").equals(-1L)) {
            return new ArrayList<>();
        }
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);
//            将List<Long>部门ID集合转换成字符串 如：[1,2,3]->（'1','2','3'）
            List<String> ids = new ArrayList<>();
            allDeptUnderDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        this.formatQueryTime(pageQuery);
        //如果没有刷选部门过滤条件
        if (null == deptId) {
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
            List<String> deptIds = new ArrayList<>();
            userAllDeptId.forEach(x -> {
                deptIds.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(deptIds));
        }
        this.checkLimit(pageQuery);
        return baseDao.findPage(pageQuery);
    }

    private void checkLimit(PageQuery pageQuery) {
        int i = baseDao.countExportCustomer(pageQuery);
        if (i == 0) {
            throw new RRException("无数据需要导出");
        }
        Integer limit = Integer.valueOf(sysConfigServiceFeign.getConfig(ConfigConstant.BIZ_EXPORT_LIMIT));
        if (i > limit) {
            throw new RRException("导出失败->导出客户数超过限制：" + limit + "个");
        }
    }


    private void setGender(TransferCustomerExportEntity customer, TransferCustomerExportDTO exportDTO) {
        if (customer.getSex().equals(Constant.Gender.MAN.getValue())) {
            exportDTO.setGender(Constant.Gender.MAN.getCode());
        }
        if (customer.getSex().equals(Constant.Gender.WOMEN.getValue())) {
            exportDTO.setGender(Constant.Gender.WOMEN.getCode());
        }
        if (customer.getSex().equals(Constant.Gender.UNKNOWN.getValue())) {
            exportDTO.setGender(Constant.Gender.UNKNOWN.getCode());
        }
    }

    private void setContact(TransferCustomerExportDTO exportDTO, TransferCustomerContactDTO c) {
        exportDTO.setPhone(c.getPhone());
        exportDTO.setQq(c.getQq());
        exportDTO.setWeiXin(c.getWeiXin());
        exportDTO.setLandLine(c.getLandLine());
    }

    private void setFollowStatus(TransferCustomerExportDTO exportDTO, Long followStatus) {

        if (followStatus == Constant.FollowStatus.POTENTIAL.getValue()) {
            exportDTO.setFollowStatus(Constant.FollowStatus.POTENTIAL.getCode());
        }
        if (followStatus == Constant.FollowStatus.VALID_DATA.getValue()) {
            exportDTO.setFollowStatus(Constant.FollowStatus.VALID_DATA.getCode());
        }
        if (followStatus == Constant.FollowStatus.INVALID_DATA.getValue()) {
            exportDTO.setFollowStatus(Constant.FollowStatus.INVALID_DATA.getCode());
        }
        if (followStatus == Constant.FollowStatus.RESERVATION.getValue()) {
            exportDTO.setFollowStatus(Constant.FollowStatus.RESERVATION.getCode());
        }
        if (followStatus == Constant.FollowStatus.DEAL.getValue()) {
            exportDTO.setFollowStatus(Constant.FollowStatus.DEAL.getCode());
        }
    }

    private void setGetWay(TransferCustomerExportEntity customer, TransferCustomerExportDTO exportDTO) {
        if (customer.getGetWay().equals(Constant.GetWayStatus.ACTIVE_GET.getValue())) {
            exportDTO.setGender(Constant.GetWayStatus.ACTIVE_GET.getCode());
        }
        if (customer.getGetWay().equals(Constant.GetWayStatus.PASSIVE_GET.getValue())) {
            exportDTO.setGender(Constant.GetWayStatus.PASSIVE_GET.getCode());
        }
    }

    /**
     * 获取客户导出报表数据
     *
     * @param list       客户集合
     * @param exportList 导出数据集合
     */
    private void setCustomerExportData(List<TransferCustomerExportEntity> list, List<TransferCustomerExportDTO> exportList) {

        List<String> customerIds = new ArrayList<>();
        list.forEach(x -> {
            customerIds.add(String.valueOf(x.getCustomerId()));
        });
        //获取商机首次分配记录
        List<TransferProcessEntity> firstAllotProcessBatch = transferProcessService.getFirstAllotProcessBatch(StringUtils.listToString(customerIds));
        //获取商机通话跟进次数
        List<TransferAnswerCountEntity> answerCountBatch = baseDao.getAnswerCountBatch(StringUtils.listToString(customerIds));
        //获取最新的跟进记录
        List<TransferFollowEntity> latestFollowBatch = transferFollowService.getLatestByCustomerIdBatch(StringUtils.listToString(customerIds));
        //获取联系方式
        List<TransferCustomerContactDTO> contactBatch = transferCustomerContactService.findByCustomerIds(StringUtils.listToString(customerIds));
        list.forEach(x -> {
            TransferCustomerExportDTO exportDTO = new TransferCustomerExportDTO();
            exportDTO.setCustomerId(x.getCustomerId()).setName(x.getName()).setDeptName(x.getDeptName()).setAge(String.valueOf(x.getAge()))
                    .setEducationName(x.getEducationName()).setWorkExperience(x.getWorkExperience()).setApplyType(x.getApplyType())
                    .setApplyKey(x.getApplyKey()).setPositionApplied(x.getPositionApplied()).setMajor(x.getMajor()).setSourceName(x.getSourceName())
                    .setCompanyName(x.getCompanyName());
            this.setGender(x, exportDTO);
            this.setGetWay(x, exportDTO);
            //设置归属人
            exportDTO.setUserName(x.getUserName());
            //设置备注
            exportDTO.setMemo(x.getNote());
            //设置创建时间
            exportDTO.setCreateTime(x.getCreateTime());
            //设置联系方式
            contactBatch.forEach(c -> {
                if (c.getCustomerId().equals(x.getCustomerId())) {
                    this.setContact(exportDTO, c);
                }
            });
            //设置首次归属人
            firstAllotProcessBatch.forEach(p -> {
                if (p.getCustomerId().equals(x.getCustomerId())) {
                    exportDTO.setFirstUserName(p.getUserName());
                }
            });
            //设置跟进次数
            answerCountBatch.forEach(c -> {
                if (c.getCustomerId().equals(x.getCustomerId())) {
                    exportDTO.setFollowCount(c.getAnswerNum());
                }
            });
            //设置跟进状态
            latestFollowBatch.forEach(f -> {
                if (f.getCustomerId().equals(x.getCustomerId())) {
                    this.setFollowStatus(exportDTO, f.getFollowStatus());
                }
            });
            exportList.add(exportDTO);
        });
    }

    /**
     * 公海客户数据
     *
     * @param pageQuery 查询参数对象
     * @return
     */
    @Override
    public List<TransferCustomerEntity> findCommonPage(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, "customerId");
        if (customerId != null && MapUtils.getLong(pageQuery, "customerId").equals(-1L)) {
            return null;
        }
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        List<String> ids = new ArrayList<>();
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);

            allDeptUnderDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
        } else {
            //如果没有刷选部门过滤条件
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysDeptServiceFeign.getUserDeptIdList(getUserId());
            userAllDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
        }
        pageQuery.put("deptIds", StringUtils.listToString(ids));
        this.formatQueryTime(pageQuery);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return baseDao.findCommonPage(pageQuery);
    }

    @Override
    public R receiveTransferCustomer(List<Long> customerId) {
//        Integer opportunity = Integer.valueOf(sysConfigServiceFeign.getConfig(ConfigConstant.BIZ_CUSTOMER_OPPORTUNITY));
//        Future<R> future = receiveExecutor.submit(() -> {
//            List<Long> success = new ArrayList<>();
//            this.doReceive(customerId, opportunity, success, getUserId(), getUserName());
//            return success.isEmpty() ? R.error("很遗憾您没有抢到商机") : R.ok("成功领取商机【" + success.size() + "】条");
//        });
//        try {
//            return future.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error(e.getCause().getMessage());
//        }
        return null;
    }

    private void doReceive(List<Long> customerId, Integer opportunity, List<Long> success, Long userId, String userName) {
        customerId.forEach(c -> {
            this.checkOpportunity(opportunity, userId);
            String version = UUID.randomUUID().toString();
            try {
                if (redisLockUtils.setLock(RedisKeys.Business.receiveLock(String.valueOf(c)), version, 60000L)) {
                    log.info("当前用户：姓名：" + userName + ",用户ID" + userId + ",尝试领取商机【" + c + "】," +
                            "拿到锁KEY=>" + RedisKeys.Business.receiveLock(String.valueOf(c)));
                    Thread.sleep(300L);
                    TransferProcessEntity process = transferProcessService.getProcessByPublicCustomerId(c);
                    if (null == process) {
                        Boolean unlock = redisLockUtils.unLock(RedisKeys.Business.receiveLock(String.valueOf(c)), version);
                        log.error("当前用户：姓名：" + userName + ",用户ID" + userId + ",尝试领取商机【" + c + "】时，该商机已被领取。" +
                                "解锁：【" + unlock + "】,锁KEY=>" + RedisKeys.Business.receiveLock(String.valueOf(c)));
                    } else {
                        //设置流程过期
                        int i = transferProcessService.disableProcessActive(process);
                        if (i > 0) {
                            //新增激活状态，归属私人客户流程
                            TransferProcessEntity processEntity = new TransferProcessEntity().setMemo("公海领取商机").setCustomerId(c)
                                    .setDeptId(process.getDeptId()).setDeptName(process.getDeptName()).setUserId(userId).setUserName(getUserName())
                                    .setCreateUserId(userId).setCreateUserName(userName).setActive(Boolean.FALSE);
                            int save = transferProcessService.save(processEntity);
                            if (save > 0) {
                                //更新客户主表(同步激活状态流程)
                                TransferCustomerEntity transferCustomerEntity = new TransferCustomerEntity().setUpdateUserId(userId).setUpdateUserName(userName)
                                        .setUserId(userId).setUserName(userName).setDeptId(process.getDeptId()).setDeptName(process.getDeptName()).setUpdateTime(new Date())
                                        .setAllotTime(new Date()).setCustomerId(c).setDeptId(process.getDeptId()).setDeptName(process.getDeptName());
                                int update = baseDao.update(transferCustomerEntity);
                                if (update > 0) {
                                    Boolean unlock = redisLockUtils.unLock(RedisKeys.Business.receiveLock(String.valueOf(c)), version);
                                    log.info("当前用户：姓名：" + userName + ",用户ID" + userId + ",成功领取商机【" + c + "】。" +
                                            "解锁：【" + unlock + "】,锁KEY->" + RedisKeys.Business.receiveLock(String.valueOf(c)));
                                    success.add(c);
                                }
                            }
                        } else {
                            log.error("(当前用户：姓名：" + userName + ",用户ID" + userId + ",流程已过期：" + process.getProcessId());
                            Boolean unlock = redisLockUtils.unLock(RedisKeys.Business.receiveLock(String.valueOf(c)), version);
                            log.error("当前用户：姓名：" + userName + ",用户ID" + userId + ",领取商机失败【" + c + "】。" +
                                    "解锁：【" + unlock + "】,锁KEY->" + RedisKeys.Business.receiveLock(String.valueOf(c)));
                        }
                    }
                }
            } catch (Exception e) {
                Boolean unlock = redisLockUtils.unLock(RedisKeys.Business.receiveLock(String.valueOf(c)), version);
                log.info("公海领取商机业务执行捕获异常，进解锁：【" + unlock + "】，异常原因：" + e.getMessage());
                throw new RRException(e.getMessage());
            }
        });
    }

    /**
     * 检查用户商机领取上限
     *
     * @param opportunity 领取上限
     */
    private void checkOpportunity(Integer opportunity, Long userId) {
        Map<String, Object> map = new HashMap<>(1024);
        map.put("userId", userId);
        map.put("beginTime", DateUtils.getBeginTime(DateUtils.format(new Date())));
        map.put("endTime", DateUtils.getEndTime(DateUtils.format(new Date())));
        //查询当天用户拥有商机数量
        int count = transferProcessService.countHasTotal(map);
        if (count >= opportunity) {
            throw new RRException(StatusCode.BIZ_CUSTOMER_RECEIVE_REACH_MAX_LIMIT);
        }
    }


    @Override
    public List<TransferCustomerEntity> findPrivatePage(PageQuery pageQuery) {
        transferCustomerContactService.setCustomerIdByContact(pageQuery);
        Long customerId = MapUtils.getLong(pageQuery, "customerId");
        if (customerId != null && MapUtils.getLong(pageQuery, "customerId").equals(-1L)) {
            return null;
        }
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);
            List<String> ids = new ArrayList<>();
            allDeptUnderDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        this.formatQueryTime(pageQuery);
        //如果没有刷选部门过滤条件
        if (null == deptId) {
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysDeptServiceFeign.getUserDeptIdList(getUserId());
            List<String> ids = new ArrayList<>();
            userAllDeptId.forEach(x -> {
                ids.add(String.valueOf(x));
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return baseDao.findPrivatePage(pageQuery);
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public R returnToCommon(List<Long> customerId) {
        Date date = new Date();
        try {
            customerId.forEach(c -> {
                String error = "用户【" + getUserId() + "】退回公海【" + c + "】:";
                TransferProcessEntity process = transferProcessService.getProcessByCustIdAndUserId(c);
                TransferCustomerEntity customerEntity = baseDao.findOne(c);
                if (null == process) {
                    log.error(error + StatusCode.BIZ_PROCESS_INACTIVE_OR_NOT_PRIVATE.getMsg());
                    return;
                }
                if (!customerEntity.getStatus().equals(Constant.CustomerStatus.POTENTIAL.getValue())) {
                    log.error(error + StatusCode.BIZ_CUSTOMER_NOT_POTENTIAL.getMsg());
                    return;
                }
                //设置流程过期
                int i = transferProcessService.disableProcessActive(process);
                if (i == 0) {
                    log.error(error + StatusCode.BIZ_PROCESS_UPDATE_INACTIVE.getMsg());
                    return;
                }
                //新增激活状态的客户流程
                int save = transferProcessService.save(new TransferProcessEntity().setCreateTime(date).setMemo("私海退回公海操作").setCreateUserName(getUserName())
                        .setCustomerId(c).setDeptId(process.getDeptId()).setDeptName(process.getDeptName()).setActive(Boolean.FALSE).setCreateUserId(getUserId()));
                if (save == 0) {
                    log.error(error + StatusCode.BIZ_PROCESS_SAVE_FAULT.getMsg());
                    return;
                }
                //更新客户主表(同步激活状态流程)
                int update = baseDao.returnToCommon(new TransferCustomerEntity().setAllotTime(date).setUpdateTime(date)
                        .setCustomerId(c).setUpdateUserId(getUserId()).setUpdateUserName(getUserName()));
                if (update == 0) {
                    log.error(error + StatusCode.BIZ_CUSTOMER_UPDATE_FAULT.getMsg());
                }
            });
            return R.ok();
        } catch (Exception e) {
            log.error("《=========私海退回公海异常，原因如下==========》,{}", e.getMessage());
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }
    }

    /**
     * 根据NCid查询客户信息
     *
     * @param ncId NCid
     * @return 客户信息
     * @author xieyuqing
     * @date create 2018年10月8日10:20:06
     */
    @Override
    public TransferCustomerEntity getByNcId(String ncId) {
        return baseDao.getByNcId(ncId);
    }

    /**
     * 根据电话和部门，查询一个客户信息
     */
    @Override
    public TransferCustomerEntity findByPhoneAndDeptId(Long deptId, String phone) {
        TransferCustomerContactEntity contactEntity = new TransferCustomerContactEntity();
        contactEntity.setDetail(phone);
        contactEntity.setType(Constant.CustomerContactType.PHONE.getValue());
        List<TransferCustomerContactEntity> list = transferCustomerContactService.findList(contactEntity);
        for (TransferCustomerContactEntity contact : list) {
            TransferCustomerEntity customerEntity = baseDao.findOne(contact.getCustomerId());
            if (deptId.equals(customerEntity.getDeptId())) {
                return customerEntity;
            }
        }
        return null;
    }
}
