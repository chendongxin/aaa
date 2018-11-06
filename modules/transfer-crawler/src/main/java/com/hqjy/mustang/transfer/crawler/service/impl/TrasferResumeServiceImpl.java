package com.hqjy.mustang.transfer.crawler.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.base.utils.Tools;
import com.hqjy.mustang.common.model.crm.TransferSourceInfo;
import com.hqjy.mustang.transfer.crawler.context.ParseMailContext;
import com.hqjy.mustang.transfer.crawler.dao.TransferResumeDao;
import com.hqjy.mustang.transfer.crawler.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crawler.feign.TrasferSourceApiService;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import com.hqjy.mustang.transfer.crawler.service.MqSendService;
import com.hqjy.mustang.transfer.crawler.service.TrasferEmailService;
import com.hqjy.mustang.transfer.crawler.service.TrasferResumeService;
import com.hqjy.mustang.transfer.crawler.utils.MailUtils;
import com.sun.mail.imap.IMAPMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isGeneralSeat;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isSuperAdmin;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:17
 */
@Service
@Slf4j
public class TrasferResumeServiceImpl extends BaseServiceImpl<TransferResumeDao, TransferResumeEntity, Long> implements TrasferResumeService {

    /**
     * 线程池对象，单例
     */
    private static ExecutorService singleThreadPool;
    @Autowired
    private TrasferEmailService trasferEmailService;
    @Autowired
    private TrasferSourceApiService trasferSourceApiService;
    @Autowired
    private MqSendService mqSendService;

    private SysDeptServiceFeign sysDeptServiceFeign;
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Autowired
    public void setSysDeptServiceFeign(SysDeptServiceFeign sysDeptServiceFeign) {
        this.sysDeptServiceFeign = sysDeptServiceFeign;
    }
    @Autowired
    public void setSysUserDeptServiceFeign(SysUserDeptServiceFeign sysUserDeptServiceFeign) {
        this.sysUserDeptServiceFeign = sysUserDeptServiceFeign;
    }


    @PostConstruct
    public void init() {
        //创建线程池对象
        if (singleThreadPool == null) {
            //自定义线程名称
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("saveRecord-pool-%d").build();
            //当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
            long keepActiveTime = 60;
            //设置时间单位，秒
            TimeUnit timeUnit = TimeUnit.SECONDS;
            //线程池最大队列
            int capacity = 10240;
            //线程池最大能接受多少线程
            int maximumPoolSize = 10;
            //核心池大小
            int corePoolSize = 10;
            singleThreadPool = new java.util.concurrent.ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,
                    new LinkedBlockingQueue<>(capacity), namedThreadFactory, new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
            log.info("saveRecord线程池创建成功[核心池大小:" + corePoolSize + ";最大线程：" + maximumPoolSize + ";生命周期:" + keepActiveTime + "]");
        }
    }

    /**
     * 查询数据库中最后一条记录
     */
    @Override
    public TransferResumeEntity findLast() {
        return baseDao.findLast();
    }

    @Override
    public boolean start(Date beforeDate) {
        List<TransferEmailEntity> emailEntityList = trasferEmailService.findAllValid();
        log.debug("开始解析邮件:{}", beforeDate);
        // 循坏配置的邮箱信息
        for (TransferEmailEntity emailConfig : emailEntityList) {
            singleThreadPool.execute(() -> {
                // 线程池控制并发
                processEmail(emailConfig, beforeDate);
            });
        }
        return true;
    }


