package com.hqjy.mustang.transfer.crm.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/10/24
 * @apiNote
 */
@RestController
@RequestMapping(Constant.API_PATH + "/genWay")
@Slf4j
public class TransferGenWayApi {


    private TransferGenWayService transferGenWayService;

    @Autowired
    public void setTransferGenWayService(TransferGenWayService transferGenWayService) {
        this.transferGenWayService = transferGenWayService;
    }


    /**
     * 获取所有推广方式
     *F=
     * @return 返回集合
     * @author xyq
     * @date 2018年10月24日15:07:23
     */
    @GetMapping("/getAllGenWayList")
    public List<TransferGenWayEntity> getAllGenWayList() {
        return transferGenWayService.getAllGenWayList();
    }
}
