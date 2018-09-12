package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.service.impl.NcServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xieyuqing
 * @ description NC交互接口
 * @ date create in 2018年9月12日14:59:48
 */
@Api(tags = "系统与NC交互接口")
@RestController
@RequestMapping("/nc")
public class NcController {

    private NcServiceImpl ncRestTemplate;

    @Autowired
    public void setRestTemplate(NcServiceImpl ncRestTemplate) {
        this.ncRestTemplate = ncRestTemplate;
    }


    @ApiOperation(value = "根据招生校区获取NC招生老师", notes = "根据招生校区获取NC招生老师,参数名key：(校区编码)schoolNcId，value值例：0001A510000000001DO5")
    @GetMapping("/getNcTeacher")
    public R getNcTeacher(@RequestParam("schoolNcId") String schoolNcId) {
        return ncRestTemplate.requestNCGetTeacher(schoolNcId);
    }

    @ApiOperation(value = "获取NC自考销售人员数据", notes = "获取NC自考销售人员数据：code:NC账号，姓名拼音，name:NC账号中文名\n" +
            "接口返回数据示例：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"code\": \"duzuopeng\",\n" +
            "            \"name\": \"杜作鹏\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"hanzhiwei\",\n" +
            "            \"name\": \"韩智威\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")
    @GetMapping("/getSelfTestSaleMan")
    public R getSelfTestSaleMan() {
        return ncRestTemplate.getSelfTestSaleMan();
    }


    @ApiOperation(value = "获取所有省份或校区数据", notes = "参数备注：【type:PROVINCE(省份)SCHOOL(校区)】,【code:省或校区编号】，【name：省或校区名称】\n" +
            "获取省份的请求参数：{\"type\":\"PROVINCE\"}\n" +
            "获取省对应的校区请求参数：\n" +
            "{\n" +
            "    \"rootCode\": \"JQ103\",\n" +
            "    \"type\": \"SCHOOL\"\n" +
            "}\n" +
            "接口返回数据示例：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"code\": \"JQ105\",\n" +
            "            \"name\": \"广西省（牵引力）\",\n" +
            "            \"ncId\": \"0001A5100000000M94BN\",\n" +
            "        }\n" +
            "    ]\n" +
            "}")
    @PostMapping("/getSchoolOrProvince")
    public R getSchoolOrProvince(@RequestBody Map<String, Object> params) {
        return ncRestTemplate.getSchoolOrProvince(params);
    }
}