    /**
     * 处理邮件信息
     */
    private void processEmail(TransferEmailEntity emailConfig, Date beforeDate) {
        Store store = getEmailSession(emailConfig);
        if (store == null) {
            // 邮箱连接失败
            return;
        }
        Folder folder;
        try {
            folder = store.getFolder("INBOX");
        } catch (Exception e) {
            log.error("邮箱连接成功,打开收件箱失败:{}", emailConfig);
            return;
        }
        // 获得收件箱的邮件列表
        Message[] messages;
        try {
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();
            // 倒序遍历
            for (int i = messages.length - 1; i >= 0; i--) {
                // 解析邮件
                try {
                    Message msg = messages[i];
                    // 只读取指定时间之后的邮件
                    if (msg.getReceivedDate().before(beforeDate)) {
                        break;
                    }
                    // 解析邮件
                    IMAPMessage imapMessage = (IMAPMessage) msg;

                    TransferResumeEntity resumeEntity = new TransferResumeEntity();
                    // 邮件主题
                    resumeEntity.setEmailTitle(MailUtils.getSubject(imapMessage));
                    // 邮件内容
                    StringBuffer content = new StringBuffer();
                    MailUtils.getMailTextContent(imapMessage, content);
                    resumeEntity.setResumeDetail(content.toString());
                    // 发件时间
                    resumeEntity.setSendTime(imapMessage.getSentDate());
                    // 发送人邮件
                    String sendMali = MailUtils.getFrom(imapMessage);
                    resumeEntity.setSendMail(sendMali);

                    // 解析邮件内容
                    if (StringUtils.isNotEmpty(sendMali)) {
                        new ParseMailContext(sendMali).parseMessage(resumeEntity);
                    }

                    // 设置邮件关联属性
                    // 公司
                    resumeEntity.setCompanyId(emailConfig.getCompanyId());
                    resumeEntity.setCompanyName(emailConfig.getCompanyName());
                    // 收件人邮箱
                    resumeEntity.setCreateEmailId(emailConfig.getId());
                    resumeEntity.setCreateEmail(emailConfig.getEmail());

                    // 收件人用户信息
                    resumeEntity.setGenUserId(emailConfig.getUserId());
                    resumeEntity.setGenUserName(emailConfig.getUserName());

                    // 来源信息 读取 transfer_source 表中配置
                    TransferSourceInfo sourceInfo = trasferSourceApiService.findByEmailDomain(StringUtils.cutPrefix(StringUtils.cutFrom(sendMali, "@"), "@"));
                    resumeEntity.setSourceId(Optional.ofNullable(sourceInfo).map(TransferSourceInfo::getSourceId).orElse(0L));
                    resumeEntity.setSourceName(Optional.ofNullable(sourceInfo).map(TransferSourceInfo::getName).orElse("未知来源"));

                    // 产品（赛道）
                    resumeEntity.setProId(emailConfig.getProId());
                    resumeEntity.setProName(emailConfig.getProName());

                    // 分配部门
                    resumeEntity.setDeptId(emailConfig.getDeptId());
                    // 分配部门名称
                    resumeEntity.setDeptName(emailConfig.getDeptName());

                    // 同步开始时间
                    resumeEntity.setSyncTime(beforeDate);

                    int age = Optional.ofNullable(resumeEntity.getAge()).orElse(0);

                    // 有手机号码才进行保存 and 只爬取年龄段需要在18-30（包含18和30岁）之间的
                    if (StringUtils.isNotEmpty(resumeEntity.getPhone()) && age >= 18 && age <= 30) {
                        int count = baseDao.save(resumeEntity);
                        if (count > 0) {
                            // 保存成功，发送mq
                            log.info("保存成功，发送mq");
                            mqSendService.send(resumeEntity);
                        } else {
                            log.info("简历已经存在：{}", resumeEntity.getPhone());
                        }
                        // 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
                        imapMessage.setFlag(Flags.Flag.SEEN, true);
                    }
                } catch (Exception e) {
                    log.error("邮件解析失败：{}", e);
                }
            }
        } catch (MessagingException e) {
            log.error("读取邮箱信息异常:{}", e);
        } finally {
            // 释放资源
            try {
                if (folder != null) {
                    folder.close(false);
                }
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 连接邮箱，重试机制
     */
    private Store getEmailSession(TransferEmailEntity emailConfig) {
        Store store;
        for (int i = 0; i < 3; i++) {
            store = MailUtils.createEmailSession(emailConfig.getEmail(), emailConfig.getPassword());
            if (store != null) {
                log.info("邮箱连接成功:{}", emailConfig);
                return store;
            }
            log.error("{},登录此邮箱失败，10秒后重试", JsonUtil.toJson(emailConfig));
            // 需要进行重试,1分钟后重试，重试3次失败，则放弃
            Tools.sleep(10000);
        }
        log.error("{},重试登录邮箱 3 次 失败", JsonUtil.toJson(emailConfig));
        return null;
    }
    @Override
    public List<TransferResumeEntity> findPage(PageQuery pageQuery) {
        Long deptId = MapUtils.getLong(pageQuery, "deptId");
        //高级查询部门刷选
        if (null != deptId) {
            //部门下所有子部门
            List<String> ids = new ArrayList<>();
            List<Long> allDeptUnderDeptId = sysDeptServiceFeign.getAllDeptId(deptId);
            allDeptUnderDeptId.forEach(x -> {
                String deptIds = String.valueOf(x);
                ids.add(deptIds);
            });
            pageQuery.put("deptIds", StringUtils.listToString(ids));
        }
        if (isGeneralSeat()) {
            pageQuery.put("userId", getUserId());
        }
        if (isSuperAdmin()) {
            log.debug("用户角色是超级管理员：" + isSuperAdmin());
            return super.findPage(pageQuery);
        }
        //如果没有刷选部门过滤条件
        if (null == deptId) {
            //获取当前用户的部门以及子部门
            List<Long> userAllDeptId = sysUserDeptServiceFeign.getUserDeptIdList(getUserId());
            List<String> deptIds = new ArrayList<>();
            userAllDeptId.forEach(x -> {
                String ids = String.valueOf(x);
                deptIds.add(ids);
            });
            pageQuery.put("deptIds", StringUtils.listToString(deptIds));
        }
        return super.findPage(pageQuery);
    }
}
