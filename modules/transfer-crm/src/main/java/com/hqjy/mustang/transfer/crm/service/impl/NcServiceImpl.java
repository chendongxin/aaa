package com.hqjy.mustang.transfer.crm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.service.NcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
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
import java.util.Map;


/**
 * @author xieyuqing
 * @ description 请求NC的相关功能的接口
 * @ date create in 2018年9月12日15:06:09
 */
@Service
@Slf4j
public class NcServiceImpl implements NcService {

    /**
     * NC首单保存成功返回code
     */
    private static final int NC_SUCCESS = 1;
    private static final String SUCCESS = "0";
    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String SCHOOL = "SCHOOL";
    private static final String PROVINCE = "PROVINCE";
    private static final String REPEAT_MSG = "已存在相同电话号码的学员";

    /**
     * 商机信息保存到NC
     */
    @Value("${nc.studentFromXN}")
    private String ncUrl;
    /**
     * NC新增跟进记录接口
     */
    @Value("${nc.followUpSaveUrl}")
    private String followUpSaveUrl;
    /**
     * NC商机修改接口
     */
    @Value("${nc.updateStuRecordOrg}")
    private String updateStuRecordOrgUrl;
    /**
     * NC根据校区获取招生老师接口
     */
    @Value("${nc.getTeacherUrl}")
    private String getNcTeacherUrl;
    /**
     * 获取NC自考销售人员接口
     */
    @Value("${nc.getZKSaleManUrl}")
    private String getZKSaleManUrl;
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

    /**
     * 请求nc保存商机接口
     *
     * @param ncBizSaveDTO 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为ncId;请求失败的，则code值为3001，msg值为错误信息
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
            String result = ncRestTemplate.postForEntity(ncUrl, httpEntity, String.class).getBody();
            log.info("请求返回结果：" + result);
            Integer code = JSONObject.parseObject(result).getInteger(CODE);
            String msg = JSONObject.parseObject(result).getString(MSG);
            //首单保存成功 返回：0
            String ncId = getNcId(msg);
            if (code == NC_SUCCESS) {
                log.info("成功保存商机到nc:" + msg);
                return new NcBizSaveResultDTO(0, ncId);
            }
            //重单 返回：0
            if (msg.startsWith(REPEAT_MSG)) {
                log.info("重单，成功保存商机到nc:" + msg);
                return new NcBizSaveResultDTO(0, ncId);
            }
            //处理失败 返回：-1
            log.info("请求nc保存处理返回失败：" + msg);
            return new NcBizSaveResultDTO(-1, msg);
        } catch (Exception e) {
            log.error("保存商机到nc接口异常->{}", e.getMessage());
            //处理异常 返回：1
            return new NcBizSaveResultDTO(1, "NC保存商机接口异常,{}" + e.getMessage());
        }
    }

    /**
     * 请求nc新增跟进记录接口
     *
     * @param ncFollowUpRequest 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为Nc返回数据;
     * 请求失败的，则code值为-1，msg值为错误信息
     */
    @Override
    public NcResponseDTO requestNcFollowUp(NcFollowUpRequestDTO ncFollowUpRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("data", ImmutableList.of(JSON.toJSONString(ncFollowUpRequest)));
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        try {
            log.info("请求nc新增跟进记录接口请求参数：" + JSON.toJSONString(map));
            String result = ncRestTemplate.postForEntity(followUpSaveUrl, httpEntity, String.class).getBody();
            log.info("请求返回结果：" + result);
            if (StringUtils.isNotEmpty(result)) {
                NcResponseDTO ncResponseEntity = JSON.parseObject(result, NcResponseDTO.class);
                String msg = ncResponseEntity.getMsg();
                if (NC_SUCCESS == Integer.valueOf(ncResponseEntity.getCode())) {
                    log.info("nc新增跟进记录接口请求成功" + msg);
                    return new NcResponseDTO("0", msg, result);
                }
                log.error("nc新增跟进记录接口请求返回失败：" + msg);
                return new NcResponseDTO("1", msg, result);
            } else {
                log.error("nc跟进记录请求失败：返回结果为空");
                return new NcResponseDTO("1", "nc跟进记录请求失败：返回结果为空", result);
            }
        } catch (Exception e) {
            log.error("nc跟进记录结果处理异常：" + e.getMessage());
            throw new RRException("nc跟进记录结果处理异常：" + e.getMessage());
        }
    }

