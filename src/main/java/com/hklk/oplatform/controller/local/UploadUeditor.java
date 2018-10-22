package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * oplatform
 * 2018/7/31 10:21
 * 百度编辑器上传
 *
 * @author 曹良峰
 * @since
 **/
@RequestMapping("/UE")
@Controller
public class UploadUeditor {


    /**
     * 2018/7/4 17:17
     * 上传文件
     *
     * @param request （uploadfile）
     * @return java.lang.String
     * @author 曹良峰
     */
    @RequestMapping("/uploadfile")
    public String uploadfile(@RequestParam("upfile") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + System.currentTimeMillis() + FileUtils.getFilenameExtension(file.getOriginalFilename());
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String fileKey = "KCGX" + PasswordProvider.getMd5ByFile(fileTemp) + "." + FileUtils.getFilenameExtension(file.getOriginalFilename());
            OssUtil.uploadFile(fileKey, fileTemp);
            String accessToDomainNames = PropUtil.getProperty("ossAccessToDomainNames") + "/" + fileKey;
            Map<String, String> result = new HashMap<>();
            result.put("original", fileTemp.getName());
            //    result.put("size", file.getSize() + "");
            result.put("state", "SUCCESS");
            result.put("title", fileKey);
            // result.put("type", FileUtils.getFilenameExtension(file.getOriginalFilename()));
            result.put("url", accessToDomainNames);
            String issimpleupload = request.getParameter("issimpleupload");
            if (issimpleupload != null && "true".equals(issimpleupload)) {
                System.out.println(HttpRequestUtils.mapToFormData(result, false));
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("资源的URL");
                //调用forward()方法，转发请求
                return "redirect:" + PropUtil.getProperty("webAddress") + HttpRequestUtils.mapToFormData(result, false);
            } else {
                return JsonUtil.toJson(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.warnStr(ResultCode.UPLOAD_ERROR);
        }
    }

    /**
     * 2018/7/4 17:17
     * 上传文件
     *
     * @param request （uploadfile）
     * @return java.lang.String
     * @author 曹良峰
     */
    @RequestMapping("/uploadStringfile")
    @ResponseBody
    public String uploadStringfile(@RequestParam("upfile") String file, HttpServletRequest request) {

        String realSavePath = request.getSession().getServletContext().getRealPath("/")
                + "/uploadTempDirectory/" + System.currentTimeMillis() + ".jpg";
        int size = 1024 * 1024 * 2;
        try {
            // 上传时生成的临时文件保存目录
            String tempPath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/";
            File tmpFile = new File(tempPath);
            if (!tmpFile.exists()) {
                // 创建临时目录当前已处理
                tmpFile.mkdir();
            }

            //sp.makePath(savePath);

            InputStream in = new BufferedInputStream(new DataInputStream(request.getInputStream()), size);
            OutputStream out = new BufferedOutputStream(new FileOutputStream(realSavePath), size);
            byte buffer[] = new byte[1024];
            // 判断输入流中的数据是否已经读完的标识
            int len = 0;
            // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while ((len = in.read(buffer)) > 0) {
                // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
                // +filename)当

                out.write(buffer, 0, len);
            }
            // 关闭输入流
            in.close();
            // 关闭输出流
            out.close();

            return "{\"state\": \"SUCCESS\",\"original\": \"\",\"size\": \"5400\",\"title\": \"1532940280368048321.jpg\",\"type\": \".jpg\",\"url\": \"/ueditor/jsp/upload/image/20180730/1532940280368048321.jpg\"}";

        } catch (IOException e) {
            System.out.println("io操作异常");
            e.printStackTrace();
            return "";
        }

    }
}
