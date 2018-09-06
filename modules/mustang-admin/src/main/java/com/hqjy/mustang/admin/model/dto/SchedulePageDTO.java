package com.hqjy.mustang.admin.model.dto;

import lombok.Data;

import java.util.List;


/**
 * @author xieyuqing
 * @ description 专门封装排班的分页对象
 * @ date create in 2018/6/1 18:28
 */
@Data
public class SchedulePageDTO{

    /**
     * 当前页数
     */
    private int currPage;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总条数
     */
    private int totalCount;
    /**
     * 当前页条数
     */
    private int size;

    /**
     * 每页大小
     */
    private int pageSize = 10;

    /**
     * 结果集
     */
    private List list;

    /**
     * 一周日期
     */
    private WeekDto week;


    public SchedulePageDTO(int rows) {

        this.setTotalCount(rows);
        if (this.totalCount % this.pageSize == 0) {
            this.totalPage = this.totalCount / this.pageSize;
        } else if (rows < this.pageSize) {
            this.totalPage = 1;
        } else {
            this.totalPage = this.totalCount / this.pageSize + 1;
        }
    }
}
