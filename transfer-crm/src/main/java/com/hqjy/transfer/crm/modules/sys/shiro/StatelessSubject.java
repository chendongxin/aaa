package com.hqjy.transfer.crm.modules.sys.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 自定义SubjectFactory,禁用session
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:04
 */
public class StatelessSubject extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
