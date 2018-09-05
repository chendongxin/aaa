package com.hqjy.transfer.allot.service.impl;

import com.hqjy.transfer.allot.dao.AllotContactDao;
import com.hqjy.transfer.allot.dao.AllotCustomerDao;
import com.hqjy.transfer.allot.dao.AllotProcessDao;
import com.hqjy.transfer.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.transfer.allot.model.dto.WeigthRoundDTO;
import com.hqjy.transfer.allot.model.entity.AllotContactEntity;
import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;
import com.hqjy.transfer.allot.model.entity.AllotProcessEntity;
import com.hqjy.transfer.allot.service.BizAllotService;
import com.hqjy.transfer.common.base.constant.ConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.hqjy.transfer.common.base.constant.Constant.CustomerContactType.*;

/**
 * 商机消息分配业务
 *
 * @author : heshuangshuang
 * @date : 2018/6/15 9:34
 */
@Service
@Slf4j
public class BizAllotServiceImpl implements BizAllotService {

    @Autowired
    private WeightRoundAreaImpl weightRoundArea;

    @Autowired
    private WeightRoundDeptImpl weightRoundDept;

    @Autowired
    private WeightRoundUserImpl weightRoundUser;

    @Autowired
    private AllotCustomerDao allotCustomerDao;

    @Autowired
    private AllotContactDao allotContactDao;

    @Autowired
    private AllotProcessDao allotProcessDao;

/*    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysMessageService wsService;*/


    /*
     * 二次咨询本地检测是否二次咨询，
     * 1、本地二次咨询且有归属：提醒人员二次咨询；
     * 2、本地二次咨询无归属，分配流程，提醒二次咨询；
     */
    // 二次咨询商机处理，对已经存在的商机如何处理
    // 二次咨询,联系次数count + 1,进入二次咨询分配流程，选择分配人员，保存商机
    // 发送websocket消息

    /**
     * 首次咨询流程分配
     */
    @Override
    public Long firstBizAllot(ContactSaveResultDTO saveResultDTO, AllotCustomerEntity customer) {
        Long customerId = customer.getCustomerId();
        return bizAllot(customer.isNotAllot(), true, customerId, customer.getAreaId(), customer.getDeptId(), customer.getUserId());
    }

    /**
     * 二次咨询流程分配
     */
    @Override
    public Long repeatBizAllot(ContactSaveResultDTO saveResultDTO, AllotCustomerEntity customer) {
        Long oldCustomerId = saveResultDTO.getOldCustomerId();
        //查询原客户信息
        AllotCustomerEntity oldCustomer = allotCustomerDao.findOne(oldCustomerId);

        //原客户信息不存在,异常数据导致
        if (oldCustomer == null) {
            log.debug("原客户信息不存在,异常数据导致,将新的客户id更新为原客户id");
            // 将新的客户id更新为原客户id
            allotCustomerDao.updateCustomerId(customer.getCustomerId(), oldCustomerId);
            // 新客户成为老客户
            oldCustomer = customer;
        } else {
            log.debug("删除刚才增加的客户信息");
            //删除刚才增加的客户信息
            allotCustomerDao.delete(customer.getCustomerId());
            log.debug("更新原客户联系次数");
            //更新原客户联系次数
            allotCustomerDao.updateConsult(oldCustomerId);
        }

        //有效状态的商机进行分配处理
        if (oldCustomer.getStatus() == 0) {
            // 查询有效状态的流程，判断商机是否有分配人员
            AllotProcessEntity curProcess = allotProcessDao.findOneActiveByCustomerId(oldCustomerId);
            // 没有有效流程记录，问题数据，按照新商机的指定进行分配
            if (curProcess == null) {
                log.debug("没有有效流程记录，问题数据，按照新商机的指定进行分配");
                return bizAllot(customer.isNotAllot(), true, oldCustomerId, customer.getAreaId(), customer.getDeptId(), customer.getUserId());
            }

            //有处理人
            if (curProcess.getUserId() != null) {
                log.debug("有处理人，再次进行分配");
            }
            //再次进行分配
            return bizAllot(customer.isNotAllot(), false, oldCustomerId, null, curProcess.getDeptId(), curProcess.getUserId());
        }

        //无效商机处理，
        if (oldCustomer.getStatus() == 2) {
            // TODO 这里可以处理长时间无效变有效的操作
            return null;
        }
        return null;
    }

