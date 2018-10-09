package com.hqjy.mustang.transfer.crm.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.transfer.crm.service.TransferKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 17:49
 */
@RestController
@RequestMapping(Constant.API_PATH + "/keyword")
@Slf4j
public class TransferKeywordApi {

    @Autowired
    private TransferKeywordService transferKeywordService;

    /**
     * 根据职位信息，遍历出关键字
     */
    @GetMapping("/getKeyword")
    public String getKeyword(@RequestParam("info") String info) {
        return transferKeywordService.getKeyWork(info);
    }
}
