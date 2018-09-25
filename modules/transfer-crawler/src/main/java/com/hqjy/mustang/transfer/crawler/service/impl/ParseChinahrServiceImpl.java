package com.hqjy.mustang.transfer.crawler.service.impl;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.service.AbstractParseService;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

import static com.hqjy.mustang.common.base.constant.Constant.Gender.*;

/**
 * 中华英才网
 *
 * @author : heshuangshuang
 * @date : 2018/9/12 16:53
 */
@Service("parseChinahrService")
@Slf4j
public class ParseChinahrServiceImpl extends AbstractParseService {
    /**
     * 解析邮件
     */
    @Override
    public TransferResumeEntity parseMessage(TransferResumeEntity resumeEntity) throws MessagingException, IOException {
        // 根据邮箱后缀判断内容的解析方式
        if (resumeEntity != null && StringUtils.isNotEmpty(resumeEntity.getResumeDetail())) {
            Document doc = Jsoup.parse(resumeEntity.getResumeDetail());
            Elements body = doc.select("body *");
            for (Element current : body) {
                String ownTest = current.ownText();
                if (StringUtils.isNotEmpty(ownTest)) {
                    // 基本信息
                    if (current.attr("style").startsWith("display: block;font-size: 22px;line-height:22px;padding:0;margin:5px 0; font-weight: normal;color: #282828;")) {
                        // 姓名
                        resumeEntity.setName(ownTest);
                        String sexAndAgeStr = current.nextElementSibling().text();
                        if (StringUtils.isNotEmpty(sexAndAgeStr)) {
                            String[] sexAndAge = sexAndAgeStr.replaceAll(" ", "").split("\\|");
                            // 性别
                            resumeEntity.setSex(MAN.getCode().contains((sexAndAge[0])) ? MAN.getValue() : WOMEN.getCode().contains((sexAndAge[0])) ? Constant.Gender.WOMEN.getValue() : UNKNOWN.getValue());
                            // 年龄
                            resumeEntity.setAge(StringUtils.cut(sexAndAge[1], "", "岁"));
                            // 学历
                            resumeEntity.setEducation(Constant.Education.NONE.handleEducationName(sexAndAge[2]));
                            // 工作经验
                            resumeEntity.setWorkExperience(handleWorkExperience(sexAndAge[3]));
                        }

                    } else if (current.attr("style").startsWith("font-size:14px;  margin-right:30px;vertical-align: middle")) {
                        // 手机号码
                        resumeEntity.setPhone(current.ownText());
                    } else if (current.attr("style").startsWith("font-size: 14px; vertical-align: middle")) {
                        // 电子邮箱
                        resumeEntity.setEmail(current.text());
                    } else if (ownTest.startsWith("求职意向") && current.attr("class").equals("gray")) {
                        System.out.println("求职意向:" + current.parent().ownText());
                    } else if (ownTest.startsWith("期望地区") && current.attr("class").equals("gray")) {
                        // 期望地区
                        resumeEntity.setAddress(current.parent().ownText());
                    } else if (ownTest.startsWith("期望薪水") && current.attr("class").equals("gray")) {
                        // 期望薪资
                        resumeEntity.setSalary(current.parent().ownText());
                    } else if (ownTest.startsWith("期望工作") && current.attr("class").equals("gray")) {
                        // 期望职位
                        resumeEntity.setPositionApplied(current.parent().ownText());
                    } else if (ownTest.startsWith("教育经历")) {
                        Element school = current.parent().nextElementSibling();
                        //毕业学校
                        resumeEntity.setSchool(school.children().first().ownText());
                        Element profession = school.nextElementSibling();
                        //专业
                        resumeEntity.setMajor(profession.children().first().ownText());
                    }
                }
            }
        }
        return resumeEntity;
    }
}
