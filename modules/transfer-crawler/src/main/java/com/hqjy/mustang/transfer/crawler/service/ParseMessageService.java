package com.hqjy.mustang.transfer.crawler.service;

import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 11:28
 */
public interface ParseMessageService {

    TransferResumeEntity parseMessage(TransferResumeEntity resumeEntity) throws MessagingException, IOException;
}
