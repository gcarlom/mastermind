package com.gcmassari.mastermind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcmassari.mastermind.data.Constants;
import com.gcmassari.mastermind.data.DataService;

@Controller
public class LoginController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = {"/login","/admin"}, method = RequestMethod.GET)
    public String loginGet(Model m,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        final String loginPage = "admin/login";
        m.addAttribute("buildVersion", Constants.BUILD_VERSION);

        if (error != null) {
            m.addAttribute("error", "Invalid username or password!");
            return loginPage;
        }

        if (logout != null) {
            m.addAttribute("msg", "You've been logged out successfully.");
        } else {
            m.addAttribute("msg", "Please Enter Your Login Details");
        }
        return loginPage;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public String adminPage(Model m) {
        m.addAttribute("buildVersion", Constants.BUILD_VERSION);

        m.addAttribute("sessionInfo", dataService.getSessionInfo());
        return "admin/session";
    }

}
