package com.hklk.oplatform.util;

import com.hklk.oplatform.provider.PasswordProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
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
    public static Map<String, Object> getWXPrePayID() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("appid", PropUtil.getProperty("wxAppid"));
        parameters.put("mch_id", PropUtil.getProperty("mchId"));
        parameters.put("nonce_str", PayUtil.CreateNoncestr());
        parameters.put("fee_type", "CNY");
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
    public static Map<String, Object> startWXPay(String result) {
        try {
            Map<String, String> map = xmlStr2Map(result);
            Map<String, Object> parameterMap = new TreeMap<>();
            parameterMap.put("appId", PropUtil.getProperty("wxAppid"));
            parameterMap.put("package", "prepay_id=" + map.get("prepay_id"));
            parameterMap.put("nonceStr", PayUtil.CreateNoncestr());
            parameterMap.put("signType", "MD5");
            parameterMap.put("timeStamp", System.currentTimeMillis() / 1000);
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
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(SortedMap<Object, Object> packageParams) throws Exception {
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
        sb.append("key=" + PropUtil.getProperty("wxKey"));
        // 算出摘要
        String mysign = MD5(sb.toString()).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }


    public static String getSign(Map<String, Object> parameters) {

        String result = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(parameters.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).compareTo(o2.getKey());
                }
            });
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    Object val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }
            sb.append("key=" + PropUtil.getProperty("wxKey"));
            result = sb.toString();
            result = MD5(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    /**
     * @param parameters 请求参数
     * @return
     * @Description：创建sign签名
     */
    public static String createSign(Map<String, Object> parameters) throws Exception {

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(parameters.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
        // 构造签名键值对的格式
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> item : infoIds) {
            if (item.getKey() != null || item.getKey() != "") {
                String key = item.getKey();
                Object val = item.getValue();
                if (!(val == "" || val == null)) {
                    sb.append(key + "=" + val + "&");
                }
            }
        }
        sb.append("key=" + PropUtil.getProperty("wxKey"));
        String sign = MD5(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(Map<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = String.valueOf(entry.getValue());
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


    /**
     * 退款
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String out_trade_no = "";   //退款订单
        int all_total_fee = 100;    //订单金额(这里是按分来计算的)
        int refund_fee = 0;      //退款金额(这里是按分来计算的)
        String appid = "";   //微信小程序--》“开发者ID”
        String mch_id = "";   //商户号，将该值赋值给partner
        String key = "";   //微信支付商户平台登录）--》“API安全”--》“API密钥”--“设置密钥”（设置之后的那个值就是partnerkey，32位）

        Map<String, Object> packageParams = new HashMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("op_user_id", mch_id);
        packageParams.put("nonce_str", PayUtil.CreateNoncestr());
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("out_refund_no", PayUtil.CreateNoncestr());
        packageParams.put("total_fee", String.valueOf(all_total_fee));
        packageParams.put("refund_fee", String.valueOf(refund_fee));
        String sign = PayUtil.createSign(packageParams);
        packageParams.put("sign", sign);

        String XML = PayUtil.getRequestXml(packageParams);

        String result = PayUtil.httpsRequest("https://api.mch.weixin.qq.com/secapi/pay/refund", "POST", XML);
        Map<String, String> refundmap = xmlStr2Map(result);
        if (refundmap.get("return_code").equals("SUCCESS")) {
            if (refundmap.get("result_code").equals("FAIL")) {
                System.out.println("退款失败:原因" + refundmap.get("err_code_des"));
            } else {
                System.out.println("退款成功");
            }
        } else {
            System.out.println("退款失败:原因" + refundmap.get("return_ms"));
        }

    }
}
