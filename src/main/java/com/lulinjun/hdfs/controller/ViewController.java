package com.lulinjun.hdfs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class ViewController {

    //    @ResponseBody
    @RequestMapping("/")
    public String sayHello(ModelMap model, HttpSession httpSession) {
        String name = (String) httpSession.getAttribute("name");
        if (name == null)
            return "redirect:/login";
        model.put("username", name);
        model.put("path", "/");
        return "home";
    }


    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/move")
    public String move(@RequestParam("path") String path, ModelMap modelMap, HttpSession httpSession) {
        String name = (String) httpSession.getAttribute("name");
        if (name == null)
            return "redirect:/login";
        path = path.replaceAll(",", "/");
        modelMap.put("username", name);
        modelMap.put("path", path);
        return "home";
    }

    @RequestMapping("/star")
    public String star(ModelMap model, HttpSession session) {
        String name = (String) session.getAttribute("name");
        model.put("username", name);
        model.put("path", "/");
        return "starpage";
    }
}
