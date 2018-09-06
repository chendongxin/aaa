package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.admin.service.SysMessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author : heshuangshuang
 * @date : 2018/6/20 11:36
 */
@Api(tags = "消息中心", description = "SysMessageController")
@RestController
@RequestMapping("/sys/message")
public class SysMessageController {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 获取websocket地址
     */
    @GetMapping("/websocketUrl")
    public R websocketUrl() {
        String url = sysMessageService.getWebsocketUrl();
        if (StringUtils.isNotEmpty(url)) {
            return R.result(url);
        }
        return R.error("系统未配置websockt地址");
    }

    /**
     * 用户查询消息列表
     */
    @GetMapping("/user/list")
    public R list(@RequestParam HashMap<String, Object> pageParam) {
        return R.ok(new PageInfo<>(sysMessageService.getUserMessageListPage(PageQuery.build(pageParam))));
    }

    /**
     * 根据消息ID标记一条已读
     */
    @PutMapping("/user/{msgId}")
    public R readMessage(@PathVariable("msgId") Long msgId) {
        sysMessageService.readUserMessage(msgId);
        return R.ok();
    }

    /**
     * 根据类型标记所有已读
     */
    @PutMapping("/user/{type}/all")
    public R readAllMessage(@PathVariable("type") Integer type) {
        sysMessageService.readUserAllMessage(type);
        return R.ok();
    }

    /**
     * 根据消息ID删除消息
     */
    @DeleteMapping("/user/{msgId}")
    public R deleteMessage(@PathVariable("msgId") Long msgId) {
        sysMessageService.deleteUserMessage(msgId);
        return R.ok();
    }

    /**
     * 根据类型清空消息
     */
    @DeleteMapping("/user/{type}/all")
    public R cleanMessage(@PathVariable("type") Integer type) {
        sysMessageService.cleanUserMessage(type);
        return R.ok();
    }

    /**
     * 统计消息数量
     */
    @GetMapping("/user/totalCount")
    public R totalCount() {
        return R.ok().add("total", sysMessageService.totalCount());
    }

    /**
     * 统计消息数量
     */
    @GetMapping("/user/countInfo")
    public R countInfo() {
        return R.ok(sysMessageService.countInfo());
    }
}
