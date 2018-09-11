package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Dic;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public interface DicService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Dic record);

    List<Map<String, Object>> queryForList(String typeCode);

    int updateByPrimaryKeySelective(Dic record);

    public static void main(String[] args) {
        List<Map<String, Object>> allList = new ArrayList<>();
        Map<String, Object> temp1 = new HashMap<>();
        temp1.put("temp1", 1);
        allList.add(temp1);
        Map<String, Object> temp2 = new HashMap<>();
        temp2.put("temp2", 1);
        allList.add(temp2);
        Map<String, Object> temp3 = new HashMap<>();
        temp3.put("temp3", 1);
        allList.add(temp3);
        Map<String, Object> temp4 = new HashMap<>();
        temp4.put("temp4", 1);
        allList.add(temp4);

        IntStream.range(1, allList.size()).forEach(index -> {
            if (index < 2) {
                allList.get(index - 1).put("hot", 1);
            } else {
                allList.get(index - 1).put("hot", 0);
            }
        });
        System.out.println(allList);
    }
}
