package com.hqjy.mustang.transfer.call.service.impl;


import com.hqjy.mustang.common.base.utils.RestHttpUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import com.hqjy.mustang.transfer.call.service.CallCenterService;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import com.hqjy.mustang.transfer.call.service.TransferCallRecordService;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class CallCenterServiceImpl  implements CallCenterService {


    @Autowired
    private TransferCallRecordService transferCallRecordService;

    /**
     * 获取录音文件地址
     * @param id
     * @return
     */
    @Override
    public String getRecordFileUrl(Long id) {
        TransferCallRecordEntity t = transferCallRecordService.findOne(id);
        String url = Optional.ofNullable(t).map(TransferCallRecordEntity::getRecordFile).orElse(null);
        if(StringUtils.isNotEmpty(url)){
            return url;
        }
        return null;
    }

    /*public String getRecordFileUrl(String fileUrl) {
        CloseableHttpResponse response = RestHttpUtils.getInstance().sendPost(fileUrl);
        if (response == null) {
            return null;
        }
        //从headers中获取录音地址
        Header[] headers = response.getHeaders("Location");
        if (headers.length == 0) {
            log.error("没有获取到录音，请检查代码");
            return null;
        }
        Header header = headers[0];
        String location = header.getValue();
        if (StringUtils.isNotEmpty(location)) {
            return location;
        }
        return null;
    }*/
}
