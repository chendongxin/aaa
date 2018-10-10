package com.hqjy.mustang.transfer.crawler.context;

import com.hqjy.mustang.common.base.utils.SpringContextUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import com.hqjy.mustang.transfer.crawler.service.AbstractParseService;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author : heshuangshuang
 * @date : 2018/9/12 10:47
 */
public class ParseMailContext {

    private AbstractParseService abstractParseService;

    public ParseMailContext(String mail) {
        String suffix = StringUtils.cutFrom(mail, "@");
        switch (suffix) {
            // 58同城
            case "@zp.58.com":
                abstractParseService = getParseService("parseFiveEightService");
                break;
            // 中华英才网
            case "@info.chinahr.com":
                abstractParseService = getParseService("parseChinahrService");
                break;
            // 默认58同城
            default:
                abstractParseService = getParseService("parseFiveEightService");
        }
    }

    public void parseMessage(TransferResumeEntity resumeEntity) throws IOException, MessagingException {
        if (abstractParseService != null) {
            abstractParseService.parseMessage(resumeEntity);
        }
    }

    private AbstractParseService getParseService(String name) {
        return (AbstractParseService) SpringContextUtils.getBean(name);
    }

}
