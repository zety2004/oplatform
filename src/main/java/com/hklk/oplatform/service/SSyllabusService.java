package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SSyllabus;

import java.util.List;
import java.util.Map;

public interface SSyllabusService {
    /**
     * @author 曹良峰
     * @Description 删除课程表
     * @Date 18:45 2018/5/29
     * @Param [id]
     * @Return int
     **/
    int deleteByPrimaryKey(Integer id);

    int deleteBySchoolId(Integer id);

    int insertOrUpdateByPrimaryKeySelective(SSyllabus sSyllabus, SCApply scApply);

    int delAndbatchSaveSyllabus(String param, Integer schoolId);

    int selectCountStudentNumBySCId(Integer scaId);

    List<Map<String, String>> queryMapByWeekType(Integer weekType, Integer schoolId);

    List<Map<String, String>> queryMapByTimeType(Integer timeType, Integer schoolId);

    List<Map<String, String>> queryMapByTimeTypeForEnd(Integer weekType, Integer schoolId);

    List<Map<String, String>> querySyllabusByTeacher(Integer weekType, Integer teacherId);

    int selectMaxTimeType(Integer schoolId);
}
