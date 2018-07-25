package com.hklk.oplatform.util;

import com.hklk.oplatform.provider.PasswordProvider;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * oplatform
 * 2018/7/23 14:08
 * 微信支付工具类
 *
 * @author 曹良峰
 * @since
 **/
public class PayUtil {
    public static final String TIME = "yyyyMMddHHmmss";


    /**
     * 创建微信交易对象
     */
    public static SortedMap<Object, Object> getWXPrePayID() {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", PropUtil.getProperty("wxAppid"));
        parameters.put("mch_id", PropUtil.getProperty("mchId"));
        parameters.put("nonce_str", PayUtil.CreateNoncestr());
        parameters.put("fee_type", "CNY");
        parameters.put("notify_url", PropUtil.getProperty("notifyUrl"));
        parameters.put("trade_type", "JSAPI");
        return parameters;
    }

    public static Map<String, String> xmlStr2Map(String xmlStr) {
        Map<String, String> map = new HashMap<>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xmlStr);
            Element root = doc.getRootElement();
            List children = root.elements();
            if (children != null && children.size() > 0) {
                for (int i = 0; i < children.size(); i++) {
                    Element child = (Element) children.get(i);
                    map.put(child.getName(), child.getTextTrim());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 再次签名，支付
     */
    public static SortedMap<Object, Object> startWXPay(String result) {
        try {
            Map<String, String> map = xmlStr2Map(result);
            SortedMap<Object, Object> parameterMap = new TreeMap<>();
            parameterMap.put("appid", PropUtil.getProperty("wxAppid"));
            parameterMap.put("package", "prepay_id=" + map.get("prepay_id"));
            parameterMap.put("noncestr", PayUtil.CreateNoncestr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap.put("timestamp",
                    Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            String sign = PayUtil.createSign(parameterMap);
            parameterMap.put("sign", sign);
            return parameterMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建随机数
     *
     * @return
     */
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(SortedMap<Object, Object> packageParams) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + PropUtil.getProperty("WxPay.key"));
        // 算出摘要
        String mysign = PasswordProvider.encrypt(sb.toString()).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
        // System.out.println(tenpaySign + " " + mysign);
        return tenpaySign.equals(mysign);
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：创建sign签名
     */
    public static String createSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + PropUtil.getProperty("wxKey"));
        String sign = PasswordProvider.encrypt(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     * @Description：返回给微信的参数
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            // log.error("连接超时：{}", ce);
        } catch (Exception e) {
            // log.error("https请求异常：{}", e);
        }
        return null;
    }

    /**
     * 接收微信的异步通知
     *
     * @throws IOException
     */
    public static String reciverWx(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    /**
     * 产生num位的随机数
     *
     * @return
     */
    public static String getRandByNum(int num) {
        String length = "1";
        for (int i = 0; i < num; i++) {
            length += "0";
        }
        Random rad = new Random();
        String result = rad.nextInt(Integer.parseInt(length)) + "";
        if (result.length() != num) {
            return getRandByNum(num);
        }
        return result;
    }

    /**
     * 返回当前时间字符串
     *
     * @return yyyyMMddHHmmss
     */
    public static String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME);
        return sdf.format(new Date());
    }
}
