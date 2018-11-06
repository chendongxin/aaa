package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.dao.TransferAllotCustomerContactDao;
import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.service.TransferAllotContactService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.StringUtils;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.base.constant.Constant.CustomerContactType.*;

/**
 * @author : heshuangshuang
 * @date : 2018/6/14
 */
@Service
public class TransferAllotContactServiceImpl extends BaseServiceImpl<TransferAllotCustomerContactDao, TransferAllotCustomerContactEntity, Long> implements TransferAllotContactService {


    /**
     * 保存多个联系方式，
     * 按照顺序进行保存并且查重，只要有一个为重单，那么不会保存后续的联系方式，而是直接返回重单对应的初始客户编号
     * 数据库有唯一主键限制，能够保证一种联系方式的号码是唯一的
     */
    @Override
    public ContactSaveResultDTO saveDetail(TransferAllotCustomerEntity content) {
        ContactSaveResultDTO result = new ContactSaveResultDTO();
        String phone = content.getPhone();
        String landLine = content.getLandLine();
        String weChat = content.getWeChat();
        String qq = content.getQq();
        // 主联系方式
        boolean isPrimary = true;

        //1、电话存在，保存电话，
        if (StringUtils.isNotEmpty(phone)) {
            //因为第一个执行，默认主联系方式
            result = save(result, PHONE.getValue(), phone, true, content);
            if (!result.getContacStatus()) {
                //如果是主联系，而且主联系方式状态为false，说明重单。直接返回结果
                return result;
            }
            //后续设置为普通联系方式
            isPrimary = false;
        }

        //2、座机存在，保存座机
        if (StringUtils.isNotEmpty(landLine)) {
            result = save(result, LAND_LINE.getValue(), landLine, isPrimary, content);
            if (isPrimary && !result.getContacStatus()) {
                //如果是座机主联系，状态为false，说明重单。直接返回结果
                return result;
            }
            isPrimary = false;
        }

        //3、微信存在，保存微信
        if (StringUtils.isNotEmpty(weChat)) {
            result = save(result, WE_CHAT.getValue(), weChat, isPrimary, content);
            if (isPrimary && !result.getContacStatus()) {
                //如果是微信主联系，状态为false，说明重单。直接返回结果
                return result;
            }
            isPrimary = false;
        }

        //4、QQ存在，保存QQ
        if (StringUtils.isNotEmpty(qq)) {
            result = save(result, QQ.getValue(), qq, isPrimary, content);
            if (isPrimary && !result.getContacStatus()) {
                return result;
            }
        }

        if (result.getType() == null) {
            //没有任何联系方式
            return null;
        }
        // 返回联系方式保存结果
        return result;
    }

    /**
     * 保存一种联系方式到数据库
     */
    private ContactSaveResultDTO save(ContactSaveResultDTO result, int type, String detail, boolean isPrimary, TransferAllotCustomerEntity content) {
        TransferAllotCustomerContactEntity contactEntity = new TransferAllotCustomerContactEntity();
        contactEntity.setCustomerId(content.getCustomerId());
        contactEntity.setProId(content.getProId());
        contactEntity.setCreateUserId(content.getCreateUserId());
        contactEntity.setType(type);
        contactEntity.setDetail(detail);
        contactEntity.setCreateUserName(content.getCreateUserName());
        // 如果保存失败，代表重单
        boolean saveStatus = baseDao.save(contactEntity) > 0;
        if (isPrimary) {
            //设置主联系方式
            result.setType(type);
            result.setDetail(detail);
            if (!saveStatus) {
                // 设置主联系方式状态
                result.setContacStatus(false);
                // 查询这个联系方式对应的客户编号，记录下来
                result.setOldCustomerId(baseDao.findOneByTypeAndDetail(type, detail).getCustomerId());
                //只要发现重单，后面的联系方式就不保存了
                return result;
            }
            // 设置联系方式状态,true:首单，false:重单
            result.setContacStatus(true);
        }
        // 不是主联系方式，不管保存结果
        return result;
    }
}
