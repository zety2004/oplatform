package com.hklk.oplatform.util;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

public final class ToolUtil {

    private static String dateFormate = "yyyy-MM-dd HH:mm:ss";

    // map value降序排序
    public static ArrayList<Entry<String, Integer>> sortMap(Map map) {

        List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());

        Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });

        return (ArrayList<Entry<String, Integer>>) entries;
    }

    public static String buildResultStr(int statusCode, String statusMessage) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultCode", statusCode);
        map.put("resultMsg", statusMessage);
        return JsonUtil.toJson(map, dateFormate);
    }

    public static String buildResultStr(int statusCode, String statusMessage, Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultCode", statusCode);
        map.put("resultMsg", statusMessage);
        map.put("resultData", obj);
        return JsonUtil.toJson(map, dateFormate);
    }

    public static String buildXhEditorStr(String statusCode, String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("err", statusCode);
        map.put("msg", url);
        return JsonUtil.toJson(map, dateFormate);
    }

    //创建唯一标识
    public static String createId() {
        return createId(8);
    }

    public static final String createId(int len) {

        String uuid = java.util.UUID.randomUUID().toString();

        uuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);

        if (len > 0) {

            return uuid.substring(0, len);

        } else {

            return uuid;

        }

    }


    /**
     * 格式化double类型数据 *.00
     *
     * @param decimal
     * @return
     */
    public static String formatDouble(double decimal) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(decimal);
    }


    /**
     * String类型小数点后去0
     *
     * @param number
     * @return
     */
    public static String trimNumber(String number) {
        if (null != number && -1 != number.indexOf(".")) {
            String aa = number.substring(number.indexOf(".") + 1);
            if (aa.length() == 1) {
                if ("0".equals(aa)) {
                    number = number.substring(0, number.indexOf("."));
                }
            } else {
                if ("00".equals(number.substring(number.indexOf(".") + 1, number.indexOf(".") + 3))) {
                    number = number.substring(0, number.indexOf("."));
                } else if ("0".equals(number.substring(number.indexOf(".") + 2, number.indexOf(".") + 3))) {
                    number = number.substring(0, number.indexOf(".") + 2);
                }
            }
        }
        return number;
    }

    /**
     * 验证翻页的页码
     *
     * @param pageSizeStr
     * @param pageNumStr
     * @param totalCount
     * @return
     */
    public static Map<String, Integer> validataPageNum(String pageSizeStr, String pageNumStr, int totalCount) {

        Integer pageSize = null;
        Integer pageNum = null;

        Map<String, Integer> map = new HashMap<String, Integer>();

        if (StringUtils.isBlank(pageSizeStr) || StringUtils.isBlank(pageNumStr)) {
            pageNum = 1;
            pageSize = 10;

        } else {

            pageNum = Integer.parseInt(trimNumber(pageNumStr));
            pageSize = Integer.parseInt(trimNumber(pageSizeStr));

            if (pageNum < 1) {
                pageNum = 1;
            }

            if (pageSize < 1) {
                pageSize = 10;
            }

            String numStr = ToolUtil.trimNumber(String.valueOf(Math.floor(totalCount / pageSize)));

            int num = 0;
            if (totalCount % pageSize > 0) {
                num = Integer.parseInt(numStr) + 1;
            } else {

                num = Integer.parseInt(numStr);
                if (num == 0) {
                    num = 1;
                }
            }

            if (pageNum > num) {
                return null;
            }
        }

        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return map;
    }


    /**
     * 发送http post 请求
     *
     * @param urlParam
     * @param urlParam ，obj，token
     * @return
     */
    public static String sendPostData(String urlParam, Object obj, String token) {

        String paramJsonStr = null;
        if (null != obj) {
            paramJsonStr = JsonUtil.toJson(obj);
        }

        String resultStr = HttpRequestUtils.httpPost(urlParam, paramJsonStr, token);

        return resultStr;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
