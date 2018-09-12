package com.hqjy.mustang.transfer.crm.nc;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.TransferCrmApplication;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.service.NcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = NcApiTest.class)
public class NcApiTest {

    @Resource
    private NcService ncService;

    @Test
    public void requestNcSaveTest() {
        NcBizSaveParamDTO ncRequestParams = new NcBizSaveParamDTO()
                .setLxqq("1784194284").setTrue_name("自考集训基地");
        NcBizSaveResultDTO r = ncService.requestNcSave(ncRequestParams);
        System.out.println("msg:" + r.getMsg() + ",,code：" + r.getCode());
    }

    @Test
    public void requestNcUpdateTest() {
        NcBizRequestDTO ncRequestParams = new NcBizRequestDTO()
                .setOrgCode("0001A510000000000L40")
                .setSaleman("yangfengge")
                .setId("0001DS100000000QBBTW")
                .setZsls("00280");
        NcResponseDTO r = ncService.requestNcUpdate(ncRequestParams);
        System.out.println("msg:" + r.getMsg() + ",code：" + r.getCode() + ",data" + r.getData());
    }

    @Test
    public void requestNcFollowUpSaveTest() {
        List<NcFollowUpDTO> ncFollowUpVos = new ArrayList<>();
        ncFollowUpVos.add(new NcFollowUpDTO().setFollowstate(3));

        NcFollowUpRequestDTO ncFollowUpRequestVo = new NcFollowUpRequestDTO()
                .setId("0001DS100000000OT4OD")
                .setRecords(ncFollowUpVos);
        NcResponseDTO r = ncService.requestNcFollowUp(ncFollowUpRequestVo);
        System.out.println("msg:" + r.getMsg() + ",code：" + r.getCode() + ",data" + r.getData());
    }


    @Test
    public void requestNCGetTeacherTest() {
        String schoolId = "0001A510000000001DO5";
        R r = ncService.requestNCGetTeacher(schoolId);
        System.out.println("msg:" + r.get("msg") + ",code：" + r.get("code") + ",result" + r.get("result"));
    }

    @Test
    public void getSelfTestSaleMan() {
        R r = ncService.getSelfTestSaleMan();
        System.out.println("msg:" + r.get("msg") + ",code：" + r.get("code") + ",result" + r.get("result"));
    }


    @Test
    public void getSchoolOrProvince() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "PROVINCE");
        R r = ncService.getSchoolOrProvince(map);
        System.out.println("msg:" + r.get("msg") + ",code：" + r.get("code") + ",result" + r.get("result"));
    }
}