package com.hqjy.mustang.transfer.crm.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.crm.TransferSourceInfo;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 16:37
 */
@RestController
@RequestMapping(Constant.API_PATH + "/source")
@Slf4j
public class TransferSourceApi {

    @Autowired
    private TransferSourceService transferSourceService;

    /**
     * 根据邮箱域名查询来源配置
     */
    @GetMapping("/findByEmailDomain")
    public TransferSourceInfo findByEmailDomain(@RequestParam("email") String email) {
        return Optional.ofNullable(transferSourceService.findByEmailDomain(email)).map(s -> PojoConvertUtil.convert(s, TransferSourceInfo.class)).orElse(null);
    }
}
