package com.hklk.oplatform.controller;

import com.hklk.oplatform.comm.cache.RedisObjCache;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

            String[] fieldNames = {"username", "password", "nickname", "nickname", "remark", "des"};
            List<User> users = ExcelUtils.importExcel(fileTemp, User.class, fieldNames, 1, 0, 0);

            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }

    @RequestMapping("/testRedis")
    @ResponseBody
    public String testRedis(HttpServletRequest request) {
        redisObjCache.set("temp:test:0", "111");
        System.out.println(redisObjCache.get("temp:test:0"));
        return "111";
    }
}
