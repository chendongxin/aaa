package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.TransferGenWayInfo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyq
 * @date create on 2018/10/24
 * @apiNote
 */
@FeignClient(name = "transfer-crm", fallbackFactory = TransferGenWayFeignFallbackFactory.class)
public interface TransferGenWayFeign {

    /**
     * 获取所有推广方式
     *
     * @return 返回集合
     * @author xyq
     * @date 2018年10月24日15:07:23
     */
    @GetMapping(value = Constant.API_PATH + "/genWay/getAllGenWayList")
    List<TransferGenWayInfo> getAllGenWayList();
}


@Component
class TransferGenWayFeignFallbackFactory implements FallbackFactory<TransferGenWayFeign> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferGenWayFeignFallbackFactory.class);

    @Override
    public TransferGenWayFeign create(Throwable throwable) {
        return new TransferGenWayFeign() {
            @Override
            public List<TransferGenWayInfo> getAllGenWayList() {
                LOGGER.error("调用方法{}异常:{},原因：{}", "TransferGenWayFeign.getAllGenWayList()", throwable.getMessage());
                return new ArrayList<>();
            }
        };
    }
}