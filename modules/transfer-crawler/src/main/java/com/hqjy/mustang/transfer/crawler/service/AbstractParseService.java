package com.hqjy.mustang.transfer.crawler.service;

import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 11:28
 */
public abstract class AbstractParseService {

    private static Pattern p = Pattern.compile("[^\\d]+([\\d]+)[^\\d]+.*");

    public abstract TransferResumeEntity parseMessage(TransferResumeEntity resumeEntity) throws MessagingException, IOException;

    /**
     * 工作经验，通过文字去匹配，不准确，但是非要这样做也没办法；
     */
    public Float handleWorkExperience(String educationName) {
        try {
            if (StringUtils.isNotEmpty(educationName)) {
                educationName = educationName
                        .replaceAll("一", "1")
                        .replaceAll("二", "2")
                        .replaceAll("三", "3")
                        .replaceAll("四", "4")
                        .replaceAll("五", "5")
                        .replaceAll("六", "6")
                        .replaceAll("七", "7")
                        .replaceAll("八", "8")
                        .replaceAll("九", "9")
                        .replaceAll("十", "10");

                int index = educationName.indexOf("-");
                if (index > 0) {
                    return Float.valueOf(educationName.substring(index - 1, index));
                }

                int year = educationName.lastIndexOf("年");
                if (year > 0) {
                    return Float.valueOf(educationName.substring(year - 1, year));
                }

                Matcher m = p.matcher("x" + educationName + "x");
                boolean result = m.find();
                if (result) {
                    return Float.valueOf(m.group(1));
                }
            }
            return 0f;
        } catch (Exception e) {
            return 0f;
        }
    }

    public Integer handleAge(String age) {
        if (StringUtils.isNotEmpty(age)) {
            age = age.replaceAll("岁", "").replaceAll(" ", "");
            try {
                return Integer.parseInt(age);
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}
