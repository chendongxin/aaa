package com.hqjy.mustang.transfer.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.service.NcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author xieyuqing
 * @ description 请求NC的相关功能的接口
 * @ date create in 2018年9月12日15:06:09
 */
@Service
@Slf4j
public class NcServiceImpl implements NcService {

    private static final int SUCCESS = 1;
    private static final int FAULT = 0;
    private static final int EXCEPTION = -1;
    private static final String CODE = "code";
    private static final String MSG = "msg";

    /**
     * 商机信息保存到NC
     */
    @Value("${nc.pushCustomerToNCUrl}")
    private String pushCustomerToNCUrl;

    /**
     * NC根据校区获取招生老师接口
     */
    @Value("${nc.getTeacherUrl}")
    private String getNcTeacherUrl;

    /**
     * 获取NC省份或校区接口
     */
    @Value("${nc.getProvinceSchoolUrl}")
    private String getProvinceSchoolUrl;


    @Qualifier("ncRestTemplate")
    private RestTemplate ncRestTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate ncRestTemplate) {
        this.ncRestTemplate = ncRestTemplate;
    }

    private RedisUtils redisUtils;

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 请求nc保存商机接口
     * NC:推送成功:{code:1;msg: 接收成功，学员NC ID值【pk】},推送失败:{code:0;msg:电话或QQ为空}
     *
     * @param ncBizSaveDTO 请求参数
     * @return 返回实体：如果请求保存成功 code值为1，msg值为ncId;请求失败的，则code值为0，msg值为错误信息
     */
    @Override
    public NcBizSaveResultDTO requestNcSave(NcBizSaveParamDTO ncBizSaveDTO) {
        log.info("请求NC保存商机参数：{}", JsonUtil.toJSONString(ncBizSaveDTO));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("data", ImmutableList.of(JSON.toJSONString(ncBizSaveDTO)));
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        try {
            String result = ncRestTemplate.postForEntity(pushCustomerToNCUrl, httpEntity, String.class).getBody();
            log.info("请求返回结果：" + result);
            Integer code = JSONObject.parseObject(result).getInteger(CODE);
            String msg = JSONObject.parseObject(result).getString(MSG);
            String ncId = getNcId(msg);
            if (code == SUCCESS) {
                log.info("成功推送商机到nc:" + msg);
                return new NcBizSaveResultDTO(SUCCESS, ncId);
            }
            log.info("推送商机到NC失败：" + msg);
            return new NcBizSaveResultDTO(FAULT, msg);
        } catch (Exception e) {
            log.error("保存商机到nc接口异常->{}", e.getMessage());
            //处理异常 返回：1
            return new NcBizSaveResultDTO(EXCEPTION, "NC保存商机接口异常,{}" + e.getMessage());
        }
    }


    /**
     * 请求nc获取招生老师接口
     *
     * @param name 系统部门
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：招生老师对象集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    @Override
    public List<NcTeacherDTO> requestNCGetTeacher(String name) {
        NcSchoolDTO school = this.getSchoolByName(name);
        if (StringUtils.isNotBlank(redisUtils.get(RedisKeys.NC.schoolTeacherKey(name)))) {
            return JSONArray.parseArray(redisUtils.get(RedisKeys.NC.schoolTeacherKey(name)), NcTeacherDTO.class);
        } else {
            try {
                String url = getNcTeacherUrl + "?schoolid=" + school.getNcId();
                String result = ncRestTemplate.getForEntity(url, String.class).getBody();
                log.info("获取招生老师接口，返回参数：" + result);
                if (StringUtils.isNotEmpty(result)) {
                    int code = JSONObject.parseObject(result).getInteger("code");
                    if (SUCCESS == code) {
                        NcTeacherResultDTO ncGetTeacherResultVo = JSONObject.parseObject(result, NcTeacherResultDTO.class);
                        log.info("请求NC获取招生老师接口成功：：" + JSON.toJSONString(ncGetTeacherResultVo));
                        redisUtils.set(RedisKeys.NC.schoolTeacherKey(name), ncGetTeacherResultVo.getMsg(), 3600L);
                        return ncGetTeacherResultVo.getMsg();
                    }
                }
                log.error("请求NC获取校区老师失败，返回参数：" + result);
                throw new RRException(JSONObject.parseObject(result).getString("msg"));
            } catch (Exception e) {
                throw new RRException("请求NC获取校区老师接口异常：" + e.getMessage());
            }
        }
    }


    @Override
    public NcSchoolDTO getSchoolByName(String name) {
        if (StringUtils.isNotBlank(redisUtils.get(RedisKeys.NC.deptNameKey(name)))) {
            return JSON.parseObject(redisUtils.get(RedisKeys.NC.deptNameKey(name)), NcSchoolDTO.class);
        } else {
            StringBuilder url = new StringBuilder();
            try {
                url.append(getProvinceSchoolUrl).append("?state=1").append("&name=").append(name);
                String result = ncRestTemplate.getForEntity(url.toString(), String.class).getBody();
                if (StringUtils.isNotBlank(result)) {
                    int code = JSONObject.parseObject(result).getInteger(CODE);
                    String data = JSONObject.parseObject(result).getString("data");
                    if (0 == code) {
                        List<NcSchoolDTO> ncSchool = JSON.parseArray(data, NcSchoolDTO.class);
                        redisUtils.set(RedisKeys.NC.deptNameKey(name), ncSchool.get(0), 3600L);
                        return ncSchool.get(0);
                    }
                }
                throw new RRException("获取NC校区接口异常:该部门名称找不到对应的NC校区");
            } catch (Exception e) {
                log.error("系统部门匹配NC校区接口异常：" + e.getMessage());
                throw new RRException("系统部门匹配NC校区接口异常：" + e.getMessage());
            }
        }
    }

    /**
     * 获取ncId
     *
     * @param msg nc返回的字符串
     * @return 返回字符串ncId
     */
    @Override
    public String getNcId(String msg) {
        int start = msg.indexOf("【");
        if (start < 0) {
            return "";
        }
        int end = msg.indexOf("】");
        return msg.substring(start + 1, end);
    }

}


