package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.base.validator.ValidatorUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.model.dto.NcBizSaveParamDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerTransferDTO;
import com.hqjy.mustang.transfer.crm.model.entity.*;
import com.hqjy.mustang.transfer.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
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
    private ListOperations<String, Object> listOperations;
//    @Autowired
//    private SysMessageService wsService;

    /**
     * 获取某客户的基本数据
     *
     * @param customerId 客户编号
     * @return 返回结果
     */
    @Override
    public TransferCustomerEntity getCustomerData(Long customerId) {
        TransferCustomerEntity customer = baseDao.findOne(customerId);
        customer.setCustomerDetail(transferCustomerDetailDao.getCustomerDetailByCustomerId(customerId));
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
            List<TransferCustomerContactEntity> list = this.checkContactHasExit(customerDto);
            if (list.size() > 0) {
                //如果存在，将客户添加到重单客户表中
                transferCustomerRepeatService.save(
                        new TransferCustomerRepeatEntity()
                                .setPhone(customerDto.getPhone()).setWeChat(customerDto.getWeChat()).setQq(customerDto.getWeChat())
                                .setLandLine(customerDto.getLandLine()).setDeptId(customerDto.getDeptId()).setCompanyId(customerDto.getCompanyId())
                                .setSourceId(customerDto.getSourceId()).setName(customerDto.getName()).setMemo(customerDto.getNote())
                                .setUserId(customerDto.getFirstUserId()).setCreateUserId(getUserId()).setCreateUserName(getUserName())
                                .setProId(customerDto.getProId())
                );
                return R.error(StatusCode.BIZ_CUSTOMER_HAS_EXIT);
            } else {
                TransferCustomerEntity entity = new TransferCustomerEntity()
                        .setPhone(customerDto.getPhone()).setWeChat(customerDto.getWeChat()).setQq(customerDto.getQq()).setLandLine(customerDto.getLandLine())
                        .setDeptId(customerDto.getDeptId()).setCompanyId(customerDto.getCompanyId()).setSourceId(customerDto.getSourceId())
                        .setProId(customerDto.getProId()).setFirstUserId(customerDto.getFirstUserId()).setName(customerDto.getName())
                        .setCreateUserId(getUserId()).setCreateUserName(getUserName());
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
                //发送客户到redis消息队列，异步回写NCId
                this.sendNcSave(entity, customerDto);
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
                String error = "用户【" + getUserId() + "】转移商机【" + c + "】:";
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
                        .setDeptId(dto.getDeptId()).setUserId(dto.getUserId()).setCustomerId(c).setCreateUserId(getUserId()).setCreateUserName(getUserName());
                int save = transferProcessService.save(newProcess);
                if (save == 0) {
                    return;
                }
                //更新客户主表(同步激活状态流程)
                int update = baseDao.update(new TransferCustomerEntity().setAllotTime(date).setUpdateTime(date)
                        .setCustomerId(c).setDeptId(dto.getDeptId()).setUserId(dto.getUserId()).setUpdateUserId(getUserId()).setUpdateUserName(getUserName()));
                if (update == 0) {
                    return;
                }
                //转移发送消息给用户
//                wsService.sendMessage(dto.getUserId(), "来自" + getUser().getUserName() + "转移的商机", "客户编号：" + c, newProcess);
            });
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }

    }

    /**
     * 更新客户基本信息：此方法只更新customerDetail中的信息
     *
     * @param dto 客户信息
     * @author gmm 2018年9月21日16:23:13
     */
    @Override
    public void updateBaseData(TransferCustomerDTO dto) {

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

    private void sendNcSave(TransferCustomerEntity entity, TransferCustomerDTO dto) {
        NcBizSaveParamDTO ncBizRequestDTO = new NcBizSaveParamDTO();
        ncBizRequestDTO.setCustomerId(entity.getCustomerId());
        ncBizRequestDTO.setTrue_name("自考集训基地");
        ncBizRequestDTO.setUserId(entity.getUserId());
        ncBizRequestDTO.setTel(entity.getPhone());
        ncBizRequestDTO.setLxqq(dto.getQq());
        ncBizRequestDTO.setSaleType(0);
        ncBizRequestDTO.setName(entity.getName());
        ncBizRequestDTO.setCreator(getUserName());
        ncBizRequestDTO.setUserId(entity.getUserId());
        ncBizRequestDTO.setNote(dto.getNote());
        listOperations.leftPush(RedisKeys.Nc.SAVE, JsonUtil.toJson(ncBizRequestDTO));
    }

}
