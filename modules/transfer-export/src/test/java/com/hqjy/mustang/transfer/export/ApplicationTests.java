package com.hqjy.mustang.transfer.export;

import com.hqjy.mustang.common.base.utils.OssFileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class ApplicationTests {

	@Test
	public void ossLoads() {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//aliyun目录
			String dir = "test";
			//文件名称
			String fileName = "测试阿里上传下载_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
			//上传文件至阿里云
			String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
			//下载地址有效时间1个小时
			System.out.println(OssFileUtils.getVisitUrl(recordFile, 3600));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
