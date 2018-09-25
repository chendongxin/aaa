package com.hqjy.mustang.transfer.crawler.factory;

import com.hqjy.mustang.common.base.utils.SpringContextUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.service.AbstractParseService;

/**
 * @author : heshuangshuang
 * @date : 2018/9/12 10:47
 */
public class ParseMailFactory {

    public static AbstractParseService build(String mail) {
        String suffix = StringUtils.cutFrom(mail, "@");
        switch (suffix) {
            // 58同城
            case "@zp.58.com":
                return (AbstractParseService) SpringContextUtils.getBean("parseFiveEightService");
            // 中华英才网
            case "@info.chinahr.com":
                return (AbstractParseService) SpringContextUtils.getBean("parseChinahrService");
            default:
                return null;
        }
    }

}
