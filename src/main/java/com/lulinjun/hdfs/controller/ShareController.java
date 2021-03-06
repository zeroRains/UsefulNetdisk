package com.lulinjun.hdfs.controller;

import com.lulinjun.hdfs.model.BaseRespond;
import com.lulinjun.hdfs.model.HDFSFile;
import com.lulinjun.hdfs.model.StarFile;
import com.lulinjun.hdfs.service.HDFSService;
import com.lulinjun.hdfs.service.StarFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/share")
public class ShareController {


    @Autowired
    HDFSService hs;

    @Autowired
    StarFileService ss;

    @RequestMapping("/home")
    public String share(ModelMap modelMap, @RequestParam("path") String path) throws IOException {
//        String res = ps.getPath(path);
        String res = path.replaceAll(",", "/");
        modelMap.put("res", res);
        modelMap.put("first", res);
        return "share";
    }

    @ResponseBody
    @RequestMapping("/list")
    public BaseRespond<HDFSFile> getDir(@RequestParam("name") String path) throws IOException {
        List<HDFSFile> res = hs.getDirectoryFromHdfs(path.replaceAll(",", "/"));
        return new BaseRespond<>(0, "success", res.size(), res);
    }

    @RequestMapping("/move")
    public String move(@RequestParam("path") String path, ModelMap modelMap) {
        path = path.replaceAll(",", "/");
        modelMap.put("res", path);
        return "share";
    }

    @ResponseBody
    @RequestMapping("/download")
    public String downLoad(@RequestParam("name") String filePath, HttpServletResponse response) throws IOException {

        String res = "success";
        try {
            //设置相应类型
//            response.setContentType("application/octet-stream;charset=UTF-8");
            /**
             * 设置响应头
             * Content-Disposition属性有两种类型：
             * inline 和 attachment
             * inline ：将文件内容直接显示在页面
             * attachment：弹出对话框让用户选择下载
             */
            filePath = filePath.replaceAll(",", "/");
            String[] filename = filePath.split("/");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename[filename.length - 1] + "");
            //创建出文件流
            InputStream fis = hs.getFileInputStreamForPath(filePath);
            int len = 0;
            byte[] buf = new byte[512];

            //获取response的输出流
            ServletOutputStream outputStream = response.getOutputStream();

            //读取同时，用response的输出流 发送给客户端
            while ((len = fis.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            //关闭资源
            fis.close();
        } catch (Exception e) {
            res = "fail";
        }
        return res;
        //注意不要跳转
    }

    @ResponseBody
    @RequestMapping("/star")
    public String startDoc(@RequestParam("path") String path, HttpSession session) {
        String user = (String) session.getAttribute("account");
        if (user == null) {
            session.setAttribute("sharePath", path);
            return "login";
        }
        if (ss.star(new StarFile(user, path))) {
            hs.copyDir(path.replaceAll(",", "/"), "/star_" + user + "/");
            return "success";
        }
        return "fail";
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(@RequestParam("path") String path, HttpSession session) {
        String user = (String) session.getAttribute("account");
        return "http://127.0.0.1:8084/share/home?path=," + user.replaceAll("/", ",") + path;
    }

}
