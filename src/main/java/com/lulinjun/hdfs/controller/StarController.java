package com.lulinjun.hdfs.controller;

import com.lulinjun.hdfs.model.BaseRespond;
import com.lulinjun.hdfs.model.HDFSFile;
import com.lulinjun.hdfs.service.HDFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/star")
public class StarController {
    @Autowired
    HDFSService hdfsService;

    @ResponseBody
    @RequestMapping("/list")
    public BaseRespond<HDFSFile> lists(HttpSession httpSession) throws IOException {
        String user = "/star_"+(String) httpSession.getAttribute("account") + "/";
        List<HDFSFile> res = hdfsService.getDirectoryFromHdfs(user);
        return new BaseRespond<>(0, "", res.size(), res);
    }

    @ResponseBody
    @RequestMapping("/lists")
    public BaseRespond<HDFSFile> lists(HttpSession httpSession, @RequestParam("path") String path) throws IOException {
        String user = "/star_"+(String) httpSession.getAttribute("account") + "/" + path + "/";
        List<HDFSFile> res = hdfsService.getDirectoryFromHdfs(user);
        return new BaseRespond<>(0, "", res.size(), res);
    }

    @ResponseBody
    @RequestMapping("/del")
    public String del(@RequestParam("path") String path, HttpSession session) {
        String res = "success";
        path = "/star_"+(String) session.getAttribute("account") + path.replaceAll(",", "/");
        try {
            hdfsService.deleteFile(path);
        } catch (Exception e) {
            res = "fail";
        }
        return res;
    }

    @RequestMapping("/addForm")
    public String addForm() {
        return "add";
    }

    @RequestMapping("/modify")
    public String rename(@RequestParam(name = "newName") String newName,
                         @RequestParam(name = "oldName") String oldName,
                         HttpSession session) throws IOException {
        String user = "/star_"+(String) session.getAttribute("account");
        if (hdfsService.moveFile(user + oldName.replace(",", "/"), user + newName.replace(",", "/"))) {
            System.out.println("????????????");
        } else {
            System.out.println("????????????");
        }

        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping("/download")
    public String downLoad(@RequestParam("name") String filePath, HttpSession session, HttpServletResponse response) throws IOException {

        String res = "success";
        try {
            //??????????????????
//            response.setContentType("application/octet-stream;charset=UTF-8");
            /**
             * ???????????????
             * Content-Disposition????????????????????????
             * inline ??? attachment
             * inline ???????????????????????????????????????
             * attachment???????????????????????????????????????
             */
            filePath = filePath.replaceAll(",", "/");
            String[] filename = filePath.split("/");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename[filename.length - 1] + "");
            filePath = "/star_"+(String) session.getAttribute("account") + filePath;
            //??????????????????
            InputStream fis = hdfsService.getFileInputStreamForPath(filePath);
            int len = 0;
            byte[] buf = new byte[512];

            //??????response????????????
            ServletOutputStream outputStream = response.getOutputStream();

            //??????????????????response???????????? ??????????????????
            while ((len = fis.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            //????????????
            fis.close();
        } catch (Exception e) {
            res = "fail";
        }
        return res;
        //??????????????????
    }
    @RequestMapping("/move")
    public String move(@RequestParam("path") String path, ModelMap modelMap, HttpSession httpSession) {
        String name = "/star_"+(String) httpSession.getAttribute("name");
        if (name == null)
            return "redirect:/login";
        path = path.replaceAll(",", "/");
        modelMap.put("username", name);
        modelMap.put("path", path);
        return "starpage";
    }

    @ResponseBody
    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public BaseRespond<String> addFile(MultipartFile file, @RequestParam("name") String path, HttpSession session) throws IOException {
        BaseRespond<String> res = new BaseRespond<String>(0, "success", 0, null);
        try {
            String originalFileName = file.getOriginalFilename();
            String user = "/star_"+(String) session.getAttribute("account") + path.replaceAll(",", "/");
            File file1 = new File("d:/bigdatatemp/" + originalFileName);
            file.transferTo(file1);
            hdfsService.copyFile(user, file1.getAbsolutePath());
//            System.out.println(originalFileName);
        } catch (Exception e) {
            res.setCode(-1);
            res.setMsg("error");
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/createdir")
    public String createDir(@RequestParam String path, HttpSession session) {
        String res = "success";
        path = "/star_"+(String) session.getAttribute("account") + path.replaceAll(",", "/");
        try {
            hdfsService.createDir(path);
        } catch (Exception e) {
            res = "fail";
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/share/create")
    public String create(@RequestParam("path") String path, HttpSession session) {
        String user = "/star_"+(String) session.getAttribute("account");
        return "http://127.0.0.1:8084/share/home?path=" + user.replaceAll("/", ",") + path;
    }
}
