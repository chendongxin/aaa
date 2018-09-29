package com.hqjy.mustang.transfer.sms.fegin.fallback;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.transfer.sms.fegin.SysUserExtendApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class SysUserExtendServiceFallbackImpl implements SysUserExtendApiService {

    /**
     * 根据用户Id查询 用户扩展信息
     */
    @Override
    public SysUserExtendInfo findByUserId(Long userId) {
        log.error("调用{} 异常:{}", "根据用户Id查询 用户扩展信息", userId);
        throw new RRException("查询用户tq失败时无法拨号");
    }
}
