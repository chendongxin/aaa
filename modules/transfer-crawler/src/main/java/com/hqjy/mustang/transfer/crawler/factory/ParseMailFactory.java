package com.hqjy.mustang.transfer.crawler.factory;

import com.hqjy.mustang.common.base.utils.SpringContextUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.service.ParseMessageService;

/**
 * @author : heshuangshuang
 * @date : 2018/9/12 10:47
 */
public class ParseMailFactory {

    public static ParseMessageService build(String mail) {
        String suffix = StringUtils.cutFrom(mail, "@");
        switch (suffix) {
            // 58同城
            case "@zp.58.com":
                return (ParseMessageService) SpringContextUtils.getBean("parseFiveEightService");
            // 中华英才网
            case "@info.chinahr.com":
                return (ParseMessageService) SpringContextUtils.getBean("parseChinahrService");
            default:
                return null;
        }
    }

}
