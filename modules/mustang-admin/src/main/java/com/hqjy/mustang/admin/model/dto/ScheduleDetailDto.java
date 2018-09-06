package com.hqjy.mustang.admin.model.dto;

import lombok.Data;

/**
 * 排班细节信息
 * @author linyuqin
 * @date 2017-10-27 09:43:11
 */
@Data
public class ScheduleDetailDto {

	/**
	 * 排班记录主键ID
	 */
	private Long scheduleId;
	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 周一班次名称
	 */
	private String monClassName;
	/**
	 * 周一班次id
	 */
	private Long monClassId;

	private String tueClassName;

	private Long tueClassId;

	private String wedClassName;

	private Long wedClassId;

	private String thuClassName;

	private Long thuClassId;

	private String firClassName;

	private Long firClassId;

	private String satClassName;

	private Long satClassId;

	private String sunClassName;

	private Long sunClassId;
}