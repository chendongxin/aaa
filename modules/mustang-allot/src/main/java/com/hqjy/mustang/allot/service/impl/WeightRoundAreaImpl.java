package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.allot.dao.WeigthRoundDao;
import com.hqjy.mustang.allot.service.AbstractWeightRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据部门权重，获取一个区域下的部门
 *
 * @author : heshuangshuang
 * @date : 2018/6/7 15:39
 */
@Service
public class WeightRoundAreaImpl extends AbstractWeightRound {
    @Autowired
    private WeigthRoundDao weigthRoundDao;

    @Override
    public String keyPrefix() {
        return RedisKeys.Allot.key("area");
    }

    @Override
    public List<Object> initData(Long id) {
        return weigthRoundDao.getDeptListByAreaId(id).stream().map(JsonUtil::toJson).collect(Collectors.toList());
    }
}
