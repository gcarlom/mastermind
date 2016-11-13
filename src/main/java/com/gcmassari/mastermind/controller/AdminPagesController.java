package com.gcmassari.mastermind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.Constants;

// TODO remove this class ?

@Controller
public class AdminPagesController {

	@Autowired  // TODO GC: replace with @inject?
	private DataService dataService;
// TODO add control for removing older sessions
	@RequestMapping("/session")
	public String showRegisteredSessions(Model m) {
		m.addAttribute("buildVersion", Constants.BUILD_VERSION);
		m.addAttribute("sessionInfo", dataService.getSessionInfo());
		return "admin/session";
	}

}
