package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xieyuqing
 * @ description nc新增跟进记录接口请求参数封装对象
 * @ date create in 2018/5/29 17:26
 */
@Data
@Accessors(chain = true)
public class NcFollowUpRequestDTO {
	/**
	 * nc_id
	 */
	private String id;
	/**
	 * 跟进记录
	 */
	private List<NcFollowUpDTO> records;

}
