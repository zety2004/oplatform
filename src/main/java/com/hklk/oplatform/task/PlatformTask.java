package com.hklk.oplatform.task;

import com.aliyuncs.exceptions.ClientException;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.vo.StudentPay;
import com.hklk.oplatform.service.SCApplyService;
import com.hklk.oplatform.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oplatform
 * 2018/9/18 17:43
 * 扫描待开课课程
 *
 * @author 曹良峰
 * @since
 **/

@Component
public class PlatformTask {
    @Autowired
    SCApplyService scApplyService;

    @Scheduled(cron = "0 0 8 * * ? ") // 每天早上8点执行一次
    public void taskCurriculumApply() {
        List<Map<String, Object>> result = scApplyService.queryCurriculumListForTask();
        result.forEach(obj -> {
            SCApply scApply = new SCApply();
            scApply.setId((int) obj.get("id"));
            scApply.setIsOpenClass(1);
            Map<String, String> teacherSmsParam = new HashMap<>();
            teacherSmsParam.put("attr1", obj.get("curriculumName").toString());
            teacherSmsParam.put("attr2", obj.get("currStartTime").toString());
            try {
                SmsUtil.sendSms(obj.get("phone").toString(), "SMS_144450668", teacherSmsParam);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            scApplyService.updateByPrimaryKeySelective(scApply);
            List<StudentPay> studentPays = scApplyService.queryStudentBySCAId((int) obj.get("id"));
            studentPays.forEach(studentPay -> {
                try {
                    Map<String, String> parentSmsParam = new HashMap<>();
                    parentSmsParam.put("attr1", studentPay.getFullName());
                    parentSmsParam.put("attr2", obj.get("curriculumName").toString());
                    parentSmsParam.put("attr3", obj.get("currStartTime").toString());
                    SmsUtil.sendSms(studentPay.getParentPhone(), "SMS_144450674", parentSmsParam);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            });

        });

        System.out.println("使用SpringMVC框架配置定时任务");
    }
}
