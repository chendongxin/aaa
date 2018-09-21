package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerContactDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferCustomerContactServiceImpl extends BaseServiceImpl<TransferCustomerContactDao, TransferCustomerContactEntity, Integer> implements TransferCustomerContactService {

    /**
     * 根据联系方式和详情，查询具体信息
     *
     * @author HSS 2018-06-25
     */
    @Override
    public TransferCustomerContactEntity getByDetail(Integer type, String detail) {
        return baseDao.findOneByDetail(type, detail);
    }

    @Override
    @Transactional
    public int save(TransferCustomerDTO dto) {
        int i = 0;
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            i = baseDao.save(
                    new TransferCustomerContactEntity()
                            .setCustomerId(dto.getCompanyId())
                            .setProId(dto.getProId())
                            .setType(Constant.CustomerContactType.PHONE.getValue())
                            .setDetail(dto.getPhone())
                            .setCreateUserId(getUserId())
                            .setCreateUserName(getUserName())
            );
        }
        if (StringUtils.isNotEmpty(dto.getQq())) {
            i = baseDao.save(
                    new TransferCustomerContactEntity()
                            .setCustomerId(dto.getCompanyId())
                            .setProId(dto.getProId())
                            .setType(Constant.CustomerContactType.QQ.getValue())
                            .setDetail(dto.getQq())
                            .setCreateUserId(getUserId())
                            .setCreateUserName(getUserName())
            );
        }
        if (StringUtils.isNotEmpty(dto.getWeChat())) {
            i = baseDao.save(
                    new TransferCustomerContactEntity()
                            .setCustomerId(dto.getCompanyId())
                            .setProId(dto.getProId())
                            .setType(Constant.CustomerContactType.WE_CHAT.getValue())
                            .setDetail(dto.getWeChat())
                            .setCreateUserId(getUserId())
                            .setCreateUserName(getUserName())
            );
        }
        if (StringUtils.isNotEmpty(dto.getLandLine())) {
            i = baseDao.save(
                    new TransferCustomerContactEntity()
                            .setProId(dto.getProId())
                            .setCustomerId(dto.getCompanyId())
                            .setType(Constant.CustomerContactType.LAND_LINE.getValue())
                            .setDetail(dto.getLandLine())
                            .setCreateUserId(getUserId())
                            .setCreateUserName(getUserName())
            );
        }
        return i;
    }

}
