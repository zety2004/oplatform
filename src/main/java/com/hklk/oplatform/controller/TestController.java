package com.hklk.oplatform.controller;

import com.hklk.oplatform.comm.cache.RedisObjCache;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/test")
@Controller
public class TestController extends BaseController {

    @Resource
    RedisObjCache redisObjCache;

    @Autowired
    UserService userService;

    /**
     * @author 曹良峰
     * @Description 上传测试
     * @Date 16:10 2018/5/24
     * @Param [request]
     * @Return java.lang.String
     **/
    @RequestMapping("/Upload")
    @ResponseBody
    public String commUploadB(MultipartHttpServletRequest request) {
        JSONObject json = new JSONObject();
        json.put("succ", false);
        try {
            MultipartFile file = request.getFile("uploadfile");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            System.out.println(savePath);
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String fileKey = "KCGX" + DateUtil.currentTimestamp2String("yyyyMMddHHmmss");
            OssUtil.uploadFile(fileKey, fileTemp);
            json.put("succ", true);
            json.put("fileName", fileKey);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return json.toString();
        }
    }

    /**
     * @author 曹良峰
     * @Description 测试导出excel
     * @Date 16:38 2018/5/24
     * @Param [request, response]
     * @Return java.lang.String
     **/
    @RequestMapping("/exportExcel")
    @ResponseBody
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) {
        String[] columnHeader = {"id", "username", "password", "createtime", "nickname", "remark", "state", "des", "userIco"};
        String[] fieldNames = {"主键", "用户名", "密码", "创建时间", "昵称", "备注", "状态", "描述", "头像"};
        PageTableForm<User> users = userService.queryUsers(new User(), 1, pageSize);
        List<User> tmp = users.getObjList();
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=details.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", tmp, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public String importExcel(MultipartHttpServletRequest request) {

        try {
            MultipartFile file = request.getFile("uploadfile");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);

            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);

            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }

    @RequestMapping("/testLong")
    @ResponseBody
    public String testRedis(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        final AsyncContext ctx = request.startAsync();

        System.out.println("进入Servlet的时间：" + System.currentTimeMillis() + ".");


        ctx.setTimeout(200000);
        //new Work(ctx).start();
        System.out.println("结束Servlet的时间：" + System.currentTimeMillis() + ".");

        return "111";
    }

    public String getminiqrQr(String accessToken) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<>();
            param.put("page", "pages/index/index");
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();

            inputStream = new ByteArrayInputStream(result);
            File file = new File("D:/用户目录/我的图片/1.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @RequestMapping("/wxMa")
    @ResponseBody
    public String wxMa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String accessToken = "12_OWhCvsbqU-Oxd9ZLUW-LSH5WT3vU4SDqs3nkWnSL-0KuV8451pnn83XjNt-9q22GbGI6EJ-cOhAZGYQIa2x3o-4T1myM_sK3QwcNNQdofmAzz0jGOXBWAre0jQs-Xb-X-a0vEoZCMdO22gceYGLeAFAEMR";

        String result = getminiqrQr(accessToken);
        return result;
    }
}
