package com.hqjy.mustang.transfer.crawler.service.impl;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.crawler.service.ParseMessageService;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Optional;

import static com.hqjy.mustang.common.base.constant.Constant.Gender.*;

/**
 * 邮件解析
 *
 * @author : heshuangshuang
 * @date : 2018/9/11 11:28
 */
@Service("parseFiveEightService")
@Slf4j
public class ParseFiveEightServiceImpl implements ParseMessageService {
    /**
     * 解析邮件
     */
    @Override
    public TransferResumeEntity parseMessage(TransferResumeEntity resumeEntity) throws MessagingException, IOException {
        // 根据邮箱后缀判断内容的解析方式
        if (resumeEntity != null && StringUtils.isNotEmpty(resumeEntity.getResumeDetail())) {
            Document doc = Jsoup.parse(resumeEntity.getResumeDetail());
            Elements address = doc.select("body *");
            for (Element current : address) {
                String ownTest = current.ownText();
                if (StringUtils.isNotEmpty(ownTest)) {
                    // 基本信息
                    if (current.attr("style").startsWith("margin:0;padding:0;font-size:22px;line-height:22px;padding-bottom:10px;font-family:'微软雅黑';font-weight:normal;")) {
                        // 姓名
                        resumeEntity.setName(ownTest);
                        String sexAndAgeStr = current.child(0).ownText();
                        String[] sexAndAge = Optional.ofNullable(sexAndAgeStr).map(s -> StringUtils.cut(s, "（", "）").split("，")).orElse(null);
                        if (sexAndAge != null && sexAndAge.length == 2) {
                            // 性别
                            resumeEntity.setSex(MAN.getCode().contains((sexAndAge[0])) ? MAN.getValue() : WOMEN.getCode().contains((sexAndAge[0])) ? Constant.Gender.WOMEN.getValue() : UNKNOWN.getValue());
                            // 年龄
                            resumeEntity.setAge(StringUtils.cut(sexAndAge[1], "", "岁"));
                        }

                        ListIterator<Element> xllist = current.nextElementSibling().select("li").listIterator();
                        while (xllist.hasNext()) {
                            int index = xllist.nextIndex();
                            String xlText = xllist.next().ownText();
                            switch (index) {
                                case 0:
                                    // 学历
                                    resumeEntity.setEducationName(StringUtils.cutPrefix(xlText, "学历"));
                                    break;
                                case 1:
                                    // 工作经验
                                    resumeEntity.setWorkExperience(StringUtils.cutPrefix(xlText, "工作经验"));
                                    break;
                                case 2:
                                    // 籍贯
                                    //resumeEntity.setWorking(xlText);
                                    break;
                                case 3:
                                    // 现居住
                                    resumeEntity.setAddress(StringUtils.cutPrefix(xlText, "现居住"));
                                    break;
                                default:
                            }
                        }

                    } else if (ownTest.startsWith("手机号码")) {
                        // 手机号码
                        resumeEntity.setPhone(current.child(0).text());

                    } else if (ownTest.startsWith("电子邮箱")) {
                        // 电子邮箱
                        resumeEntity.setEmail(current.child(0).text());

                    } else if (ownTest.startsWith("求职意向")) {
                        ListIterator<Element> jobslist = current.parent().nextElementSibling().select("li").listIterator();
                        while (jobslist.hasNext()) {
                            int index = jobslist.nextIndex();
                            String jobInfo = jobslist.next().ownText();
                            switch (index) {
                                case 0:
                                    // 期望职位
                                    resumeEntity.setPositionApplied(jobInfo);
                                    break;
                                case 1:
                                    // 期望薪资
                                    resumeEntity.setSalary(jobInfo);
                                    break;
                                case 2:
                                    // 期望地区
                                    resumeEntity.setAddress(jobInfo);
                                    break;
                                default:
                            }
                        }
                    }

                }
            }
        }
        return resumeEntity;
    }
}