    /**
     * 请求NC更新商机接口
     *
     * @param ncBizRequestVo 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功信息；
     * 请求失败的，则code值为-1，msg值为错误信息
     */
    @Override
    public NcResponseDTO requestNcUpdate(NcBizRequestDTO ncBizRequestVo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("id", ImmutableList.of(ncBizRequestVo.getId()));
        map.put("orgcode", ImmutableList.of(ncBizRequestVo.getOrgCode()));
        map.put("zsls", ImmutableList.of(ncBizRequestVo.getZsls()));
        map.put("saleman", ImmutableList.of(ncBizRequestVo.getSaleman()));
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        try {
            String res = ncRestTemplate.postForEntity(updateStuRecordOrgUrl, httpEntity, String.class).getBody();
            log.info("nc修改商机接口请求结果:" + res);
            NcResponseDTO ncResponseEntity = JSONObject.parseObject(res, NcResponseDTO.class);
            if (SUCCESS.equals(ncResponseEntity.getCode())) {
                log.info(StatusCode.NC_REQUEST_UPDATE_SUCCESS.getMsg());
                return new NcResponseDTO("0", StatusCode.NC_REQUEST_UPDATE_SUCCESS.getMsg(), ncResponseEntity.getData());
            }
            log.info(StatusCode.NC_REQUEST_UPDATE_FAULT.getMsg() + ncResponseEntity.getData());
            return new NcResponseDTO("1", StatusCode.NC_REQUEST_UPDATE_FAULT.getMsg(), ncResponseEntity.getData());
        } catch (Exception e) {
            log.error(StatusCode.NC_REQUEST_UPDATE_EXCEPTION.getMsg() + e.getMessage());
            throw new RRException(StatusCode.NC_REQUEST_UPDATE_EXCEPTION);
        }
    }

    /**
     * 请求nc获取招生老师接口
     *
     * @param schoolId 招生校区编码
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：招生老师对象集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    @Override
    public R requestNCGetTeacher(String schoolId) {
        String url = getNcTeacherUrl + "?schoolid=" + schoolId;
        try {
            String result = ncRestTemplate.getForEntity(url, String.class).getBody();
            log.info("获取招生老师接口，返回参数：" + result);
            if (StringUtils.isNotEmpty(result)) {
                int code = JSONObject.parseObject(result).getInteger("code");
                if (NC_SUCCESS == code) {
                    NcTeacherResultDTO ncGetTeacherResultVo = JSONObject.parseObject(result, NcTeacherResultDTO.class);
                    log.info("请求NC获取招生老师接口成功：：" + JSON.toJSONString(ncGetTeacherResultVo));
                    return R.ok(ncGetTeacherResultVo.getMsg());
                }
            }
            log.error("请求NC获取招生老师接口失败，返回参数：" + result);
            return R.error(JSONObject.parseObject(result).getString("msg"));
        } catch (Exception e) {
            return R.error("请求NC获取招生老师接口异常：" + e.getMessage());
        }
    }

    /**
     * 请求NC自考销售人员数据
     *
     * @return @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：NC自考销售人员数据集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    @Override
    public R getSelfTestSaleMan() {
        try {
            String result = ncRestTemplate.getForEntity(getZKSaleManUrl, String.class).getBody();
            int code = JSONObject.parseObject(result).getInteger(CODE);
            String msg = JSONObject.parseObject(result).getString(MSG);
            if (NC_SUCCESS == code) {
                if (msg.contains("{")) {
                    Object o = new Gson().fromJson(msg, new TypeToken<List<NcSelfTestSaleManDTO>>() {
                    }.getType());
                    log.info("成功获取NC自考销售人员");
                    return R.ok(JSONObject.toJSON(o));
                }
            }
            return R.error("获取NC自考销售人员失败：" + msg);
        } catch (Exception e) {
            log.error("获取NC自考销售人员接口异常：" + e.getMessage());
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取所有省份或者省对应的所有校区
     *
     * @return @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：返回数据集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    @Override
    public R getSchoolOrProvince(Map<String, Object> params) {
        StringBuilder url = new StringBuilder();
        try {
            url.append(getProvinceSchoolUrl).append("?state=1");
            String type = MapUtils.getString(params, "type");
            String rootCode = MapUtils.getString(params, "rootCode");
            if (SCHOOL.equals(type)) {
                url.append("&type=SCHOOL");
            }
            if (PROVINCE.equals(type)) {
                url.append("&type=PROVINCE");
            }
            if (StringUtils.isNotBlank(rootCode)) {
                url.append("&rootCode=").append(rootCode);
            }
            String result = ncRestTemplate.getForEntity(url.toString(), String.class).getBody();
            if (StringUtils.isNotBlank(result)) {
                int code = JSONObject.parseObject(result).getInteger(CODE);
                String data = JSONObject.parseObject(result).getString("data");
                if (0 == code) {
                    List<NcProvinceDTO> provinceList = JSON.parseArray(data, NcProvinceDTO.class);
                    return R.ok(provinceList);
                }
            }
            return R.error("获取所有省份或者省对应的所有校区接口异常:返回空");
        } catch (Exception e) {
            log.error("获取所有省份或者省对应的所有校区接口异常：" + e.getMessage());
            return R.error(e.getMessage());
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


