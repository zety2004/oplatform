package com.hklk.oplatform.entity;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SSyllabus;
import com.hklk.oplatform.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaseBean {

    public static void main(String[] args) {
        String param = "\n" +
                "                            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;\">\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th width=\"10%\">\n" +
                "\n" +
                "                                    </th>\n" +
                "                                    <th width=\"18%\">\n" +
                "                                        星期一\n" +
                "                                    </th>\n" +
                "                                    <th width=\"18%\">\n" +
                "                                        星期二\n" +
                "                                    </th>\n" +
                "                                    <th width=\"18%\">\n" +
                "                                        星期三\n" +
                "                                    </th>\n" +
                "                                    <th width=\"18%\">\n" +
                "                                        星期四\n" +
                "                                     </th>\n" +
                "                                    <th width=\"18%\">\n" +
                "                                        星期五\n" +
                "                                    </th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-set-box\">\n" +
                "                                            三点半课堂<br>\n" +
                "                                            <span id=\"classS\">15:30</span>-<span id=\"classE\">16:30</span><br>\n" +
                "                                            <div class=\"set-time\">点击<i id=\"setTime\">设置</i>时间</div>\n" +
                "                                        </div>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-order-list ui-droppable\" data-week=\"星期一\" id=\"Monday\">\n" +
                "\n" +
                "                                        <div class=\"class-item-box setted15\"><h6 class=\"cf\"><span class=\"setName\">佛挡杀佛</span><i data-id=\"15\" data-set=\"0\"></i></h6><div class=\"class-content-box\" data-selstart=\"2018-06-20\" data-selend=\"2018-06-25\" data-starttime=\"2018-06-29\"><div>适用年级：<label class=\"grade\"></label></div><div class=\"teacher5\">任课老师：<label class=\"teacherName\">周杰伦</label></div><div>上课地点：<label class=\"place\">上课地点1</label></div><div>上课人数：<label class=\"maxNum\">35</label>人</div><div><label class=\"set\">（每周一节）</label></div></div></div></div>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-order-list ui-droppable\" data-week=\"星期二\" id=\"Tuesday\">\n" +
                "\n" +
                "                                        <div class=\"class-item-box setted2\"><h6 class=\"cf\"><span class=\"setName\">lesson1</span><i data-id=\"2\" data-set=\"0\"></i></h6><div class=\"class-content-box\" data-selstart=\"2018-06-13\" data-selend=\"2018-06-14\" data-starttime=\"2018-06-22\"><div>适用年级：<label class=\"grade\">一年级、二年级、三年级、九年级</label></div><div class=\"teacher2\">任课老师：<label class=\"teacherName\">罗勇豪</label></div><div>上课地点：<label class=\"place\">一年级三班</label></div><div>上课人数：<label class=\"maxNum\">30</label>人</div><div><label class=\"set\">（每周一节）</label></div></div></div></div>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-order-list ui-droppable\" data-week=\"星期三\" id=\"Wednesday\">\n" +
                "                                        </div>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-order-list ui-droppable\" data-week=\"星期四\" id=\"Thursday\">\n" +
                "                                        </div>\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"class-order-list ui-droppable\" data-week=\"星期五\" id=\"Friday\">\n" +
                "                                        </div>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        ";
        Document doc = Jsoup.parse(param);
        Elements tds = doc.select("table").select("tbody").select("tr").select("td");
        int x = 0;
        StringBuffer classTime = new StringBuffer();
        for (Element td : tds) {
            classTime.delete(0, classTime.length());
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
                    Elements divs = td.select("div.class-order-list");
                    int y = 0;
                    for (Element div : divs) {
                        if (div.select("div.class-item-box").size() == 0) {
                            y++;
                            break;
                        }
                        Integer scaId = Integer.valueOf(div.select("i").attr("data-id"));
                        SSyllabus sSyllabus = new SSyllabus();
                        sSyllabus.setScaId(scaId);
                        sSyllabus.setSchoolId(1);
                        sSyllabus.setTimeType(y);
                        sSyllabus.setWeekType(x);
                        sSyllabus.setClassTime(classTime.toString());

                        SCApply scApply = new SCApply();
                        scApply.setId(scaId);
                        scApply.setClassPlace(div.select("label.place").html());
                        scApply.setBeginOfSelectTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-selstart"), "yyyy-MM-dd"));
                        scApply.setEndOfSelectTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-selend"), "yyyy-MM-dd"));
                        scApply.setStatus(3);
                        scApply.setCurrStartTime(DateUtil.string2Date(div.select("div.class-content-box").attr("data-starttime"), "yyyy-MM-dd"));
                        y++;
                    }
                    break;
                default:
                    break;
            }
            x++;
        }
    }
}