    /**
     * 商机分配
     */
    private Long bizAllot(boolean notAllot, boolean isFirst, long customerId, Long areaId, Long deptId, Long userId) {
        if (userId != null && deptId != null) {
            // 指定了人员
            return bizAllotByUserId(notAllot, isFirst, customerId, deptId, userId);
        }
        if (deptId != null) {
            // 指定了部门,递归获取最后一个部门
            return bizAllotByDeptId(notAllot, isFirst, customerId, getDeptByDeptId(deptId));
        }
        // 指定了区域或没有指定任何信息
        return bizAllotByAreaId(notAllot, isFirst, customerId, areaId);
    }

    /**
     * 商机分配，区域ID
     */
    private Long bizAllotByAreaId(boolean notAllot, boolean isFirst, long customerId, Long areaId) {
        // 指定了区域,获取这个区域下的一个部门进行处理
        Long allotDeptId = Optional.ofNullable(areaId).map(this::getDeptByAreaId).orElse(null);
        // 写入流程表
        return bizAllotByDeptId(notAllot, isFirst, customerId, allotDeptId);
    }

    /**
     * 商机分配，部门Id
     */
    private Long bizAllotByDeptId(boolean notAllot, boolean isFirst, long customerId, Long deptId) {
        // 部门不存在，设置为默认部门
        long allotDeptId = Optional.ofNullable(deptId).orElse(getDefaultDeptId());
        // 分配的人员,默认部门不分配人员
        Long allotUserId = Optional.ofNullable(deptId).map(this::getUserByDeptId).orElse(null);
        // 如果不分配
        if (notAllot) {
            return bizNotAllot(isFirst, customerId, allotDeptId);
        }
        // 写入流程表
        return bizAllotByUserId(notAllot, isFirst, customerId, allotDeptId, allotUserId);
    }

    /**
     * 商机分配，人员Id
     */
    private Long bizAllotByUserId(boolean notAllot, boolean isFirst, long customerId, long deptId, Long userId) {
        // 如果不分配
        if (notAllot) {
            return bizNotAllot(isFirst, customerId, deptId);
        }
        //初始化数据，写入流程表
        return saveProcess(isFirst, customerId, deptId, userId);
    }

    /**
     * 不自动分配，但是必须要有部门或区域
     */
    private Long bizNotAllot(boolean isFirst, long customerId, long deptId) {
        //初始化数据，写入流程表
        return saveProcess(isFirst, customerId, deptId, null);
    }

    /**
     * 写入流程表,返回流程Id
     */
    private Long saveProcess(boolean isFirst, long customer, long deptId, Long userId) {
        AllotProcessEntity allotProcess = new AllotProcessEntity();
        allotProcess.setCustomerId(customer);
        // 部门不存在，放进默认部门
     /*   allotProcess.setDeptId(Optional.ofNullable(sysDeptService.findOne(deptId)).map(SysDeptEntity::getDeptId).orElse(getDefaultDeptId()));
        // 人员不存在，不设置分配人员
        if (userId != null) {
            allotProcess.setUserId(Optional.ofNullable(sysUserService.findOneInfo(userId)).map(SysUserEntity::getUserId).orElse(null));
        }*/
        allotProcess.setActive(1);
        // 0 默认为系统
        allotProcess.setCreateId(0);
        // 联系次数默认0
        allotProcess.setFollowCount(0);

        int count;

        //首次咨询
        if (isFirst) {
            allotProcess.setMemo("首次咨询，自动分配");
            // 流程的到期时间，默认为0天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.ALLOT_FIRST_TIMEOUT, 0));
        }

        //二次咨询
        else {
            allotProcess.setMemo("二次咨询，自动分配");
            // 流程的到期时间，默认为3天，写到系统配置里面
            allotProcess.setExpireTime(processTimeout(ConfigConstant.ALLOT_REPEAT_TIMEOUT, 3));
        }

        //保存流程
        count = allotProcessDao.saveActive(allotProcess);

        // 修改其他流程为未激活状态, 因为customerId加了索引，并发时会锁表，单独进性update
        if (count > 0) {
            allotProcessDao.updateNotActivated(customer);
            allotProcessDao.updateActive(allotProcess.getProcessId());
        }
        //发送webSocket消息,需要分别处理，首次和二次
        if (count > 0 && userId != null) {
            sendMessage(isFirst, customer, userId);
        }
        return allotProcess.getProcessId();
    }

