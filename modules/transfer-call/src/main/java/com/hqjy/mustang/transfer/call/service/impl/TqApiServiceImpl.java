package com.hqjy.mustang.transfer.call.service.impl;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.base.utils.Tools;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.transfer.call.constant.TqConstant;
import com.hqjy.mustang.transfer.call.model.dto.*;
import com.hqjy.mustang.transfer.call.service.TqApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TQ 接口调用服务
 *
 * @author : heshuangshuang
 * @date : 2018/9/18 16:12
 */
@Service
@Slf4j
public class TqApiServiceImpl implements TqApiService {
    @Resource(name = "openRestTemplate")
    private RestTemplate restTemplate;

    /**
     * 管理员账号
     */
    @Value("${tq.adminUin}")
    private String adminUin;
    /**
     * 管理员密码
     */
    @Value("${tq.password}")
    private String password;

    /**
     * 获取外呼token
     */
    @Value("${tq.call.getToken}")
    private String getTokenUrl;

    /**
     * 点击外呼
     */
    @Value("${tq.call.clickCall}")
    private String clickCallUrl;

    private final static String STATE = "state=";
    private final static String TOKEN = "token=";


    /**
     * 服务接口地址
     */
    @Value("${tq.webservice.host}")
    private String host;

    /**
     * 获取服务token
     */
    @Value("${tq.webservice.getAccessToken}")
    private String accessTokenUrl;

    /**
     * 获取通话记录
     */
    @Value("${tq.webservice.phoneRecord}")
    private String phoneRecordUrl;

    /**
     * 获取外呼token
     */
    @Override
    public String getCallToken(SysUserExtendInfo userExtendInfo) {
        if (userExtendInfo == null || userExtendInfo.getTqId() == null || StringUtils.isEmpty(userExtendInfo.getTqPw())) {
            throw new RRException("尚未对您设置TQ工号，所以您暂时不能进行呼出 ");
        }
        // 请求token
        String result = restTemplate.getForEntity(getTokenUrl + "?uin={1}&pw={2}", String.class, userExtendInfo.getTqId(), userExtendInfo.getTqPw()).getBody();
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
    @Override
    public boolean clickCall(int kefuUin, String token, String phone, String params) {
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("kefu_uin", String.valueOf(kefuUin));
        postParameters.add("access_token", token);
        postParameters.add("phone", phone);
        postParameters.add("admin_uin", adminUin);
        postParameters.add("client_id", params);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(postParameters, headers);
        // 拨打电话
        TqCallDTO result = restTemplate.postForObject(clickCallUrl, httpEntity, TqCallDTO.class);
        log.info("外呼结果：phone:{},rsult:{}", phone, result);
        return result != null && 1 == result.getResult_status();
    }

    /**
     * 获取业务接口token
     */
    @Override
    public String getAccessToken() {
        String ctime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String sign = Tools.md5Hex(adminUin + "$sign$" + ctime).toUpperCase();
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("ctime", ctime);
        postParameters.add("sign", sign);
        postParameters.add("admin_uin", adminUin);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(postParameters, headers);
        // 请求token
        TqAccessTokenDTO result = restTemplate.postForObject(accessTokenUrl, httpEntity, TqAccessTokenDTO.class);
        if (result.isSuccess()) {
            log.info("获取tq业务接口token====>{}", result.getAccess_token());
            return result.getAccess_token();
        }
        log.error(" 获取业务接口token失败：result:{}", result);
        // 请求token
        return null;
    }

    /**
     * 分页获取通话记录
     */
    @Override
    public List<TqCallRecordDTO> getPhoneRecord(int pageSize, int pageNum, long startTime, long endTime) {
        String accessToken = getAccessToken();
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(postParameters, headers);
        // 获取通话记录
        String url = phoneRecordUrl + "?access_token={1}&admin_uin={2}&pageSize={3}&pageNum={4}&insert_db_time_start={5}&insert_db_time_end={6}";
        TqResponseDTO result = restTemplate.postForObject(url, httpEntity, TqResponseDTO.class, accessToken, adminUin, pageSize, pageNum, startTime, endTime);
        if (0 == result.getErrorCode()) {
            TqListPageDTO<TqCallRecordDTO> listPageDTO = JsonUtil.parseObject(JsonUtil.toJSONString(result.getData()), new TypeToken<TqListPageDTO<TqCallRecordDTO>>() {
            }.getType());
            return listPageDTO.getList();
        }
        return new ArrayList<>();
    }

}
