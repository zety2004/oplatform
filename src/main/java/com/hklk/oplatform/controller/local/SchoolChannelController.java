package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Channel;
import com.hklk.oplatform.entity.table.ChannelCurriculum;
import com.hklk.oplatform.entity.table.ChannelSchool;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.SchoolChannelService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 运营登陆管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/channel")
@Controller
public class SchoolChannelController extends BaseController {
    @Autowired
    SchoolChannelService schoolChannelService;

    /**
     * 2018/9/28 16:45
     * 添加修改渠道
     *
     * @param channel
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateChannel")
    public String insertOrUpdateChannel(Channel channel, String curriculumIds) {
        if (channel.getId() == null) {
            String sign = ToolUtil.createId(32);
            channel.setSign(sign);
            schoolChannelService.insertChannel(channel);
            if (curriculumIds != null) {
                insertChannelCurriculumBySign(sign, curriculumIds);
            }
        } else {
            schoolChannelService.updateChannel(channel);
            if (curriculumIds != null) {
                insertChannelCurriculumById(channel.getId(), curriculumIds);
            }
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    private void insertChannelCurriculumBySign(String sign, String curriculumIds) {
        Integer channelId = schoolChannelService.queryChannelBySign(sign);
        Arrays.asList(curriculumIds.split(",")).forEach(curriculumId -> {
            ChannelCurriculum channelCurriculum = new ChannelCurriculum();
            channelCurriculum.setCurriculumId(Integer.valueOf(curriculumId));
            channelCurriculum.setChannelId(channelId);
            schoolChannelService.insertChannelCurriculum(channelCurriculum);
        });
    }

    private void insertChannelCurriculumById(Integer id, String curriculumIds) {
        Arrays.asList(curriculumIds.split(",")).forEach(curriculumId -> {
            ChannelCurriculum channelCurriculum = new ChannelCurriculum();
            channelCurriculum.setCurriculumId(Integer.valueOf(curriculumId));
            channelCurriculum.setChannelId(id);
            schoolChannelService.insertChannelCurriculum(channelCurriculum);
        });
    }

    /**
     * 2018/9/28 16:46
     * 添加学校渠道
     *
     * @param channelSchool
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addChannelSchool")
    public String insertChannelSchool(ChannelSchool channelSchool) {
        schoolChannelService.insertChannelSchool(channelSchool);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/9/28 16:49
     * 添加渠道课程
     *
     * @param channelId
     * @param curriculumIds
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addChannelCurriculum")
    public String insertChannelCurriculum(int channelId, String curriculumIds) {
        if (curriculumIds == null) {
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            Arrays.asList(curriculumIds.split(",")).forEach(curriculumId -> {
                ChannelCurriculum channelCurriculum = new ChannelCurriculum();
                channelCurriculum.setCurriculumId(Integer.valueOf(curriculumId));
                channelCurriculum.setChannelId(channelId);
                schoolChannelService.insertChannelCurriculum(channelCurriculum);
            });
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/9/28 16:59
     * 查询渠道列表
     *
     * @param
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryChannels")
    public String queryChannels() {
        List<Map<String, Object>> result = schoolChannelService.queryChannels();
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    /**
     * 2018/9/28 17:01
     * 查询渠道下的课程
     *
     * @param id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryChannelCurriculumById")
    public String queryChannelCurriculumById(Integer id) {
        List<Map<String, Object>> result = schoolChannelService.queryChannelCurriculumById(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    /**
     * 2018/9/28 17:03
     * 查询所有课程
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumAll")
    public String queryCurriculumAll() {
        List<Map<String, Object>> result = schoolChannelService.queryCurriculumAll();
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

}
