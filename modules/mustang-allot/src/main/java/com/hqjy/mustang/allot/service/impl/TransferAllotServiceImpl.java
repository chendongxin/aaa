package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.dao.TransferAllotCustomerDao;
import com.hqjy.mustang.allot.dao.TransferAllotProcessDao;
import com.hqjy.mustang.allot.feign.SysConfigApiService;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.dto.WeigthRoundDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotProcessEntity;
import com.hqjy.mustang.allot.service.AbstractAllotService;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public void allot(ContactSaveResultDTO saveResultDTO, TransferAllotCustomerEntity customer) {
        if (saveResultDTO.getContacStatus()) {
            log.debug("首次咨询流程分配");
            //首次咨询流程分配
            firstAllot(customer);
        }
        //二次咨询处理
        else {
            log.debug("二次咨询流程分配");
            //二次咨询流程分配
            repeatAllot(saveResultDTO, customer);
        }
    }

    /**
     * 首次咨询流程分配
     */
    private void firstAllot(TransferAllotCustomerEntity customer) {
        bizAllot(customer);
    }

    /**
     * 二次咨询流程分配，招转需要更新失效时间，和创建时间
     */
    private void repeatAllot(ContactSaveResultDTO saveResultDTO, TransferAllotCustomerEntity customer) {
        Long oldCustomerId = saveResultDTO.getOldCustomerId();
        //查询原客户信息
        TransferAllotCustomerEntity oldCustomer = allotCustomerDao.findOne(oldCustomerId);

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
            //log.debug("更新原客户联系次数");
            //更新原客户联系次数
            // allotCustomerDao.updateConsult(oldCustomerId);
        }

        // 有效状态的商机进行分配处理
        if (oldCustomer.getStatus() == 0) {
            // 查询有效状态的流程，判断商机是否有分配人员
            TransferAllotProcessEntity curProcess = transferAllotProcessDao.findOneActiveByCustomerId(oldCustomerId);

            // 没有有效流程记录，问题数据，按照新商机的指定进行分配
            if (curProcess == null) {
                log.debug("没有有效流程记录，问题数据，按照新商机的指定进行分配");
                oldCustomer.setDeptId(customer.getDeptId());
                oldCustomer.setUserId(customer.getUserId());
                bizAllot(oldCustomer);
                return;
            }

            // 商机仍然存在电销的私海，此时重复导入同一条商机，此时导入不成功（因为仍在电销私海处在15天保护期，每次跟新会刷新保护期时间）
            // 商机留回公海，此时重复导入同一条商机，更新商机的创建的时间，其他电销领取后当作新商机处理（外呼记录和历史记录隐藏
            log.debug("商机仍然存在电销的私海，更新商机的创建时间，分配时间");
            oldCustomer.setDeptId(curProcess.getDeptId());
            oldCustomer.setUserId(curProcess.getUserId());
            bizAllot(oldCustomer);
            return;
        }

        //无效商机处理，
        if (oldCustomer.getStatus() == 2) {
            // TODO 这里可以处理长时间无效变有效的操作
        }
    }

    /**
     * 商机分配
     */
    private void bizAllot(TransferAllotCustomerEntity customer) {
        // 递归获取子部门，如果部门不存在，设置为默认部门,所以部门一定会存在
        customer.setDeptId(Optional.of(customer.getDeptId()).map(this::getDeptByDeptId).orElse(getDefaultDeptId()));

        // 如果不分配,直接保存到部门，不指定人员
        if (customer.isNotAllot()) {
            customer.setUserId(null);
            return;
        }

        // 指定了用户,不处理
        if (customer.getUserId() != null) {
            return;
        }

        // 其他情况，未指定部门或者未指定人，使用分配算法
        customer.setUserId(Optional.ofNullable(customer.getDeptId()).map(this::getUserByDeptId).orElse(null));
    }


    /**
     * 获取默认部门配置
     */

    private Long getDefaultDeptId() {
        Long configDefaultDept = Optional.ofNullable(sysConfigApiService.getConfig(ConfigConstant.ALLOT_DEFAULT_DEPTID)).map(c -> toObject(c, Long.class)).orElse(null);
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
