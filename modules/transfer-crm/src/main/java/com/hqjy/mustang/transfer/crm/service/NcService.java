package com.hqjy.mustang.transfer.crm.service;


import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.*;


import java.util.Map;


/**
 * @author xieyuqing
 * @ description 请求NC的相关功能的接口
 * @ date create in 2018/5/29 17:16
 */
public interface NcService {


    /**
     * 请求nc保存商机接口
     *
     * @param ncBizSaveDTO 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为ncId;请求失败的，则code值为3001，msg值为错误信息
     */
    NcBizSaveResultDTO requestNcSave(NcBizSaveParamDTO ncBizSaveDTO);

    /**
     * 请求nc新增跟进记录接口
     *
     * @param ncFollowUpRequest 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为Nc返回数据;
     * 请求失败的，则code值为-1，msg值为错误信息
     */
    NcResponseDTO requestNcFollowUp(NcFollowUpRequestDTO ncFollowUpRequest);

    /**
     * 请求NC更新商机接口
     *
     * @param ncBizRequestVo 请求参数
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功信息；
     * 请求失败的，则code值为-1，msg值为错误信息
     */
    NcResponseDTO requestNcUpdate(NcBizRequestDTO ncBizRequestVo);

    /**
     * 请求nc获取招生老师接口
     *
     * @param schoolId 招生校区编码
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：招生老师对象集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    R requestNCGetTeacher(String schoolId);

    /**
     * 请求NC自考销售人员数据
     *
     * @return @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：NC自考销售人员数据集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    R getSelfTestSaleMan();

    /**
     * 获取所有省份或者省对应的所有校区
     *
     * @return @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：返回数据集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    R getSchoolOrProvince(Map<String, Object> params);

    /**
     * 获取ncId
     *
     * @param msg nc返回的字符串
     * @return 返回字符串ncId
     */
    String getNcId(String msg);

}


