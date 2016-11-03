package com.gcmassari.mastermind.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.GameConstants;
import com.gcmassari.mastermind.model.Result;
import com.gcmassari.mastermind.model.Round;
import com.gcmassari.mastermind.model.Sequence;

import static com.gcmassari.mastermind.data.GameConstants.*;



@Controller
public class GameController {

	@Autowired  // TODO GC: replace with @inject?
	private DataService dataService;

	private static final Result SEQUENCE_FOUND = new Result(HOLES_NO, 0);

	@RequestMapping({"/","/home"})
	public String showHomePage(Model m) {
		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION);
		return "home";
	}

	@RequestMapping({"/play"})
	public String showPlayPage(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "move", required = false) String move,
			Model m) {

		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION);
		List<Round> history = new ArrayList<Round>();
		//		m.addAttribute("name", "Mastermind");

		if (sessionId == null) {
			sessionId = dataService.getSessionIdForNewMatch();
			// TODO debug only: don't store sessionId, secretCeq attributes in model
			m.addAttribute("sessionId", sessionId);

			Sequence secretSequence = dataService.getSecretSequence(sessionId);
			m.addAttribute("secret", secretSequence);
			return "play";
		}

		if (!dataService.isRegisteredPlay(sessionId)) {
			m.addAttribute("errorMessage", "invalid session id");
			return "error";
		}

		if (move == null) {
			System.out.println("--> *********** move== null && sessID!= null ?!");
			// TODO debug only: don't store sessionId, secretCeq attributes in model
			Sequence secretSequence = dataService.getSecretSequence(sessionId);
			m.addAttribute("sessionId", sessionId);
			m.addAttribute("secret", secretSequence);
			return "play";
		}
		move = move.trim(); // TODO check if if invalid move! -> if so: error
		Sequence latestMove = new Sequence(move); // get latest move from form parameter
		history = dataService.getHistoryAfterMove(latestMove, sessionId);
		int movesDone = history.size();
		
		Sequence secretSequence = dataService.getSecretSequence(sessionId);
		m.addAttribute("sessionId", sessionId);
		m.addAttribute("secret", secretSequence);
		m.addAttribute("history", history);
		boolean endOfTheGame = false;
		
		if ( SEQUENCE_FOUND.equals(history.get(movesDone-1).getResult()) ) {
			endOfTheGame = true;
			m.addAttribute("userWon", true);
			dataService.removeSession(sessionId);
		} else if (movesDone >= MAX_NO_MOVES) {
			endOfTheGame = true;
			m.addAttribute("userWon", false);
		}
		if (endOfTheGame) {
			dataService.removeSession(sessionId);
			m.addAttribute("endOfGame", endOfTheGame);
		}

		// TODO debug only: don't store  secretSeq attributes in model
		// m.addAttribute("endOfGame", false);
		return "play";
	}


}
