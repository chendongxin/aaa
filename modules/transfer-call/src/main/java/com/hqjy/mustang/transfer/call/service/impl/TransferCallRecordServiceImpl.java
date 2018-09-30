package com.hqjy.mustang.transfer.call.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.*;
import com.hqjy.mustang.transfer.call.dao.TransferCallRecordDao;
import com.hqjy.mustang.transfer.call.model.dto.TqCallClienIdDTO;
import com.hqjy.mustang.transfer.call.model.dto.TqCallRecordDTO;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import com.hqjy.mustang.transfer.call.service.TransferCallRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/19 11:56
 */
@Service
@Slf4j
public class TransferCallRecordServiceImpl extends BaseServiceImpl<TransferCallRecordDao, TransferCallRecordEntity, Long> implements TransferCallRecordService {
    /**
     * 保存同步的通话记录
     */
    @Override
    public int save(TqCallRecordDTO tqCallRecordDTO) {
        // 解析自定义参数,如果自定义参数没有，则不保存通话记录
        String params = tqCallRecordDTO.getClient_id();
        if (StringUtils.isEmpty(params)) {
            log.error("params 自定义参数获取失败，放弃保存通话记录=>>Fsunique_id:{}", tqCallRecordDTO.getFsunique_id());
            TqCallClienIdDTO tqCallClienIdDTO = new TqCallClienIdDTO();
            tqCallClienIdDTO.setCustomerId(0L);
            tqCallClienIdDTO.setUserId(1L);
            tqCallClienIdDTO.setUserName("test");
            params = Tools.base64Encode(JsonUtil.toJson(tqCallClienIdDTO));
            return 0;
        }
        // base64解码
        TqCallClienIdDTO tqCallClienIdDTO;
        try {
            params = Tools.base64Decoder(params);
            tqCallClienIdDTO = JsonUtil.fromJson(params, TqCallClienIdDTO.class);
        } catch (Exception e) {
            return 0;
        }
        // PO转换
        TransferCallRecordEntity transferCallRecordEntity = new TransferCallRecordEntity()
                // 原生参数
                .setParams(params)
                // 野马客户Id
                .setCustomerId(tqCallClienIdDTO.getCustomerId())
                // 创建人
                .setCreateUserId(tqCallClienIdDTO.getUserId())
                // 创建人名称
                .setCreateUserName(tqCallClienIdDTO.getUserName())
                // 通话唯一编号
                .setUniqueId(tqCallRecordDTO.getFsunique_id())
                // 坐席号
                .setCno(tqCallRecordDTO.getUin())
                // 坐席电话
                .setClientNumber(tqCallRecordDTO.getCalled_id())
                // 客户电话
                .setCustomerNumber(tqCallRecordDTO.getCaller_id())
                // 客户归属地
                .setCustomerArea(tqCallRecordDTO.getCallerArea())
                // 呼叫方式
                .setCallStyle(tqCallRecordDTO.getCall_style() == 3 ? "外呼电话" : "直线呼入")
                // 呼叫途径
                .setPathway(tqCallRecordDTO.getPathway() == 1 ? "呼叫中心" : "工作手机")
                // 是否接通状态
                .setStatus(tqCallRecordDTO.getIs_called_phone() == 1 ? "接通" : "未接通")
                // 电话开始呼叫时间
                .setStartTime(DateUtils.timestampToDate(tqCallRecordDTO.getInsert_time()))
                // 总通话时长
                .setTotalDuration(tqCallRecordDTO.getDuration())
                // 录音URL链接
                .setRecordFile(tqCallRecordDTO.getRecordfile())
                // 响铃时长（秒）；long型
                .setRingTime(tqCallRecordDTO.getRingTime())
                // 提供商记录插入时间
                .setInsertDbTime(tqCallRecordDTO.getInsert_db_time());
        return baseDao.save(transferCallRecordEntity);
    }

    /**
     * 查询数据库中最后一条记录
     */
    @Override
    public TransferCallRecordEntity findLast() {
        return baseDao.findLast();
    }

    @Override
    public List<TransferCallRecordEntity> findPage(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        List<TransferCallRecordEntity> list = baseDao.findPage(pageQuery);
        list.forEach(v -> {
            v.setRingTimeStr(DateUtils.secondToTime(v.getRingTime()));
            v.setTotalDurationStr(DateUtils.secondToTime(v.getTotalDuration()));
        });
        return list;
    }
}
