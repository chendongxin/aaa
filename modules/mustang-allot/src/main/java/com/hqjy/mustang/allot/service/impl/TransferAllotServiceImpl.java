package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.dao.TransferAllotCustomerDao;
import com.hqjy.mustang.allot.dao.TransferAllotCustomerDetailDao;
import com.hqjy.mustang.allot.dao.TransferAllotCustomerRepeatDao;
import com.hqjy.mustang.allot.dao.TransferAllotProcessDao;
import com.hqjy.mustang.allot.exception.MqException;
import com.hqjy.mustang.allot.feign.SysConfigApiService;
import com.hqjy.mustang.allot.feign.SysDeptApiService;
import com.hqjy.mustang.allot.feign.SysUserApiService;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.dto.WeigthRoundDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerRepeatEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotProcessEntity;
import com.hqjy.mustang.allot.service.AbstractAllotService;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.hqjy.mustang.allot.constant.AllotCode.BIZ_INVALID;
import static com.hqjy.mustang.common.base.utils.JsonUtil.toObject;

/**
 * 商机消息分配业务
 *
 * @author : heshuangshuang
 * @date : 2018/6/15 9:34
 */
@Slf4j
@Service("transferAllotService")
public class TransferAllotServiceImpl implements AbstractAllotService<TransferAllotCustomerEntity> {

    @Autowired
    private WeightRoundDeptImpl weightRoundDept;

    @Autowired
    private WeightRoundUserImpl weightRoundUser;

    @Autowired
    private TransferAllotCustomerDao allotCustomerDao;

    @Autowired
    private TransferAllotProcessDao transferAllotProcessDao;

    @Autowired
    private SysConfigApiService sysConfigApiService;

    @Autowired
    private TransferAllotCustomerRepeatDao allotCustRepeatDao;

    @Autowired
    private TransferAllotCustomerDetailDao transferAllotCustomerDetailDao;

    @Autowired
    private SysUserApiService sysUserApiService;
    @Autowired
    private SysDeptApiService sysDeptApiService;

    /*
     * 二次咨询本地检测是否二次咨询，
     * 1、本地二次咨询且有归属：提醒人员二次咨询；
     * 2、本地二次咨询无归属，分配流程，提醒二次咨询；
     */
    // 二次咨询商机处理，对已经存在的商机如何处理
    // 二次咨询,联系次数count + 1,进入二次咨询分配流程，选择分配人员，保存商机
    // 发送websocket消息

    /**
     * 首次咨询流程分配，部分不存在会指定到默认部门
     * 暂时不做部门不存在，人员不存在的检测
     */
    @Override
    public TransferAllotCustomerEntity allot(ContactSaveResultDTO saveResultDTO, TransferAllotCustomerEntity customer) {
        if (saveResultDTO.getContacStatus()) {
            log.debug("首次咨询流程分配");
            //首次咨询流程分配
            return firstAllot(customer);
        }
        //二次咨询处理
        else {
            log.debug("二次咨询流程分配");
            //二次咨询流程分配
            return repeatAllot(saveResultDTO, customer);
        }
    }

    /**
     * 首次咨询流程分配
     */
    private TransferAllotCustomerEntity firstAllot(TransferAllotCustomerEntity customer) {
        // 保存简历详情
        transferAllotCustomerDetailDao.saveCustomer(customer);
        return bizAllot(true, customer);
    }

    /**
     * 二次咨询流程分配，招转需要更新失效时间，和创建时间
     */
    private TransferAllotCustomerEntity repeatAllot(ContactSaveResultDTO saveResultDTO, TransferAllotCustomerEntity customer) {
        TransferAllotCustomerEntity newCustomer = PojoConvertUtil.convert(customer, TransferAllotCustomerEntity.class);
        Long oldCustomerId = saveResultDTO.getOldCustomerId();
        //查询原客户信息
        TransferAllotCustomerEntity oldCustomer = allotCustomerDao.findOne(oldCustomerId);

        //原客户信息不存在,异常数据导致
        if (oldCustomer == null) {
            log.debug("原客户信息不存在,异常数据导致,将新的客户id更新为原客户id");
            // 将新的客户id更新为原客户id
            allotCustomerDao.updateCustomerId(customer.getCustomerId(), oldCustomerId);
        } else {
            newCustomer = oldCustomer;
            log.debug("删除刚才增加的客户信息");
            //删除刚才增加的客户信息
            allotCustomerDao.delete(customer.getCustomerId());
            //log.debug("更新原客户联系次数");
            //更新原客户联系次数
            // allotCustomerDao.updateConsult(oldCustomerId);
        }

        //无效商机处理，TODO 同个赛道。如果简历已经标记为失败状态为无效失败，而且时间在15天内（包括15天）则过滤掉这部分简历。
        if (newCustomer.getStatus() == -2) {
            // 判断无效失败时间长度
            boolean isBefore = newCustomer.getAllotTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now().plus(15, ChronoUnit.DAYS));
            if (isBefore) {
                // 抛出异常，不处理
                throw new MqException(BIZ_INVALID);
            }
        }

