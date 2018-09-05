package com.hqjy.transfer.crm.modules.sys.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xieyuqing
 * @ description 封装排班数据
 * @ date create in 2018/6/1 18:28
 */
@Data
public class ScheduleDto {
	/**
	 *  周班次列表
	 */
	private List<ScheduleDetailDto> scheduleList;
	/**
	 * 周天
	 */
	private WeekDto week;


}
