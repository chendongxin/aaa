package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import com.hqjy.mustang.admin.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息发送服务
 *
 * @author : heshuangshuang
 * @date : 2018/9/5 19:50
 */
@RestController
@RequestMapping(Constant.API_PATH + "/message")
public class SysMessageApi {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 发送通知
     */
    @PostMapping("/sendNotice")
    public void sendNotice(@RequestBody MessageSendVO msg) {
        sysMessageService.sendNotice(msg.getUserId(), msg.getTitle(), msg.getContent(), msg.getData());
    }

    /**
     * 发送消息
     */
    @PostMapping("/sendNotice")
    public void sendMessage(@RequestBody MessageSendVO msg) {
        sysMessageService.sendMessage(msg.getUserId(), msg.getTitle(), msg.getContent(), msg.getData());
    }

    /**
     * 主动断开本地连接
     */
    @DeleteMapping("/{userId}")
    public void disconnect(@PathVariable("userId") Long userId) {
        sysMessageService.disconnect(userId);
    }

}