        // 有效状态的商机进行分配处理
        if (newCustomer.getStatus() == 0) {
            // 查询有效状态的流程，判断商机是否有分配人员
            TransferAllotProcessEntity curProcess = transferAllotProcessDao.findOneActiveByCustomerId(oldCustomerId);

            // 没有有效流程记录，问题数据，按照新商机的指定进行分配
            if (curProcess == null) {
                log.debug("没有有效流程记录，问题数据，按照新商机的指定进行分配");
                newCustomer.setDeptId(customer.getDeptId());
                newCustomer.setUserId(customer.getUserId());
                bizAllot(false, newCustomer);
            } else {
                // 商机仍然存在电销的私海，此时重复导入同一条商机，此时导入不成功（因为仍在电销私海处在15天保护期，每次跟新会刷新保护期时间）
                // 商机留回公海，此时重复导入同一条商机，更新商机的创建的时间，其他电销领取后当作新商机处理（外呼记录和历史记录隐藏
                log.debug("商机仍然存在电销的私海，更新商机的创建时间，分配时间");
                newCustomer.setDeptId(curProcess.getDeptId());
                newCustomer.setUserId(curProcess.getUserId());
                bizAllot(false, newCustomer);
            }
        }
        // 记录重单
        repeatSave(saveResultDTO, customer, newCustomer);
        return newCustomer;
    }

    private void repeatSave(ContactSaveResultDTO resultDTO, TransferAllotCustomerEntity newCustomer, TransferAllotCustomerEntity customer) {
        // 二次咨询处理，保存重单商机
        if (!resultDTO.getContacStatus()) {
            log.debug("记录到客户二次咨询表");
            //记录到客户二次咨询表
            TransferAllotCustomerRepeatEntity custRepeatEntity = PojoConvertUtil.convert(newCustomer, TransferAllotCustomerRepeatEntity.class);
            custRepeatEntity.setCustomerId(resultDTO.getOldCustomerId());
            custRepeatEntity.setDeptId(customer.getDeptId());
            custRepeatEntity.setDeptName(customer.getDeptName());
            custRepeatEntity.setUserId(customer.getUserId());
            custRepeatEntity.setUserName(customer.getUserName());
            allotCustRepeatDao.save(custRepeatEntity);
        }
    }

    /**
     * 商机分配
     */
    private TransferAllotCustomerEntity bizAllot(boolean isFirst, TransferAllotCustomerEntity customer) {

        // 递归获取子部门，如果部门不存在，设置为默认部门,所以部门一定会存在
        customer.setDeptId(getDeptByDeptId(Optional.ofNullable(customer.getDeptId()).orElseGet(this::getDefaultDeptId)));
        customer.setDeptName(Optional.ofNullable(sysDeptApiService.findOne(customer.getDeptId())).map(SysDeptInfo::getDeptName).orElse("未知部门"));

        // 如果不分配,直接保存到部门，不指定人员
        if (customer.isNotAllot()) {
            customer.setUserId(null);
            return customer;
        }

        // 未指定部门或者未指定人，使用分配算法
        if (customer.getUserId() == null || customer.getDeptId() == null) {
            customer.setUserId(Optional.ofNullable(customer.getDeptId()).map(this::getUserByDeptId).orElse(null));
        }

        // 冗余分配人id和姓名
        String userName = Optional.ofNullable(customer.getUserId())
                .map(uid -> Optional.ofNullable(sysUserApiService.findOne(uid)).map(SysUserInfo::getUserName).orElse(null))
                .orElse(null);
        // 用户不存在
        if (StringUtils.isEmpty(userName)) {
            customer.setUserId(null);
            return customer;
        }
        // 用户存在
        customer.setUserName(userName);
        customer.setLastUserId(customer.getUserId());
        customer.setLastUserName(customer.getUserName());
        if (isFirst) {
            customer.setFirstUserId(customer.getUserId());
            customer.setFirstUserName(customer.getUserName());
        }
        return customer;
    }


    /**
     * 获取默认部门配置
     */

    private Long getDefaultDeptId() {
        Long configDefaultDept = Optional.ofNullable(sysConfigApiService.getConfig(ConfigConstant.TRANSFER_ALLOT_DEFAULT_DEPTID))
                .map(c -> toObject(c, Long.class))
                .orElse(null);
        if (configDefaultDept != null) {
            return configDefaultDept;
        }
        // 默认设置部门不存在，设置为0
        log.error("系统未设置默认处理部门，设置分配部门为0");
        return 0L;
    }

    /**
     * 根据部门编号，算法获取一个最终部门进行商机分配
     */
    private Long getDeptByDeptId(long deptId) {
        log.debug("递归查询部门：{}", deptId);
        WeigthRoundDTO dept = weightRoundDept.getOne(deptId);
        if (dept == null) {
            log.debug("找到最终部门：{}", deptId);
            //子部门不存在，到达最后部门
            return deptId;
        }
        return this.getDeptByDeptId(dept.getId());
    }

    /**
     * 根据最终部门编号，算法获取一个人员进行商机分配
     */
    private Long getUserByDeptId(long deptId) {
        log.debug("查询部门下人员：{}", deptId);
        //通过最后一个部门ID和当起班次ID获取一个人员
        WeigthRoundDTO user = weightRoundUser.getOne(deptId);
        if (user != null) {
            // 找到分配人员
            log.debug("找到分配人员:{}", user);
            return user.getId();
        }
        log.debug("没有找到分配人员");
        return null;
    }

    @Override
    public void restUserList(Long deptId) {
        log.info("重置restUser deptId:{}", deptId);
        weightRoundDept.listReset(deptId);
        weightRoundUser.listReset(deptId);
    }

    @Override
    public void restDeptList(Long deptId) {
        log.info("重置restDept deptId:{}", deptId);
        weightRoundDept.listReset(deptId);
    }

}
