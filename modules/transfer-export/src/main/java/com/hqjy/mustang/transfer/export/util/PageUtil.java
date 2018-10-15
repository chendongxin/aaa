package com.hqjy.mustang.transfer.export.util;

import com.hqjy.mustang.transfer.export.model.query.PageParams;
import lombok.Data;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/18
 * @apiNote 查询结果集进行分页处理
 */
@Data
public class PageUtil<T> {

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
     * 当前页实际条数
     */
    private int size;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 结果集
     */
    private List<T> list;


    public PageUtil(PageParams pageParams, List<T> list) {
        if (!list.isEmpty()) {
            this.size=list.size();
            this.currPage = pageParams.getPageNum();
            this.pageSize = pageParams.getPageSize();
            this.totalCount = list.size();

            //获得分页数据在list集合中的索引
            int firstIndex = (currPage - 1) * pageSize;
            int toIndex = currPage * pageSize;
            if (toIndex > size) {
                toIndex = size;
            }
            if (firstIndex > toIndex) {
                firstIndex = 0;
                this.currPage = 1;
            }

            if (this.totalCount % this.pageSize == 0) {
                this.totalPage = this.totalCount / this.pageSize;
            } else if (list.size() < this.pageSize) {
                this.totalPage = 1;
            } else {
                this.totalPage = this.totalCount / this.pageSize + 1;
            }

            //截取数据集合，获得分页数据
            List<T> subList = list.subList(firstIndex, toIndex);
            this.size = subList.size();
            this.list = subList;
        } else {
            this.currPage = 1;
            this.pageSize = pageParams.getPageSize();
            this.totalPage = 1;
            this.size = 0;
            this.totalCount = 0;
        }
    }
}
