package com.hqjy.mustang.allot.dao;

import com.hqjy.mustang.allot.model.dto.AllotClassDTO;
import com.hqjy.mustang.allot.model.dto.WeigthRoundDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WeigthRound 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/06 15:04
 */
@Mapper
public interface WeigthRoundDao {
    /**
     * 根据部门id 查询子部门列表
     */
    List<WeigthRoundDTO> getDeptList(Long deptId);

    /**
     * 根据部门id 查询人员列表
     */
    List<WeigthRoundDTO> getUserList(Long deptId);

    /**
     * 获取指定时间的排版id列表，不指定时间，查询最早排班
     */
    List<AllotClassDTO> getDeptClassList(@Param("deptId") Long deptId);

    /**
     * 根据部门id 和排班id查询排班人员列表
     */
    List<WeigthRoundDTO> getUserListByClass(@Param("date") String date, @Param("deptId") Long deptId, @Param("classList") List<AllotClassDTO> classList);

    /**
     * 根据区域编码查询处理此区域下商机的部门列表
     */
    List<WeigthRoundDTO> getDeptListByAreaId(Long areaId);

}