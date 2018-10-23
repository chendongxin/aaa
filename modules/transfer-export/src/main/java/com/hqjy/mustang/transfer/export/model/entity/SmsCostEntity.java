package com.hqjy.mustang.transfer.export.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xyq
 * @date create on 2018/10/20
 * @apiNote
 */
@Data
@Accessors(chain = true)
public class SmsCostEntity {

    /**
     * 短信Id
     */
    private Long id;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 发送状态：0 待发送，1提交发送，2发送成功，其他：发送失败对应code
     */
    private int status;

    /**
     * 发送内容
     */
    private String content;


    /**
     * 发送成功条数：规则：例如
     * 发一条短信70字以内，发送成功，成功次数为1，总消耗条数为1；
     * 发送一条短信100字，发送成功，成功次数为1，总消耗条数为2
     */
    private int count;
}
