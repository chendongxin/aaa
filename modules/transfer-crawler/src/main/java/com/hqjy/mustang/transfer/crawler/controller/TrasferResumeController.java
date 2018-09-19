package com.hqjy.mustang.transfer.crawler.controller;

import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crawler.service.TrasferResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:38
 */
@RestController
@RequestMapping("/resume")
@Api(tags = "简历管理", description = "TrasferResumeController")
public class TrasferResumeController {

    @Autowired
    private TrasferResumeService trasferResumeService;

    /**
     * 分页查询简历列表
     */
    @ApiOperation(value = " 分页查询简历列表", notes = "{\n" +
            " {\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[现居住地] address\": \"西安 未央区\",\n" +
            "                \"[年龄] age\": \"22\",\n" +
            "                \"[出生日期] birthday\": \"2018-09-19 00:00:00\",\n" +
            "                \"[公司ID] companyId\": 1,\n" +
            "                \"[公司名称] companyName\": \"恒企教育\",\n" +
            "                \"[收集邮箱] createEmail\": \"hr12@baidanwang.cn\",\n" +
            "                \"[收集邮箱ID] createEmailId\": 1,\n" +
            "                \"[收集时间] createTime\": \"2018-09-19 18:03:07\",\n" +
            "                \"[部门ID] deptId\": 8000,\n" +
            "                \"[部门名称] deptName\": \"野马部门\",\n" +
            "                \"[学历ID] educationId\": 1,\n" +
            "                \"[学历] educationName\": \"大专\",\n" +
            "                \"[邮箱] email\": \"hejinyo@gmail.com\",\n" +
            "                \"[邮件标题] emailTitle\": \"(58.com)应聘贵公司高薪python开发助理-西安-胡小丽\",\n" +
            "                \"[推广人ID] genUserId\": 1,\n" +
            "                \"[推广人名称] genUserName\": \"admin\",\n" +
            "                \"[毕业时间] graduateDate\": \"2018-09-19 18:09:41\",\n" +
            "                \"[简历编号] id\": 63,\n" +
            "                \"[专业名称] major\": \"金融学\",\n" +
            "                \"[姓名] name\": \"胡小丽\",\n" +
            "                \"[电话] phone\": \"15392546747\",\n" +
            "                \"[应聘职位] positionApplied\": \"数据库管理/DBA 1-2年\",\n" +
            "                \"[赛道ID] proId\": 1,\n" +
            "                \"[赛道名称] proName\": \"IT赛道\",\n" +
            "                \"[简历详情] resumeDetail\": \"<html>\\r\\n <head>\\r\\n  <style type=\\\"text/css\\\">\\r\\n\\t\\t</style> \\r\\n </head>\\r\\n <body style=\\\"margin:0px;padding:0;\\\"> \\r\\n  <div style=\\\"background:url(http://pic2.58.com/ui7/job/resume/resume_prebg.gif) repeat;_background-attachment:fixed;font-size:12px;font-family:arial,'宋体';\\\"> \\r\\n   <div style=\\\"width:907px;margin:0 auto;background:none;color:#999;height:50px;overflow:hidden;position:relative;\\\"> \\r\\n    <a style=\\\"margin-left:4px;float:left;\\\" href=\\\"http://www.58.com/\\\"><img style=\\\"border:0;\\\" alt=\\\"中文最大生活信息门户\\\" src=\\\"http://pic2.58.com/ui7/job/resume/rp_logo.gif\\\" /></a> \\r\\n    <span style=\\\"float:right;color:#404040;margin-top:30px;\\\"><b>应聘职位</b>：<a style=\\\"color:#2255DD;text-decoration:underline;\\\" href=\\\"http://xa.58.com/tech/34104447371567x.shtml\\\" target=\\\"_blank\\\">高薪python开发助理</a>。请通过简历中的电话或邮件联系我（请勿直接回复邮件）。 更新时间：2018-09-18</span> \\r\\n   </div> \\r\\n   <div style=\\\"width:907px;margin:0 auto;\\\">\\r\\n    <div style=\\\"padding:0;background:none;\\\">\\r\\n     <div style=\\\"padding:0;\\\"> \\r\\n      <div style=\\\"background:url(http://pic2.58.com/ui7/job/resume/rpbgt.gif) no-repeat;padding:0 30px;height:10px;overflow:hidden;zoom:1;\\\"></div> \\r\\n      <div style=\\\"background:url(http://pic2.58.com/ui7/job/resume/rpbgc.gif) repeat-y;padding:30px;\\\"> \\r\\n       <div style=\\\"overflow:hidden;zoom:1;\\\">\\r\\n        <h3 style=\\\"margin:0;padding:0;font-size:22px;line-height:22px;padding-bottom:10px;font-family:'微软雅黑';font-weight:normal;\\\">胡小丽<span style=\\\"font-size:16px;color:#343434;font-family:arial,'宋体';padding:0 10px;\\\">（女，22岁）</span></h3>\\r\\n        <div style=\\\"padding:11px 0 0;border:none;\\\">\\r\\n         <ul style=\\\"margin:0;padding:0;list-style:none;width:680px;float:left;overflow:hidden;zoom:1;*padding-bottom:5px;\\\"> \\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;border-right:1px solid #eaeaea;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">大专</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;border-right:1px solid #eaeaea;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">1-2年工作经验</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;border-right:1px solid #eaeaea;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">籍贯</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">现居住</li>\\r\\n         </ul>\\r\\n        </div>\\r\\n       </div> \\r\\n       <div style=\\\"margin:0 0 20px;\\\"> \\r\\n        <div class=\\\"infoview\\\" style=\\\"font-size:12px;color:#404040;overflow:hidden;zoom:1;padding:11px 0 0;border:none;\\\">\\r\\n         <ul class=\\\"mybright\\\" style=\\\"margin:0;padding:0;list-style:none;overflow:hidden;zoom:1;font-size:12px;clear:both;\\\">\\r\\n          <li class=\\\"bkc3\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;height:22px;line-height:22px;margin:0 4px 4px 0;padding:0 10px;color:#fff;white-space:nowrap;zoom:1;background:#C9A497;border:solid #B19085;border-width:0 1px 1px 0;\\\"><span class=\\\"fl\\\" style=\\\"float:left;\\\">沟通力强</span></li>\\r\\n          <li class=\\\"bkc3\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;height:22px;line-height:22px;margin:0 4px 4px 0;padding:0 10px;color:#fff;white-space:nowrap;zoom:1;background:#C9A497;border:solid #B19085;border-width:0 1px 1px 0;\\\"><span class=\\\"fl\\\" style=\\\"float:left;\\\">执行力强</span></li>\\r\\n          <li class=\\\"bkc3\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;height:22px;line-height:22px;margin:0 4px 4px 0;padding:0 10px;color:#fff;white-space:nowrap;zoom:1;background:#C9A497;border:solid #B19085;border-width:0 1px 1px 0;\\\"><span class=\\\"fl\\\" style=\\\"float:left;\\\">学习力强</span></li>\\r\\n          <li class=\\\"bkc3\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;height:22px;line-height:22px;margin:0 4px 4px 0;padding:0 10px;color:#fff;white-space:nowrap;zoom:1;background:#C9A497;border:solid #B19085;border-width:0 1px 1px 0;\\\"><span class=\\\"fl\\\" style=\\\"float:left;\\\">诚信正直</span></li>\\r\\n          <li class=\\\"bkc3\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;height:22px;line-height:22px;margin:0 4px 4px 0;padding:0 10px;color:#fff;white-space:nowrap;zoom:1;background:#C9A497;border:solid #B19085;border-width:0 1px 1px 0;\\\"><span class=\\\"fl\\\" style=\\\"float:left;\\\">责任心强</span></li>\\r\\n         </ul>\\r\\n         <div style=\\\"color:#e7e7e7; height:58px; overflow:hidden; position:relative\\\">\\r\\n          1.基础：掌握html、css、html5、css3、ajax 2.前端框架：熟悉bootscript、element、vue等框架 3.熟练的库：jQuery 4.熟练运用Sublime text、pycharm等常用网页设计以及常用小程序的制作软件 4.后台语言：了解python和数据库mysql的使用 5.后台框架：熟练使用flask、django等框架\\r\\n          <a style=\\\"position:absolute;top:5px;left:210px;\\\" href=\\\"https://jianli.58.com/resumedetail/single/3_neysTvdp_EHpTEt5TvtplEDkTemQTEyaTeSknpsVlE6snErsnisf_eZaT6**?utm_source=zhiweituisong&amp;utm_medium=email&amp;utm_campaign=zhiweituisong\\\" target=\\\"_blank\\\"><img border=\\\"0\\\" src=\\\"http://pic2.58.com/ui6/zp/zt/update.gif\\\" /></a>\\r\\n         </div>\\r\\n        </div> \\r\\n        <div style=\\\"position:relative;overflow:hidden;zoom:1;padding:10px 0 0;\\\">\\r\\n         <div>\\r\\n          <div style=\\\"border-top:1px dashed #E0E0E0;border:none;\\\">\\r\\n           <p style=\\\"margin:0;padding:0;font-size:14px;line-height:40px;vertical-align:middle;\\\"> <label style=\\\"color:#666;padding-right:20px;\\\">手机号码：<span style=\\\"color:#E05947;font-size:20px;\\\"><span style=\\\"color:#E05947;font-size:20px;\\\">15392546747</span></span></label> <label style=\\\"color:#666;padding-right:20px;\\\">电子邮箱：<span style=\\\"color:#E05947;font-size:20px;\\\"></span></label> </p>\\r\\n          </div>\\r\\n         </div>\\r\\n        </div> \\r\\n       </div> \\r\\n       <div class=\\\"margin:15px 0 26px;\\\"> \\r\\n        <div style=\\\"overflow:hidden;zoom:1;border-top: 1px solid #E4E4E4;\\\"> \\r\\n         <h3 style=\\\"margin:0;padding:0;font-size:16px;height:18px;line-height:18px;font-weight:bold;color:#404040;/*padding:25px 0 0;*/padding:26px 0 0;\\\">求职意向</h3> \\r\\n        </div> \\r\\n        <div style=\\\"overflow:hidden;font-size:12px;color:#404040;padding:11px 0 0;border:none;\\\"> \\r\\n         <ul style=\\\"margin:0;padding:0;list-style:none;overflow:hidden;zoom:1;*padding-bottom:5px;\\\"> \\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;height:22px;line-height:22px;font-size:14px;margin:5px 10px 5px 0;white-space:nowrap;\\\"><span class=\\\"c66\\\" style=\\\"color:#404040;\\\">期望职位：</span>数据库管理/DBA 1-2年</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;height:22px;line-height:22px;font-size:14px;margin:5px 10px 5px 0;white-space:nowrap;\\\"><span class=\\\"c66\\\" style=\\\"color:#404040;\\\">期望薪资：</span>5000-8000元/月</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;height:22px;line-height:22px;font-size:14px;margin:5px 10px 5px 0;white-space:nowrap;\\\"><span class=\\\"c66\\\" style=\\\"color:#404040;\\\">期望地区：</span>西安 未央区</li> \\r\\n         </ul> \\r\\n        </div> \\r\\n       </div> \\r\\n       <div class=\\\"addcont addexpe\\\" style=\\\"margin: 15px 0 26px;\\\">\\r\\n        <div class=\\\"modtab\\\" style=\\\"overflow:hidden;zoom:1;border-top: 1px solid #E4E4E4;\\\">\\r\\n         <h3 style=\\\"margin:0;padding:0;font-size:16px;height:18px;line-height:18px;font-weight:bold;color:#404040;padding:26px 0 0;\\\">工作经验</h3>\\r\\n        </div>\\r\\n        <div class=\\\"infoview\\\" style=\\\"font-size:12px;color:#404040;overflow:hidden;zoom:1;padding:11px 0 0;border:none;\\\">\\r\\n         <p class=\\\"excomp\\\" style=\\\"margin:0;padding:0;clear:both;padding-top:0;overflow:hidden;zoom:1;color:#404040;line-height:27px;font-size:14px;\\\"><strong>建网科技有限公司</strong></p>\\r\\n         <ul class=\\\"summ\\\" style=\\\"margin:0;padding:0;list-style:none;overflow:hidden;zoom:1;*padding-bottom:5px;\\\">\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;border-right:1px solid #eaeaea;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">2017年8月-2018年8月</li>\\r\\n          <li style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;border-right:1px solid #eaeaea;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">python工程师</li>\\r\\n          <li class=\\\"noborder\\\" style=\\\"margin:0;padding:0;list-style:none;float:left;display:inline;padding-right:10px;margin:5px 10px 5px 0;height:16px;line-height:16px;font-size:14px;white-space:nowrap;\\\">保密</li>\\r\\n         </ul>\\r\\n         <table class=\\\"box_table\\\" style=\\\"width:100%;\\\" cellpadding=\\\"0\\\" cellspacing=\\\"0\\\">\\r\\n          <tbody>\\r\\n           <tr>\\r\\n            <th class=\\\"veraltop\\\" style=\\\"vertical-align:top;width:70px;font-size:14px;font-weight:normal;text-align:right;padding:0;color:#404040;\\\"><span class=\\\"c66\\\" style=\\\"width:70px;text-align:left;float:left;display:inline;padding:4px 0 0;line-height:27px;\\\">工作内容：</span></th>\\r\\n            <td style=\\\"vertical-align:top;padding:0;color:#404040;\\\"><p style=\\\"margin:0;padding:0;clear:both;padding:4px 0 0;overflow:hidden;zoom:1;font-size:14px;line-height:27px;\\\"></p></td>\\r\\n           </tr>\\r\\n          </tbody>\\r\\n         </table>\\r\\n        </div>\\r\\n       </div> \\r\\n       <p style=\\\"margin:0;padding:0;\\\" align=\\\"center\\\"><img border=\\\"0\\\" src=\\\"http://jianli.58.com/ajax/eusermsg/?id=7203614172022088\\\" width=\\\"0\\\" height=\\\"0\\\" /></p> \\r\\n      </div> \\r\\n      <div style=\\\"background:url(http://pic2.58.com/ui7/job/resume/rpbgb.gif) no-repeat;padding:0 30px;height:10px;overflow:hidden;zoom:1;\\\"></div> \\r\\n     </div>\\r\\n     <div style=\\\"height:0px; font-size:0;clear:both; overflow:hidden\\\"></div>\\r\\n    </div>\\r\\n   </div> \\r\\n   <div style=\\\"width:907px;margin:0 auto;background:none;color:#999;\\\">\\r\\n    <p style=\\\"margin:0;padding:0;text-align:center;line-height:24px;padding:10px 0;font-size:12px;\\\">该简历来自 58同城(www.58.com)</p>\\r\\n   </div> \\r\\n  </div>  \\r\\n  <img id=\\\"1537235264246_Easeye_isOpen\\\" src=\\\"http://linktrace.easeyedelivery.com/MailLink/LogoImageHandler.jpg?EASEYEUID=N132-GIYTQNZZ-GE4DAOJRHAYDSNBXGQZTAMZRHE3TG-NBZDCMSAMJQWSZDBNZ3WC3THFZRW4-AC0236B9&amp;\\\" height=\\\"1\\\" width=\\\"1\\\" />\\r\\n </body>\\r\\n</html>\\r\\n\\r\\n\",\n" +
            "                \"[期望薪资] salary\": \"5000-8000元/月\",\n" +
            "                \"[毕业学校] school\": \"清华大学\",\n" +
            "                \"[发送邮箱] sendMail\": \"jianli@zp.58.com\",\n" +
            "                \"[发送时间] sendTime\": \"2018-09-18 09:47:44\",\n" +
            "                \"[性别] sex\": 2,\n" +
            "                \"[来源ID] sourceId\": 0,\n" +
            "                \"[来源名称] sourceName\": \"默认来源\",\n" +
            "                \"[发送状态] status\": 0,\n" +
            "                \"[工作经验] workExperience\": \"1-2年工作经验\",\n" +
            "                \"[工作地点] workingPlace\": \"工作地点或者城市\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 10,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 1,\n" +
            "        \"totalPage\": 1\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        return R.ok(new PageInfo<>(trasferResumeService.findPage(PageQuery.build(pageParam, queryParam))));
    }

}
