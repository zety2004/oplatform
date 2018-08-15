package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.SCApplyMapper;
import com.hklk.oplatform.dao.inter.SSyllabusMapper;
import com.hklk.oplatform.dao.inter.TeacherMessageMapper;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SSyllabus;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.service.SSyllabusService;
import com.hklk.oplatform.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SSyllabusServiceServiceImpl implements SSyllabusService {

    @Autowired
    SSyllabusMapper sSyllabusMapper;
    @Autowired
    SCApplyMapper scApplyMapper;
    @Autowired
    TeacherMessageMapper teacherMessageMapper;

    @Override
    public int selectCountStudentNumBySCId(Integer scaId) {
        return sSyllabusMapper.selectCountStudentNumBySCId(scaId);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sSyllabusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBySchoolId(Integer schoolId) {
        return sSyllabusMapper.deleteBySchoolId(schoolId);
    }

    @Override
    public int insertOrUpdateByPrimaryKeySelective(SSyllabus sSyllabus, SCApply scApply) {
        scApplyMapper.updateByPrimaryKeySelective(scApply);
        if (sSyllabus.getId() != null) {
            return sSyllabusMapper.updateByPrimaryKeySelective(sSyllabus);
        } else {
            return sSyllabusMapper.insertSelective(sSyllabus);
        }
    }

    @Override
    public List<Map<String, String>> queryMapByWeekType(Integer weekType, Integer schoolId) {
        Map<String, Object> param = new HashMap<>();
        param.put("weekType", weekType);
        param.put("schoolId", schoolId);
        return sSyllabusMapper.queryMapByWeekType(param);
    }

    @Override
    public List<Map<String, String>> queryMapByTimeType(Integer timeType, Integer schoolId) {
        Map<String, Object> param = new HashMap<>();
        param.put("timeType", timeType);
        param.put("schoolId", schoolId);
        return sSyllabusMapper.queryMapByTimeType(param);
    }

    @Override
    public List<Map<String, String>> queryMapByTimeTypeForEnd(Integer weekType, Integer schoolId) {
        Map<String, Object> param = new HashMap<>();
        param.put("weekType", weekType);
        param.put("schoolId", schoolId);
        return sSyllabusMapper.queryMapByTimeTypeForEnd(param);
    }

    @Override
    public List<Map<String, String>> querySyllabusByTeacher(Integer weekType, Integer teacherId) {
        Map<String, Object> param = new HashMap<>();
        param.put("weekType", weekType);
        param.put("teacherId", teacherId);
        return sSyllabusMapper.querySyllabusByTeacher(param);
    }

    @Override
    public int selectMaxTimeType(Integer schoolId) {
        return sSyllabusMapper.selectMaxTimeType(schoolId);
    }

    @Override
    public int delAndbatchSaveSyllabus(String param, Integer schoolId) {
        this.deleteBySchoolId(schoolId);
        Document doc = Jsoup.parse(param);
        Elements tds = doc.select("table").select("tbody").select("tr").select("td");
        int x = 0;
        StringBuffer classTime = new StringBuffer();
        for (Element td : tds) {
            switch (x) {
                case 0:
                    for (Element span : td.select("span")) {
                        classTime.append(span.html()).append("-");
                    }
                    classTime = classTime.delete(classTime.length() - 1, classTime.length());
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    Elements divs = td.select("div.class-item-box");
                    if (divs.size() == 0) {
                        break;
                    }
                    int y = 0;
                    for (Element div : divs) {
                        Integer scaId = Integer.valueOf(div.select("i").attr("data-id"));
                        SSyllabus sSyllabus = new SSyllabus();
                        sSyllabus.setScaId(scaId);
                        sSyllabus.setSchoolId(schoolId);
                        sSyllabus.setTimeType(y);
                        sSyllabus.setWeekType(x);
                        sSyllabus.setClassTime(classTime.toString());

                        SCApply scApply = new SCApply();
                        scApply.setId(scaId);
                        scApply.setClassPlace(div.select("label.place").html());
                        scApply.setBeginOfSelectTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-selstart"), "yyyy-MM-dd"));
                        scApply.setEndOfSelectTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-selend"), "yyyy-MM-dd"));
                        scApply.setStatus(3);
                        scApply.setIsFineQuality(Integer.valueOf(div.select("h6.clear").attr("data-fine")));
                        scApply.setCurrStartTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-starttime"), "yyyy-MM-dd"));
                        this.insertOrUpdateByPrimaryKeySelective(sSyllabus, scApply);

                        TeacherMessage teacherMessage = new TeacherMessage();
                        teacherMessage.setTeacherId(Integer.parseInt(div.select("div.class-content-box").select("div.teacher").attr("data-teacherid")));
                        teacherMessage.setMessage("您申报的 " + div.select("span.setName").html() + " 课程已经成功排课！");
                        teacherMessageMapper.insertSelective(teacherMessage);
                        y++;
                    }
                    break;
                default:
                    System.out.println(1111);
                    break;
            }
            x++;
        }

        return 1;
    }
}
