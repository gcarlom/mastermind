package com.gcmassari.mastermind.controller;

import static com.gcmassari.mastermind.data.GameConstants.HOLES_NO;
import static com.gcmassari.mastermind.data.GameConstants.MAX_NO_MOVES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.GameConstants;
import com.gcmassari.mastermind.model.MoveForm;
import com.gcmassari.mastermind.model.Result;
import com.gcmassari.mastermind.model.Round;
import com.gcmassari.mastermind.model.Sequence;
import com.gcmassari.mastermind.validators.MoveValidator;

@Controller
public class GameController {

	@Autowired  // TODO GC: replace with (more standard) @Inject?
	private DataService dataService;

	@Autowired
	private MoveValidator moveValidator;

	private static final Result SEQUENCE_FOUND = new Result(HOLES_NO, 0);

	@RequestMapping({"/","/home"})
	public String showHomePage(Model m) {
		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION);
		return "home";
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String showMoveForm (Model m) {

		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION);

		String sessionId = dataService.getSessionIdForNewMatch();

		MoveForm moveForm = new MoveForm();
		moveForm.setSessionId(sessionId);
		m.addAttribute("moveForm", moveForm);

		return "play";
	}

	@RequestMapping(value = "/play", method = RequestMethod.POST)
	public String evaluateMove (@ModelAttribute("moveForm") MoveForm moveForm, BindingResult result, Model m) {

		m.addAttribute("buildVersion", GameConstants.BUILD_VERSION + "(c)");
		String sessionId = moveForm.getSessionId();

		if (!dataService.isRegisteredPlay(sessionId)) {
			m.addAttribute("errorMessage", "invalid session id");
			return "error";
		}

		// Validate the move, store eventual validation errors in result
		moveValidator.validate(moveForm, result);

		if (result.hasErrors()) {
			m.addAttribute("moveForm", moveForm);
			List<Round> history = dataService.getHistory(sessionId);
			m.addAttribute("history", history);
			return "play";
		}

		// Validator checks also that move isn't null
		String move = moveForm.getMove().trim();
		Sequence latestMove = new Sequence(move); // get latest move from form parameter
		List<Round> history = dataService.getHistoryAfterMove(latestMove, sessionId);
		int movesDone = history.size();
		
		MoveForm nextMoveForm = new MoveForm();
		nextMoveForm.setSessionId(sessionId);
		m.addAttribute("moveForm", nextMoveForm);
		m.addAttribute("history", history);
		boolean endOfTheGame = false;
		
		if ( SEQUENCE_FOUND.equals(history.get(movesDone-1).getResult()) ) {
			endOfTheGame = true;
			m.addAttribute("userWon", true);
		} else if (movesDone >= MAX_NO_MOVES) {
			endOfTheGame = true;
			m.addAttribute("userWon", false);
			Sequence secretSequence = dataService.getSecretSequence(sessionId);
			m.addAttribute("secret", secretSequence); // used to display which was the secret sequence
		}
		if (endOfTheGame) {
			dataService.removeSession(sessionId);
			m.addAttribute("endOfGame", true);
		}

		return "play";
	}


}
