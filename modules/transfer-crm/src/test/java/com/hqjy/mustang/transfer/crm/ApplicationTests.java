package com.hqjy.mustang.transfer.crm;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.TransferCrmApplication;
import com.hqjy.mustang.transfer.crm.model.dto.*;
import com.hqjy.mustang.transfer.crm.service.NcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransferCrmApplication.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private NcService ncService;

@Test
	public void requestNcSaveTest() {

	}




	@Test
	public void getSchoolByName() {
		NcSchoolDTO school = ncService.getSchoolByName("衡阳江东校区");
		System.out.println(JSON.toJSONString(school));
	}
}
