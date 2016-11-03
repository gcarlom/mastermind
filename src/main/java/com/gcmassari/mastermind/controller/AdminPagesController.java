package com.gcmassari.mastermind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.GameConstants;

// TODO remove this class ?

@Controller
public class AdminPagesController {

	@Autowired  // TODO GC: replace with @inject?
	private DataService dataService;

	@RequestMapping("/session")
	public String showRegisteredSessions(Model m) {
		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION);
		m.addAttribute("sessions", dataService.getSessions());
		return "admin/session";
	}

}
