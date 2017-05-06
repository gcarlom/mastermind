package com.gcmassari.mastermind.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcmassari.mastermind.data.Constants;
import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.LoginBean;
import com.gcmassari.mastermind.data.Token;

@Controller
public class LoginController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = {"/login","/admin"}, method = RequestMethod.GET)
    public String init(Model m) {
        m.addAttribute("msg", "Please Enter Your Login Details");
        m.addAttribute("buildVersion", Constants.BUILD_VERSION);
        return "admin/login";
    }

    @RequestMapping(value={"/login","/admin"}, method = RequestMethod.POST)
    public String submit(Model m, @ModelAttribute("loginBean") LoginBean loginBean) {
        StringBuilder logInErrorMessage =  new StringBuilder();
        if (areUserAndPasswordCorrect(loginBean, logInErrorMessage)) {
                m.addAttribute("buildVersion", Constants.BUILD_VERSION);
                Token token = Token.createNew();
                m.addAttribute("sessionToken", token);

                m.addAttribute("sessionInfo", dataService.getSessionInfo());
                return "admin/session";
        } else {
            m.addAttribute("error", logInErrorMessage.toString());
            return "admin/login";
        }
    }

    @RequestMapping(value = "/secret**", method = RequestMethod.GET)
    public String adminPage(Model model) {
        model.addAttribute("title", "Spring Security Hello World");
        model.addAttribute("message", "This is protected page!");

        return "admin/secret";
    }



    // Utility methods
    private boolean areUserAndPasswordCorrect(LoginBean login, StringBuilder error) {
        if (login == null || StringUtils.isEmpty(login.getUserName()) || StringUtils.isEmpty(login.getPassword())) {
            error.replace(0, error.length(), "Please enter User and Password");
            return false;
        }

        // TODO GC: Read password form DB or property file !! (no psw hard coded !!)
        if (login.getUserName().equals("admin") && login.getPassword().equals("12345")) {
            return true;
        }

        error.replace(0, error.length(), "Invalid User or Password");
        return false;
    }
}