    /**
     * 获取默认部门配置
     */
    private Long getDefaultDeptId() {
        /*Long configDefaultDept = sysConfigService.getConfig(ConfigConstant.ALLOT_DEFAULT_DEPTID, Long.class);
        if (configDefaultDept != null) {
            return configDefaultDept;
        }*/
        // 默认设置部门不存在，设置为0
        log.error("系统未设置默认处理部门，设置分配部门为0");
        return 0L;
    }

    /**
     * 处理超时时间
     */
    private Date processTimeout(String code, int defDay) {
        // 超时的天数
       /* int dayCount = Optional.ofNullable(sysConfigService.getConfig(code, Integer.class)).orElse(defDay);
        return Date.from(LocalDateTime.now().plusDays(dayCount).atZone(ZoneId.systemDefault()).toInstant());*/
        return null;

       /* if (LocalTime.of(21, 0, 0).isBefore(LocalTime.now())) {
            return Date.from(LocalDateTime.now().plusDays(dayCount).atZone(ZoneId.systemDefault()).toInstant());
        }
        return DateUtils.parse(LocalDate.now().plusDays(dayCount).toString() + " 21:00:00");*/
    }

    /**
     * 发送webSocket消息
     */
    private void sendMessage(boolean isFirst, long customer, Long userId) {
        AllotContactEntity contactEntity = allotContactDao.findPrimary(customer);
        StringBuilder content = new StringBuilder();
        if (contactEntity != null) {
            if (PHONE.equals(contactEntity.getType())) {
                content.append(PHONE.getLabel());
            } else if (LAND_LINE.equals(contactEntity.getType())) {
                content.append(PHONE.getLabel());
            } else if (WEI_XIN.equals(contactEntity.getType())) {
                content.append(WEI_XIN.getLabel());
            } else if (QQ.equals(contactEntity.getType())) {
                content.append(QQ.getLabel());
            }
            content.append(":").append(contactEntity.getDetail());
        }
        if (isFirst) {
            // 首次咨询
            // wsService.sendMessage(userId, "首次咨询", content.toString(), contactEntity);
        } else {
            // 二次咨询
            // wsService.sendMessage(userId, "二次咨询", content.toString(), contactEntity);
        }
    }

    /**
     * 根据区域编号，根据算法获取一个最终部门进行商机分配
     */
    private Long getDeptByAreaId(long areaId) {
        log.debug("查询指定区域下分配部门");
        // 获取一个区域下配置的处理部门
        WeigthRoundDTO areaDept = weightRoundArea.getOne(areaId);
        //获得一个部门
        if (areaDept != null) {
            // 区域部门存在,递归获取最后一个部门
            return getDeptByDeptId(areaDept.getId());
        }
        return null;
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

    /**
     * 重置List
     */
    @Override
    public void listRest(boolean onlyDept, Long deptId) {
        log.info("重置List deptId:{}", deptId);
        weightRoundDept.listReset(deptId);
        if (!onlyDept) {
            weightRoundUser.listReset(deptId);
        }
    }
}
