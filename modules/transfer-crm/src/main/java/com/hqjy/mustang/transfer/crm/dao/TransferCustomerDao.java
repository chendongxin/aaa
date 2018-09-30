package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferAnswerCountEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_customer 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerDao extends BaseDao<TransferCustomerEntity, Long> {

    /**
     * (批量)统计跟进次数（拨打电话，接通次数）
     *
     * @param customerIds 客户ID
     * @return 返回跟进次数
     * @author XYQ 2018年8月20日09:49:38
     */
    List<TransferAnswerCountEntity> getAnswerCountBatch(@Param("customerIds") String customerIds);

    /**
     * 客户列表统计导出数据条数
     * @param pageQuery 查询条件
     * @return 条数
     * @author xyq 2018年8月23日16:33:37
     */
    int countExportCustomer(PageQuery pageQuery);

    /**
     * 根据客户ID查询一个客户
     */
    TransferCustomerEntity getCustomerByCustomerId(Long customerId);

    /**
     * 公海客户
     *
     * @param pageQuery 查询条件
     * @return 私海客户数据
     */
    List<TransferCustomerEntity> findCommonPage(PageQuery pageQuery);

//    /**
//     * 私海客户
//     *
//     * @param pageQuery 查询条件
//     * @return 私海客户数据
//     */
//    List<TransferCustomerEntity> findPrivatePage(PageQuery pageQuery);

}