package com.hqjy.mustang.transfer.call.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.call.constant.TqConstant;
import com.hqjy.mustang.transfer.call.fegin.SysUserExtendApiService;
import com.hqjy.mustang.transfer.call.model.dto.TqCallResult;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:45
 */
@Service
@Slf4j
public class TqCallServiceImpl implements TqCallService {

    @Autowired
    private SysUserExtendApiService sysUserExtendApiService;

    @Autowired
    private RestTemplate restTemplate;

    private final static String TQ_TOKEN_URL = "http://passport.mobile.tq.cn:81/pulse?uin={1}&pw={2}";
    private final static String TQ_CALLOUT_URL = "http://vip.mobile.tq.cn/vip/workMobile/clickCall";
    private final static String STATE = "state=";
    private final static String TOKEN = "token=";
    private final static int ADMIN_UIN = 9773112;

    /**
     * TQ 外呼
     */
    @Override
    public boolean callOut(String phone) {
        // 查询用户绑定的tq帐号信息
        SysUserExtendInfo userExtendInfo = sysUserExtendApiService.findByUserId(ShiroUtils.getUserId());
        // 请求token
        String token = getTqToken(userExtendInfo);
        return doCall(userExtendInfo.getTqId(), ADMIN_UIN, token, phone);
    }

    /**
     * 请求token
     */
    private String getTqToken(SysUserExtendInfo userExtendInfo) {
        if (userExtendInfo == null || userExtendInfo.getTqId() == null || StringUtils.isEmpty(userExtendInfo.getTqPw())) {
            throw new RRException("尚未对您设置TQ工号，所以您暂时不能进行呼出");
        }
        // 请求token
        String result = restTemplate.getForEntity(TQ_TOKEN_URL, String.class, userExtendInfo.getTqId(), userExtendInfo.getTqPw()).getBody();
        if (StringUtils.isNotEmpty(result)) {
            // 请求token异常
            if (result.startsWith(STATE)) {
                throw new RRException("qt请求token失败：" + TqConstant.state(Integer.valueOf(result.replaceAll(STATE, ""))));
            }
            // 获取token成功
            if (result.startsWith(TOKEN)) {
                String token = result.replace(TOKEN, "");
                log.info("获取TQ token 成功:{}", token);
                return token;
            }
        }
        throw new RRException("未请求到 qt result:" + result);
    }

    /**
     * 执行拨打电话
     */
    private boolean doCall(int kefuUin, int adminUin, String token, String phone) {
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("kefu_uin", String.valueOf(kefuUin));
        postParameters.add("access_token", token);
        postParameters.add("phone", phone);
        postParameters.add("admin_uin", String.valueOf(adminUin));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(postParameters, headers);
        // 请求token
        TqCallResult result = restTemplate.postForObject(TQ_CALLOUT_URL, httpEntity, TqCallResult.class);
        log.info("外呼结果：phone:{},rsult:{}", phone, result);
        return result != null && 1 == result.getResult_status();
    }
}
