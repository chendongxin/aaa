package com.hqjy.mustang.transfer.export.util;

import com.hqjy.mustang.transfer.export.model.query.QueryObject;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/18
 * @apiNote
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
    private List<T> list;


    public PageUtil page(QueryObject pageQuery, List<T> list) {
        //设置当前页
        this.currPage = pageQuery.getPageNum();
        //获得分页大小
        this.pageSize = pageQuery.getPageSize();

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

        this.setTotalCount(list.size());

        if (this.totalCount % this.pageSize == 0) {
            this.totalPage = this.totalCount / this.pageSize;
        } else if (list.size() < this.pageSize) {
            this.totalPage = 1;
        } else {
            this.totalPage = this.totalCount / this.pageSize + 1;
        }

        //截取数据集合，获得分页数据
        List<T> subList = list.subList(firstIndex, toIndex);
        this.size=list.size();
        this.list=subList;


        return null;
    }
}
