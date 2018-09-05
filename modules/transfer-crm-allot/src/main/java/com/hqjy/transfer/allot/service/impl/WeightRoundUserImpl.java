package com.hqjy.transfer.allot.service.impl;

import com.hqjy.transfer.allot.dao.WeigthRoundDao;
import com.hqjy.transfer.allot.model.dto.AllotClassDTO;
import com.hqjy.transfer.allot.model.dto.WeigthRoundDTO;
import com.hqjy.transfer.allot.service.AbstractWeightRound;
import com.hqjy.transfer.common.base.utils.DateUtils;
import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.redis.utils.RedisKeys;
import com.hqjy.transfer.common.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据用户权重，获取部门的一个用户
 *
 * @author : heshuangshuang
 * @date : 2018/6/7 15:39
 */
@Service
@Slf4j
public class WeightRoundUserImpl extends AbstractWeightRound {

    private final static DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private WeigthRoundDao weigthRoundDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String keyPrefix() {
        return RedisKeys.Allot.key("user");
    }

    @Override
    public List<Object> initData(Long id) {
        // 排班list
        List<AllotClassDTO> classList = getScheduleList(id);
        if (classList.size() > 0) {
            log.debug("设置失效时间为当前班次的下班时间");
            // 根据class的最早的排版，下班时间进行重置
            redisUtils.set(RedisKeys.Allot.expire(id), DateUtils.formatPattern(classList.get(0).getStop()), classList.get(0).getStop());
            // 排班用户List
            List<WeigthRoundDTO> userList = weigthRoundDao.getUserList(LocalDate.now().format(YMD), id, classList);
            return userList.stream().map(JsonUtil::toJson).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 根据当前时间和排班表，分析商机应该分配给哪个时间段
     *
     * @param deptId 团队Id
     * @return 排班列表
     */
    private List<AllotClassDTO> getScheduleList(Long deptId) {
        List<AllotClassDTO> schedule = weigthRoundDao.getDeptClassList(deptId);
        log.info("teamId:{},当前排班表list长度:{}", deptId, schedule.size());
        if (schedule.size() > 0) {
            //当前日期

            LocalDate nowDate = LocalDate.now();
            log.info("当前日期:{}", nowDate);

            //当前时间
            LocalTime nowTime = LocalTime.now();
            log.info("当前时间:{}", nowTime);

            //第一个值班团队
            AllotClassDTO firstSchedule = schedule.get(0);
            log.info("第一个值班团队:{}", firstSchedule);

            //用于存放筛选出的值班团队
            List<AllotClassDTO> newSchedule = new ArrayList<>();

            //当日没有值班团队，返回次日最早上班的团队列表
            if (firstSchedule.getDutyDate().isAfter(nowDate)) {
                log.info("当前是本日下班时间");
                for (AllotClassDTO value : schedule) {
                    if (value.getStartTime() == firstSchedule.getStartTime()) {
                        newSchedule.add(value);
                    } else {
                        break;
                    }
                }
                log.info("获得次日最早值班列表:{}", newSchedule);
                return newSchedule;
            }

            //当日有值班团队，但还未到上班时间，返回本日最早上班的团队
            if (nowTime.isBefore(firstSchedule.getStartTime())) {
                log.info("当前是本日还未上班时间,或当前时间没有值班团队:{}", nowTime);
                for (AllotClassDTO value : schedule) {
                    //不遍历第二天的排班
                    if (value.getStartTime() == firstSchedule.getStartTime() && !value.getDutyDate().isAfter(nowDate)) {
                        newSchedule.add(value);
                    } else {
                        break;
                    }
                }
                log.info("获得当前时间临近值班列表:{}", newSchedule);
                return newSchedule;
            }

            for (AllotClassDTO value : schedule) {
                //不遍历次日的排班
                if (value.getDutyDate().isAfter(nowDate)) {
                    log.info("遍历到次日，结束遍历:{}", value);
                    return newSchedule;
                }
                //遍历出当前时间在排班时间段之内的，正常的值班时间
                if (!value.getStartTime().isAfter(nowTime) && !value.getStopTime().isBefore(nowTime)) {
                    newSchedule.add(value);
                    log.info("获得一个正常值班团队，加入List:{}", value);
                }
                //值班开始时间大于当前之间，正常值班遍历完毕
                if (value.getStartTime().isAfter(nowTime)) {
                    log.info("获得正常值班所有团队列表:{}", newSchedule);
                    //后面的团队上班时间作为当前排班团队的下班时间,用于重置游标
                    newSchedule.forEach(scheduleEntity -> {
                        scheduleEntity.setStopTime(value.getStartTime());
                    });
                    return newSchedule;
                }
            }
            return newSchedule;
        }
        return schedule;
    }
}
