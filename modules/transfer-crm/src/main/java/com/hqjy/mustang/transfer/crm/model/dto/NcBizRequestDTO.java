package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xieyuqing
 * @ description 请求nc接口的参数
 * @ date create in 2018/5/29 17:26
 */
@Data
@Accessors(chain = true)
public class NcBizRequestDTO {

    /**
     * ncId（请求nc商机更新接口必传参数）
     */
    private String id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 销售人员编码
     */
    private String saleman;

    /**
     * 招生老师编码
     */
    private String zsls;

    /**
     * 校区编码（请求nc商机更新接口必传参数）
     */
    private String orgCode;

    /**
     * 客户联系电话（请求nc商机保存接口必传参数,tel或者qq不能为空）
     */
    private String tel;

    /**
     * 客户联系方式QQ(请求nc商机保存接口必传参数，tel或者qq不能为空)
     */
    private String qq;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 自考为指定校区，即自考集训基地(请求nc商机保存接口必传参数)
     */
    private String true_name;

}
