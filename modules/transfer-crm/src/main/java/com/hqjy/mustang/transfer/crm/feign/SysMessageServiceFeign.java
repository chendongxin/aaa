package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mustang-admin")
public interface SysMessageServiceFeign {

    @PostMapping(Constant.API_PATH + "/message/sendMessage")
    void sendMessage(@RequestBody MessageSendVO msg);

    @PostMapping(Constant.API_PATH + "/message/sendNotice")
    void sendNotice(@RequestBody MessageSendVO msg);

}