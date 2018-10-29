package com.hqjy.mustang.transfer.crm.service;


import com.hqjy.mustang.transfer.crm.model.dto.*;


import java.util.List;


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
     * 请求nc获取招生老师接口
     *
     * @param schoolId 招生校区编码
     * @return 返回实体：如果请求保存成功 code值为0，msg值为成功，result值为：招生老师对象集合；
     * 请求失败的，则code值为-1，msg值为错误信息，result值为null
     */
    List<NcTeacherDTO> requestNCGetTeacher(String schoolId);


    /**
     * 根据校区名字获取NC校区数据
     * @param name 校区名称
     * @return 返回校区数据
     */
    NcSchoolDTO getSchoolByName(String name);

    /**
     * 获取ncId
     *
     * @param msg nc返回的字符串
     * @return 返回字符串ncId
     */
    String getNcId(String msg);

}


