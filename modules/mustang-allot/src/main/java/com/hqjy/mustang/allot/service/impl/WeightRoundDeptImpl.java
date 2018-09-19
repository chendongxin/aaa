package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.allot.dao.WeigthRoundDao;
import com.hqjy.mustang.allot.service.AbstractWeightRound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据部门权重，获取部门的一个子部门
 *
 * @author : heshuangshuang
 * @date : 2018/6/7 15:39
 */
@Service
@Slf4j
public class WeightRoundDeptImpl extends AbstractWeightRound {
    @Autowired
    private WeigthRoundDao weigthRoundDao;

    @Override
    public String keyPrefix() {
        return RedisKeys.Allot.key("dept");
    }

    @Override
    public List<Object> initData(Long id) {
        return weigthRoundDao.getDeptList(id).stream().map(JsonUtil::toJson).collect(Collectors.toList());
    }

}
