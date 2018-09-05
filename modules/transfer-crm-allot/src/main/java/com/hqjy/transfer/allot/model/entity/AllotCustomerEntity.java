package com.hqjy.transfer.allot.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商机分配用户接受消息队列消息的对象
 * 包含分配属性，商机信息，和后续处理的商机属性
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/22 10:55
 */
@Data
public class AllotCustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否不分配， false分配，true 不分配
     */
    private boolean notAllot;

    /**
     * 分配人员Id
     */
    private Long userId;

    /**
     * 分配部门Id
     */
    private Long deptId;

    /**
     * 分配区域Id
     */
    private Long areaId;

    /**
     * 区域对应的销售类型，不指定默认为0
     */
    private int saleType;

    /**
     * 分配url
     **/
    private String url;

    /**
     * 分配ip
     */
    private String ip;

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * NC客户编号 nc_id
     **/
    private String ncId;

    /**
     * 客户状态（-1：无效 0：潜在 1：预约 2：成交) status
     **/
    private Integer status;

    /**
     * 咨询次数（1：首次咨询  >1：重单商机数量） consult
     **/
    private Integer consult;

    /**
     * 客户来源：（0：系统   1：官网    2：客服   3：代理网  ） source
     **/
    private Integer source;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建人，主要针对小能
     */
    private String creator;

    /**
     * 客户手机号码
     **/
    private String phone;

    /**
     * 客户主联系方式
     */
    private String contact;

    /**
     * 座机
     **/
    private String landline;

    /**
     * qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 客户名称 name
     **/
    private String name;

    /**
     * 性别(-1：女   0：未知   1：男) sex
     **/
    private Integer sex;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
     * 分配时间
     */
    private Date allotTime;
    private String address;
    private Date createTime;
    private Long updateId;
    private Date updateTime;

}