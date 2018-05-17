package com.hklk.oplatform.controller;

import com.hklk.oplatform.util.DateUtil;
import com.hklk.oplatform.util.OssUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

@RequestMapping("/test")
@Controller
public class TestController extends BaseController {
    @RequestMapping("/Upload")
    @ResponseBody
    public String commUploadB(MultipartHttpServletRequest request) {//参数类型不同
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
}
