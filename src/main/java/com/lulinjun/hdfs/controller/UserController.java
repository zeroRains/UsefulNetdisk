package com.lulinjun.hdfs.controller;

import com.alibaba.fastjson.JSON;
import com.lulinjun.hdfs.model.MyUser;
import com.lulinjun.hdfs.service.HDFSService;
import com.lulinjun.hdfs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HDFSService hdfsService;

    @ResponseBody
    @RequestMapping("/logins")
    public String logins(@RequestParam("num") String num, @RequestParam("psw") String psw, HttpSession httpSession) {
        List<MyUser> users = userService.login(num, psw);
        String dataJson = "fail";
        if (users.size() == 1) {
            String account = users.get(0).getId();
            String name = users.get(0).getUsername();
            if (account.equals("admin")) {
                httpSession.setAttribute("account", "/");
            } else {
                httpSession.setAttribute("account", account);
            }
            httpSession.setAttribute("name", name);
            if (httpSession.getAttribute("sharePath") != null) {
                return "share@" + (String) httpSession.getAttribute("sharePath");
            }
            dataJson = JSON.toJSONString(users);
        }
        return dataJson;
    }

    @ResponseBody
    @RequestMapping("/registers")
    public String registers(@RequestParam("uid") String uid, @RequestParam("password") String password,
                            @RequestParam("repassword") String repassword,
                            @RequestParam("username") String username, @RequestParam("phone") String phone,
                            @RequestParam("email") String email, @RequestParam("info") String info,
                            HttpSession httpSession) throws IOException {
        String dataJson = "fail";
        if (repassword.equals(password) && userService.selectById(uid).size() == 0) {
            MyUser user = new MyUser(uid, password, username, phone, email, info);
            if (userService.addUser(user)) {
                String account = user.getId();
                String name = user.getUsername();
                httpSession.setAttribute("account", account);
                httpSession.setAttribute("name", name);
                hdfsService.createDir(account);
                hdfsService.createDir("star_" + account);
                if (httpSession.getAttribute("sharePath") != null) {
                    return "share@" + (String) httpSession.getAttribute("sharePath");
                }
                dataJson = "success";
            }
        }
        return dataJson;
    }

    @RequestMapping("/out")
    public String out(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/testsssss")
    public String testsssss(ModelMap modelMap, HttpSession httpSession) {
        String id = httpSession.getId();
        String account = (String) httpSession.getAttribute("account");
        String name = (String) httpSession.getAttribute("name");
        modelMap.put("id", id);
        modelMap.put("account", account);
        modelMap.put("name", name);
        return "add";
    }

    @RequestMapping("/user/info")
    public String information(ModelMap modelMap, HttpSession session) {
        String id = (String) session.getAttribute("account");
        String name = (String) session.getAttribute("name");
        id = id.equals("/") ? "admin" : id;
        List<MyUser> users = userService.selectById(id);
        modelMap.put("user", users.get(0));
        modelMap.put("username", name);
        return "userinfo";
    }

    @ResponseBody
    @RequestMapping("/user/modify")
    public String information(@RequestParam("uid") String uid, @RequestParam("password") String password,
                              @RequestParam("username") String username, @RequestParam("phone") String phone,
                              @RequestParam("email") String email, @RequestParam("info") String info,
                              HttpSession httpSession) {
        String res = "fail";
        String now = (String) httpSession.getAttribute("account");
        now = now.equals("/") ? "admin" : now;
//        String now = "admin";
        if (now.equals(uid)) {
            MyUser user = new MyUser(uid, password, username, phone, email, info);
            if (userService.modify(user)) return "success";
        }
        return res;
    }
}
