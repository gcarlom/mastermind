package com.gcmassari.mastermind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcmassari.mastermind.data.Constants;
import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.Token;

// TODO GC: Add login to access to this page, add commands to remove (last) xxx sessions (cron job params)?

 @Controller
public class AdminPagesController {

	@Autowired
	private DataService dataService;

    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
	public String showSessionsToRegisteredUsers(@ModelAttribute("sessionToken") Token sessionToken, Model m) {
		m.addAttribute("buildVersion", Constants.BUILD_VERSION);
		if (sessionToken.isValid()) {
		    m.addAttribute("sessionToken", Token.createNew());
		    m.addAttribute("sessionInfo", dataService.getSessionInfo());
		    return "admin/session";
		}
		return "error";
	}

}
