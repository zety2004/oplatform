package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SSyllabus;

import java.util.List;
import java.util.Map;

public interface SSyllabusMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteBySchoolId(Integer schoolId);

    int insert(SSyllabus record);

    int insertSelective(SSyllabus record);

    SSyllabus selectByPrimaryKey(Integer id);

    int selectCountStudentNumBySCId(Integer scaId);

    int updateByPrimaryKeySelective(SSyllabus record);

    int updateByPrimaryKey(SSyllabus record);

    List<Map<String, String>> queryMapByWeekType(Map<String, Object> param);

    List<Map<String, String>> queryMapByTimeType(Map<String, Object> param);

    List<Map<String, String>> queryMapByTimeTypeForEnd(Map<String, Object> param);

    List<Map<String, String>> querySyllabusByTeacher(Map<String, Object> param);

    int selectMaxTimeType(Integer schoolId);
}