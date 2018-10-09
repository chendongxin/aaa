package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 推送招转商机至NC数据对象
 *
 * @author : xyq
 * @date : 2018年10月9日15:59:28
 */
@Data
@Accessors(chain = true)
public class NcBizSaveParamDTO {

    /**
     * 野马客户Id，冗余
     */
    private Long customerId;
    /**
     * 学员名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String tel;
    /**
     * qq
     */
    private String qq;

    /**
     * 校区名称：非空，默认，it赛道默认为【广州领教文化传媒科技有限公司】，财经赛道默认为【广州领教文化传媒科技有限公司2】
     */
    private String true_name;
    /**
     * 业务线：牵引力赛道为it,财经赛道默认为hq
     */
    private String ywx;


}
