package com.hqjy.transfer.crm.modules.sys.service.impl;

import com.hqjy.transfer.common.base.base.BaseServiceImpl;
import com.hqjy.transfer.crm.modules.sys.dao.SysClassDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysClassEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/5/23 14:20
 */
@Service
@Slf4j
public class SysClassServiceImpl extends BaseServiceImpl<SysClassDao,SysClassEntity,Long> implements SysClassService {

    @Autowired
    private SysClassDao sysClassDao;


    @Override
    public List<SysClassEntity> getClassComboBox() {
        return sysClassDao.getClassComboBox();
    }

    @Override
    public SysClassEntity findClassByName(String className,Long classId) {
        return sysClassDao.findClassByName(className,classId);
    }
}
